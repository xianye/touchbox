package com.xc.touchbox.dao;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.xc.gospel.core.db.hibernate.GenericHibernateDAO;
import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.SystemParam;

public class CDAOTemplateImpl extends GenericHibernateDAO implements
		CDAOTemplate {
	protected static Properties systemParams = new Properties();

	protected String rootDir;// 服务根目录
	protected String rootPath;// 服务根路径

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getSystemParam(String id) {
		String paramVal = systemParams.getProperty(id);
		if (StringUtils.isEmpty(paramVal)) {// 内存中获取为空，从数据库查询
			SystemParam sysParam = (SystemParam) this
					.get(SystemParam.class, id);
			if (sysParam != null) {
				paramVal = StringUtils.trimToEmpty(sysParam.getValue());
				systemParams.setProperty(id, paramVal);
			}
		}
		return paramVal;
	}

	/**
	 * 获取所有系统参数值
	 * 
	 * @return
	 */
	public List<SystemParam> getSystemParams() {
		return findAll(SystemParam.class);
	}

	/**
	 * 下发短信验证码
	 * 
	 * @param mobile
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return
	 */
	protected boolean sendSms(String mobile, String content) {
		// TODO 下发短信验证码

		return false;
	}

	@Override
	public List<Role> getValidRoles(String roleGroupId) {
		StringBuilder hql = new StringBuilder("from Role where status!=2 ");
		if (StringUtils.isNotEmpty(roleGroupId)) {
			hql.append("and groupId in (").append(roleGroupId).append(")");
		}

		return getHibernateTemplate().find(hql.toString());
	}

}
