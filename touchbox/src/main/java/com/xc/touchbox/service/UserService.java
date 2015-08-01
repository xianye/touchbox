package com.xc.touchbox.service;

import java.util.List;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Account;
import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.Serial;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.Verification;

public interface UserService extends CDAOTemplate{
	
	/**
	 * 
	 * 生成序列号
	 * 
	 * @param serial 序列号对象
	 * @return
	 */
	public boolean doGenerateSerial(Serial serial);
	
	/**
	 * 发送验证码
	 * 
	 * @param serialId 序列号ID
	 * @param mobile 手机号码
	 * @return 验证码对象
	 */
	public Verification doSendVcode(String serialId,String mobile); 
	
	/**
	 * 用户登陆
	 * 
	 * @param account 账号对象
	 * @return 账号对象
	 */
	public int doLogin(Account account);
	
	/**
	 * 分页查询客户列表数据
	 * 
	 * @param mobile 客户手机号码
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<User> findUser(String mobile,int page,int pagesize);
	
	/**
	 * 分页查询管理员列表数据
	 * 
	 * @param keyword 查询关键字
	 * @param roleGroupId 角色分组ID
	 * @param roleId 角色ID
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Admin> findAdmin(String keyword,String roleGroupId,int roleId,int page,int pagesize);
	
	/**
	 * 根据手机号码查询管理员数据
	 * 
	 * @param mobile 手机号码
	 * @return
	 */
	public Admin getAdminByUsername(String mobile);
	
	/**
	 * 保存管理员数据
	 * 
	 * @param admin 管理员对象
	 * @param roleId 所属角色id
	 * @return
	 */
	public int saveAdmin(Admin admin,int roleId);
	
	/**
	 * 获取指定用户ID相关的角色ID值
	 * 
	 * @param userId 用户id
	 * @return
	 */
	public List<Integer> getAdminRoleIds(long userId);
	
	/**
	 * 获取指定用户ID相关的角色对象列表
	 * 
	 * @param userId 用户id
	 * @return
	 */
	public List<Role> getAdminRoles(long userId);
	
	/**
	 * 获取admin对象相关的资源权限对象数据并存入admin对象中
	 * 
	 * @param admin 管理员对象
	 * @return
	 */
	public void fetchAdminResources(Admin admin);
	
	/**
	 * 根据手机号码查询用户数据
	 * 
	 * @param mobile 手机号码
	 * @return
	 */
	public User getUserByMobile(String mobile);
}
