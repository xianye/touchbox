package com.xc.touchbox.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.Account;
import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.AdminRoleRelation;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.Resource;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.Serial;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.UserCommon;
import com.xc.touchbox.model.Verification;

public class UserServiceImpl extends CDAOTemplateImpl implements UserService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger.getLogger(UserServiceImpl.class);

	@Override
	public boolean doGenerateSerial(Serial serial) {
		if (serial != null && StringUtils.isNotEmpty(serial.getPlatform())) {

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Random random = new Random();
				Date date = new Date();
				StringBuilder sb = new StringBuilder(serial.getPlatform()
						.toUpperCase()).append(sdf.format(date));
				for (int i = 0; i < 8; i++) {
					sb.append(random.nextInt(10));
				}
				serial.setSerialId(sb.toString());
				serial.setCreateTime(date);
				// 保存序列号
				getHibernateTemplate().save(serial);
				return true;
			} catch (Exception e) {
				SimpleUtils.log(e, log);
			}
		}
		return false;
	}

	@Override
	public Verification doSendVcode(String serialId, String mobile) {
		Verification a = (Verification) this
				.getHQLUnique("from Verification where mobile=? and serialId=? order by verificationId desc");

		if (a == null
				|| (a.getCreateTime().getTime() + 600000) < System
						.currentTimeMillis()) {
			a = new Verification();
			a.setMobile(mobile);

			Serial b = new Serial();
			b.setSerialId(serialId);
			a.setSerial(b);

			StringBuilder codeStr = new StringBuilder(RandomUtils.nextInt(10));
			codeStr.append(RandomUtils.nextInt(10))
					.append(RandomUtils.nextInt(10))
					.append(RandomUtils.nextInt(10));
			a.setCode(codeStr.toString());
			a.setStatus(CDAOTemplate.STATUS_DATA_VALID);
			a.setCreateTime(new Date());
			getHibernateTemplate().save(a);
		}

		String smsText = ISysParam.API_SMS_CONTENT_TEMPLET_VCODE;

		smsText = smsText.replace("$(vcode)", a.getCode());

		if (sendSms(a.getMobile(), smsText)) {
			return a;
		}

		return null;
	}

	@Override
	public int doLogin(Account account) {
		int errorCode = 0;// 返回错误码，0表示成功，大于0的数值表示具体错误

		Account a = (Account) getHQLUnique("from Account where username=?",
				account.getUsername());
		if (a != null) {
			if (!a.getPassword().equals(
					ISysParam.encodePassword(account.getPassword()))) {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_LOGIN_PASSWORDERROR;
			} else {
				BeanUtils.copyProperties(a, account);
			}
		} else {
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_LOGIN_NOTEXISTACCOUNT;
		}

		return errorCode;
	}

	@Override
	public PaginationSupport<User> findUser(String mobile, int page,
			int pagesize) {
		StringBuilder hql = new StringBuilder();
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(mobile)) {
			hql.append("select b from Account a,User b where a.userId = b.userId and a.mobile=?");
			params.add(mobile);
		} else {
			params = null;
			hql.append("select b from Account a,User b where a.userId = b.userId");
		}
		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

	@Override
	public PaginationSupport<Admin> findAdmin(String keyword,
			String roleGroupId, int roleId, int page, int pagesize) {

		List<Role> roleList = getValidRoles(null);
		Map<Integer, Role> roleMap = new HashMap<Integer, Role>();
		StringBuilder roleIdsOnMyGroup = new StringBuilder();
		if (CollectionUtils.isNotEmpty(roleList)) {
			for (Role o : roleList) {
				roleMap.put(o.getRoleId(), o);
				if (StringUtils.isNotEmpty(roleGroupId)
						&& ("," + roleGroupId + ",").indexOf(","
								+ o.getGroupId() + ",") != -1) {// 以角色分组ID查询
					roleIdsOnMyGroup.append(o.getRoleId()).append(",");
				}
			}
			if (roleIdsOnMyGroup.length() > 0) {
				roleIdsOnMyGroup.deleteCharAt(roleIdsOnMyGroup.length() - 1);
			}
		}

		StringBuilder hql = new StringBuilder(
				"select new AdminRoleRelation(b,c.roleId) from Admin b,AdminRoleRelation c where b.status!=2 and b.userId=c.userId ");
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and (b.name like ? or b.username=? or b.mobile=?) ");
			params.add("%" + keyword + "%");
			params.add(keyword);
			params.add(keyword);
		}
		if (roleId > 0) {// 角色
			hql.append("and c.roleId=? ");
			params.add(roleId);
		} else if (roleIdsOnMyGroup.length() > 0) {// 以角色分组ID查询
			hql.append("and c.roleId in (").append(roleIdsOnMyGroup)
					.append(") ");

		}
		hql.append("order by b.userId desc");

		int firstResults = (page - 1) * pagesize;
		PaginationSupport<Admin> ps = getHQLPagination(hql.toString(), params,
				firstResults, pagesize);
		if (ps != null) {
			Map<String, Object> setDataParams = new HashMap<String, Object>();
			setDataParams.put("roleMap", roleMap);
			List dataList = setDataToUser(ps.getItems(), setDataParams);
			ps.setItems(dataList);
		}
		return ps;
	}

	/**
	 * 设置账号对象至用户对象（user或admin）
	 * 
	 * @param items
	 *            用户对象列表
	 */
	private List setDataToUser(List items, Map<String, Object> params) {
		if (CollectionUtils.isNotEmpty(items)) {
			Map<Integer, Role> roleMap = (Map<Integer, Role>) params
					.get("roleMap");
			Map<Long, UserCommon> userMap = new HashMap<Long, UserCommon>();
			List<UserCommon> targetItems = new ArrayList<UserCommon>();
			for (Object o : items) {
				if (o instanceof AdminRoleRelation) {
					Admin a = ((AdminRoleRelation) o).getAdmin();
					int roleId = ((AdminRoleRelation) o).getRoleId();
					a.getRoleList().add(roleMap.get(roleId));
					userMap.put(a.getUserId(), a);
					targetItems.add(a);
				} else if (o instanceof UserCommon) {
					UserCommon uc = (UserCommon) o;
					userMap.put(uc.getUserId(), uc);
				}
			}

			if (MapUtils.isNotEmpty(userMap)) {
				List<Account> accounts = getHibernateTemplate().find(
						"from Account a where a.userId in ("
								+ StringUtils.join(userMap.keySet(), ",")
								+ ") ");
				if (CollectionUtils.isNotEmpty(accounts)) {
					for (Account o : accounts) {
						userMap.get(o.getUserId()).setAccount(o);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(targetItems)) {
				return targetItems;
			}

		}

		return items;
	}

	@Override
	public Admin getAdminByUsername(String mobile) {

		Account a = (Account) this.getHQLUnique(
				"from Account where adminEnabled=true and username=?", mobile);
		if (a != null) {
			Admin b = a.getAdmin();
			b.setAccount(a);
			return b;
		}
		return null;
	}

	@Override
	public int saveAdmin(Admin admin, int roleId) {
		int result = ISysParam.API_RESPONSE_ERRORCODE_SAVEDATA_FAIL;
		try {
			Date date = new Date();
			if (admin.getUserId() > 0) {// 更新admin

				Admin dbObj = getHibernateTemplate().get(Admin.class,
						admin.getUserId());

				int updateCount = getHibernateTemplate()
						.bulkUpdate(
								"update Admin set name=?,sex=?,mobile=?,email=?,updateTime=now() where userId=?",
								new Object[] { admin.getName(), admin.getSex(),
										admin.getMobile(), admin.getEmail(),
										admin.getUserId() });

				if (updateCount > 0 && admin.isAccountChanged(dbObj)) {
					getHibernateTemplate()
							.bulkUpdate(
									"update Account set mobile=?,email=?,updateTime=now() where userId=?",
									new Object[] { admin.getMobile(),
											admin.getEmail(), admin.getUserId() });
				}
				admin = dbObj;

				getHibernateTemplate().bulkUpdate(
						"delete from AdminRoleRelation where userId=?",
						admin.getUserId());

			} else {// 新增admin
				Account a = (Account) this.getHQLUnique(
						"from Account where username=?", admin.getUsername());
				if (a != null) {
					if (a.isAdminEnabled()) {
						return ISysParam.API_RESPONSE_ERRORCODE_SAVEDATA_EXISTUSERNAME;
					} else {
						getHibernateTemplate()
								.bulkUpdate(
										"update Account set adminEnabled=true,updateTime=now() where userId=?",
										new Object[] { admin.getUserId() });
					}
				} else {
					a = new Account();
					a.setUsername(admin.getUsername());
					a.setMobile(admin.getMobile());
					a.setEmail(admin.getEmail());
					a.setAdminEnabled(true);
					a.setPassword(ISysParam.encodePassword("123456"));
					a.setCreateTime(date);

					admin.setAccount(a);
				}

				admin.setCreateTime(date);
				admin.setStatus(1);
				getHibernateTemplate().save(admin);
			}

			AdminRoleRelation arr = new AdminRoleRelation();
			arr.setUserId(admin.getUserId());
			arr.setRoleId(roleId);
			arr.setCreateTime(date);
			getHibernateTemplate().save(arr);

			result = 0;
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

		return result;
	}

	@Override
	public List<Integer> getAdminRoleIds(long userId) {
		return getHibernateTemplate().find(
				"select roleId from AdminRoleRelation where userId=?", userId);
	}

	@Override
	public List<Role> getAdminRoles(long userId) {
		List<Role> roleList = new ArrayList<Role>();
		List<Integer> roleIds = getAdminRoleIds(userId);
		if (CollectionUtils.isNotEmpty(roleIds)) {
			roleList = getHibernateTemplate().find(
					"from Role where roleId in ("
							+ StringUtils.join(roleIds, ",") + ")");
		}
		return roleList;
	}

	@Override
	public void fetchAdminResources(Admin admin) {
		boolean isSuperAdmin = false;
		StringBuilder roleIds = new StringBuilder();
		for (Role o : (List<Role>) admin.getRoleList()) {
			roleIds.append(o.getRoleId()).append(",");
			if (o.isSuberAdmin()) {
				isSuperAdmin = true;
			}
		}
		List<Resource> list = getHibernateTemplate()
				.find("select a from Resource a where a.status!=2 order by a.resourceId,a.level");
		List<String> resourceIds = new ArrayList<String>();
		if (!isSuperAdmin) {
			if (roleIds.length() > 0) {
				roleIds.deleteCharAt(roleIds.length() - 1);
				resourceIds = getHibernateTemplate()
						.find("select b.resource.resourceId from RoleResourceAuthority b where b.role.roleId in ("
								+ roleIds + ")");
			}
		}

		if (CollectionUtils.isNotEmpty(list)) {

			Map<String, Resource> resourceDirs = new HashMap<String, Resource>();
			for (Resource o : list) {
				if (o.getType() == CDAOTemplate.CONSTANT_RESOURCE_TYPE_MENU) {
					if (o.getLevel() == 0) {
						resourceDirs.put(o.getResourceId(), o);
						if (isSuperAdmin) {
							admin.getMenuResources().add(o);
						}
					} else {
						if (isSuperAdmin) {
							resourceDirs.get(o.getParentId()).getChildren()
									.add(o);
						} else if (resourceIds.contains(o.getResourceId())) {
							Resource parent = resourceDirs.get(o.getParentId());
							parent.getChildren().add(o);
							if (!admin.getMenuResources().contains(parent))
								admin.getMenuResources().add(parent);
						}
					}
				} else {
					admin.getFuncA().put(o.getFeature(), true);
				}
			}
		}

	}

	@Override
	public User getUserByMobile(String mobile) {
		Account a = (Account) this.getHQLUnique(
				"from Account where userEnabled=true and mobile=?", mobile);
		if (a != null) {
			User b = getHibernateTemplate().get(User.class, a.getUserId());
			b.setAccount(a);
			return b;
		}
		return null;
	}

}
