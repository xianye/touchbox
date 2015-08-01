package com.xc.touchbox.service;

import java.util.List;

import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Resource;
import com.xc.touchbox.model.Role;

public interface RoleService extends CDAOTemplate {

	/**
	 * 获取指定角色关联资源id
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	public List<String> getRoleResourceIds(int roleId);
	
	/**
	 * 保存角色数据
	 * 
	 * @param role 角色对象
	 * @param resourceIds 设置的权限ID
	 */
	public void saveRole(Role role, String[] resourceIds);
	
	/**
	 * 获取有效的资源数据
	 * 
	 * @return
	 */
	public List<Resource> getValidResources();
}
