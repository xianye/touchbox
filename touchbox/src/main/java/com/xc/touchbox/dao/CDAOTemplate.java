package com.xc.touchbox.dao;

import java.util.List;

import com.xc.gospel.core.db.GenericDAO;
import com.xc.touchbox.model.Role;

/**
 * 需要使用的复杂的且多处使用的数据库处理方法
 * 
 * @author James
 * 
 */
public interface CDAOTemplate extends GenericDAO {	
	
	public static final String UPLOAD_PATH = "uploads/";// 文件临时上传目录
	
	/** ******************* 会话中的字段 ********************** */
	public static final String SESSION_USER = "SESSION_USER";// session用户对象
	public static final String SESSION_ADMIN = "SESSION_ADMIN";// session管理员对象
	public static final String SESSION_VCODEPREFIX = "vcode_";// session存储验证码前缀字符
	public static final String SESSION_SERIAL = "SESSION_SERIAL";// session存储序列号名称

	
	/** ******************* 系统参数中的字段 ********************** */
	public static final String SYSPARAM_API_AUTH_SECRETKEY = "API_AUTH_SECRETKEY";// api鉴权秘钥
	public static final String SYSPARAM_API_AUTH_WHITELIST_IP = "API_AUTH_WHITELIST_IP";// api鉴权白名单ip列表
	public static final String SYSPARAM_PRODUCTION = "PRODUCTION";// 是否生产环境true/false
	public static final String SYSPARAM_API_SMS_CONTENT_TEMPLET_VCODE = "API_SMS_CONTENT_TEMPLET_VCODE";// api验证码短信内容模板
	public static final String SYSPARAM_RESOURCE_DIR_ROOTPATH = "RESOURCE_DIR_ROOTPATH";// 资源文件存放根目录
	public static final String SYSPARAM_RESOURCE_DIR_URL = "RESOURCE_DIR_URL";// 资源目录http地址
	public static final String SYSPARAM_API_SMS_INTERFACE_HTTPURL = "API_SMS_INTERFACE_HTTPURL";// 第三方短信接口http地址
	public static final String SYSPARAM_API_SMS_INTERFACE_USERNAME = "API_SMS_INTERFACE_USERNAME";// 第三方短信接口账号
	public static final String SYSPARAM_API_SMS_INTERFACE_PASSWORD = "API_SMS_INTERFACE_PASSWORD";// 第三方短信接口密码
	public static final String SYSPARAM_API_WAREHOUSING_CHINAWAY_HTTPURL = "API_WAREHOUSING_CHINAWAY_HTTPURL";// 汉维仓储服务接口http地址
	public static final String SYSPARAM_API_WAREHOUSING_CHINAWAY_COMPANY = "API_WAREHOUSING_CHINAWAY_COMPANY";// 汉维仓储服务接口货主编码
	public static final String SYSPARAM_API_WAREHOUSING_CHINAWAY_WAREHOUSE = "API_WAREHOUSING_CHINAWAY_WAREHOUSE";// 汉维仓储服务接口仓库编码

	public static final String CONSTANT_PLATFORM_WEB = "web";// 常量：PC网站
	public static final String CONSTANT_PLATFORM_WAP = "wap";// 常量：手机wap
	public static final String CONSTANT_PLATFORM_ANDROID = "android";// 常量：android平台
	public static final String CONSTANT_PLATFORM_IPHONE = "iPhone";// 常量：iPhone平台
	
	public static final int CONSTANT_INFORMATION_TYPE_PROCLAMATION = 1;// 常量：网站公告
	public static final int CONSTANT_INFORMATION_TYPE_ABOUTNEWS = 2;// 常量：相关新闻
	public static final int CONSTANT_INFORMATION_TYPE_SALESTOOL = 3;// 常量：销售工具
	public static final int CONSTANT_INFORMATION_TYPE_FAQ = 4;// 常量：常见问题
	public static final int CONSTANT_ROLE_GROUP_SUPERADMIN = 1;// 常量：角色组别-管理员
	public static final int CONSTANT_ROLE_GROUP_SELLER = 2;// 常量：角色组别-销售员
	public static final int CONSTANT_RESOURCE_TYPE_MENU = 1;// 常量：资源分类-菜单
	public static final int CONSTANT_RESOURCE_TYPE_FUNCTION = 2;// 常量：资源分类-功能
	public static final int CONSTANT_FEEDBACK_ENTITYTYPE_FEEDBACK = 1;// 常量：反馈实体分类-问题提报
	public static final int CONSTANT_FEEDBACK_ENTITYTYPE_COMPLAINT = 2;// 常量：反馈实体分类-投诉建议

	/**
	 * id：绑定到节点的标识值。
	•text：显示的文字。
	•checked：是否节点被选中。
	•attributes：绑定到节点的自定义属性。
	•target：目标的 DOM 对象。
	 */
	public static final String CONSTANT_FORMAT_JEASYUI_TREE = "jeasyui.tree";// 常量：jeasyui.tree控件数据格式

	/** ******************* 状态常量 ********************** */
	public static final byte STATUS_DATA_VALID = 1;// 数据有效
	public static final byte STATUS_DATA_INVALID = 2;// 数据无效

	
	/** ******************* 操作常量 ********************** */
	// 添加
	public static final byte Operate_Add = 1;
	// 修改
	public static final byte Operate_Modify = 2;
	// 删除
	public static final byte Operate_Del = 3;
	// 审核
	public static final byte Operate_Verify = 4;
	// 恢复
	public static final byte Operate_Recover = 5;
	
	/** **************************** 操作信息start ******************************** */
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_ERROR = "删除失败";
	public static final String UPDATE_SUCCESS = "更新成功";
	public static final String UPDATE_ERROR = "更新失败";
	public static final String UPDATEPWD_SUCCESS = "更新密码成功";
	public static final String UPDATEPWD_ERROR = "更新密码失败";
	public static final String ACCOUNT_NOT_EXIST = "帐号已经存在";
	public static final String QUERY_NOT_ACCOUT = "帐号不存在";
	public static final String ACCOUT_LOGIN_FAIL = "登录失败,帐号或密码错误!";
	public static final String INPUT_USERNAME_PASSWORD = "请输入用户名和密码!";
	public static final String INPUT_VERIFY_CODE_ERROR = "验证码错误!";
	public static final String SAVE_SUCCESS = "添加成功";
	public static final String SAVE_ERROR = "添加失败,或数据已经存在!";
	public static final String QUERY_LIST_NOT = "无数据";
	public static final String SYS_BUSY = "系统正忙";
	public static final String IMPORT_SUCCESS = "文件导入成功";
	public static final String IMPORT_ERROR = "文件导入失败";
	public static final String NOT_STAT_DATA = "选定时间内,无统计信息!";
	public static final String SAVE_TIME_AD_EXIST = "添加失败,设定时间段内已存在其他广告!";
	public static final String SAVE_CHANNEL_AD_EXIST = "添加失败,该栏目下的广告还存在未过期的新闻通栏!";
	public static final String UPDATE_CHANNEL_AD_EXIST = "跟新失败,该栏目下的广告还存在未过期的新闻通栏!";

	public static final String VERIFY_SUCCESS = "审核成功";
	public static final String VERIFY_ERROR = "审核失败";
	public static final String VERIFY_NO_PASS = "审核不通过";

	public static final String IS_EXIST = "已经存在的数据";
	public static final String IS_EXIST_SEQ = "位置已经存在,请重新输入位置";
	public static final String OLD_PWD_ERROR = "原始密码错误";

	public static final String PUSH_SUCCESS = "推送成功";
	public static final String PUSH_ERROR = "推送失败";

	public static final String SET_TOP_ERROR = "设置头条失败";
	public static final String PUSH_NEWS_NOT_EXIST = "推送新闻不存在";

	public static final String PUSH_NEWS_INPUT_ERROE = "新闻ID不能输入非数字!";
	/** **************************** 操作信息end ******************************** */
	
	
	/**
	 * 设置服务根目录
	 * 
	 * @param rootDir
	 */
	public void setRootDir(String rootDir);

	/**
	 * 设置服务根路径
	 * 
	 * @param rootPath
	 */
	public void setRootPath(String rootPath);
	
	/**
	 * 获取系统参数值
	 * 
	 * @param id
	 *            系统参数ID
	 * @return
	 */
	public String getSystemParam(String id);
	
	/**
	 * 获取有效的角色列表
	 * @param roleGroupId 角色分组ID
	 * 
	 * @return
	 */
	public List<Role> getValidRoles(String roleGroupId);

}
