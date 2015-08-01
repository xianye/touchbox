package com.xc.touchbox.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.Resource;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.RoleResourceAuthority;

public class RoleServiceImpl extends CDAOTemplateImpl implements RoleService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger.getLogger(RoleServiceImpl.class);

	@Override
	public List<String> getRoleResourceIds(int roleId) {
		return getHibernateTemplate()
				.find("select a.resourceId from Resource a ,RoleResourceAuthority b where a.resourceId=b.resource.resourceId and a.status!=2 and b.role.roleId=?",
						roleId);
	}

	public void saveRole(Role role, String[] resourceIds) {
		Date date = new Date();

		try {
			if (role.getRoleId() == null) {
				role.setCreateTime(date);
				getHibernateTemplate().save(role);
			} else {
				Role dbRole = (Role) this.getHibernateTemplate().get(
						Role.class, role.getRoleId());
				BeanUtils.copyProperties(role, dbRole, new String[] {
						"createTime", "status" });
				dbRole.setUpdateTime(date);
				getHibernateTemplate().update(dbRole);
				deleteByIds(RoleResourceAuthority.class, "role.roleId",
						new Object[] { role.getRoleId() });
			}

			// 保存资源权限角色关联数据
			if (ArrayUtils.isNotEmpty(resourceIds)) {
				for (String resId : resourceIds) {

					Resource res = new Resource();
					res.setResourceId(resId);
					RoleResourceAuthority rra = new RoleResourceAuthority();
					rra.setResource(res);
					rra.setRole(role);
					rra.setCreateTime(date);
					getHibernateTemplate().save(rra);
				}
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

	@Override
	public List<Resource> getValidResources() {
		return this.getHibernateTemplate().find("from Resource where status!=2 order by resourceId,level");
	}

}
