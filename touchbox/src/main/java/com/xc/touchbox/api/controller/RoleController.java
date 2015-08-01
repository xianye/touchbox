package com.xc.touchbox.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.Resource;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.ListResponse;
import com.xc.touchbox.model.view.ResourceView;
import com.xc.touchbox.model.view.RoleView;
import com.xc.touchbox.service.RoleService;
import com.xc.touchbox.service.UserService;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/role")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class RoleController extends BaseAction {

	private static final Logger log = Logger.getLogger(RoleController.class);

	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	private Role role;// 输入role对象数据
	private String[] resourceIds;// 权限资源ID数组
	private String roleGroupId;// 角色分组ID

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	/**
	 * 查询角色列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "find", results = { @Result(name = "success", type = "json", params = {
			"root", "listResp", "excludeNullProperties", "true" }) })
	public String find() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				List list = roleService.getValidRoles(roleGroupId);

				if (list != null) {
					listResp = new ListResponse(list, RoleView.class);
					return SUCCESS;
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);
		return ERROR;
	}

	/**
	 * 获取指定ID的角色数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "fetchRole", results = { @Result(name = "success", type = "json", params = {
			"root", "getDataResp", "excludeNullProperties", "true" }) })
	public String fetchRole() throws Exception {
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&dataId=").append(dataId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Role a = (Role) roleService.get(Role.class,
						NumberUtils.toInt(dataId));

				if (a != null) {
					getDataResp = new GetDataResponse(a, RoleView.class);

					return SUCCESS;
				} else {
					baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL);
				}
			} else {
				baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}
		return ERROR;
	}

	/**
	 * 保存角色数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "save", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String save() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功

				roleService.saveRole(role, resourceIds);

				return SUCCESS;
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;

	}

	/**
	 * 更新角色状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "updateStatus", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String updateStatus() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();

				if (role.getRoleId() != null) {

					List whereParamVals = new ArrayList();
					whereParamVals.add(role.getRoleId());
					Map<String, Object> setParams = new HashMap<String, Object>();
					setParams.put("status", role.getStatus());
					setParams.put("updateTime", date);
					int updateCount = roleService.executeUpdate(Role.class,
							setParams, " where roleId=?", whereParamVals);
					if (updateCount > 0) {// 更新成功
						return SUCCESS;
					}

				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;

	}

	/**
	 * 获取所有资源权限
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getResources", results = {
			@Result(name = "success", type = "json", params = { "root",
					"listResp", "excludeNullProperties", "true" }),
			@Result(name = "list", type = "json", params = { "root", "list",
					"excludeNullProperties", "true" }) })
	public String getResources() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				boolean allCheck = false;
				List<String> roleResourceIds = new ArrayList<String>();
				if (role != null && role.getRoleId() > 0) {
					role = (Role) roleService.get(Role.class, role.getRoleId());
					allCheck = role.getGroupId()==1;
					roleResourceIds = roleService.getRoleResourceIds(role
							.getRoleId());
				}
				List<Resource> list = roleService.getValidResources();

				if (list != null) {

					if (format != null
							&& CDAOTemplate.CONSTANT_FORMAT_JEASYUI_TREE
									.equalsIgnoreCase(format)) {
						List<Map<String, Object>> jeasyuiTreeDatas = new ArrayList<Map<String, Object>>();
						Map<String, Map<String, Object>> jeasyuiTreeMap = new HashMap<String, Map<String, Object>>();
						for (Resource res : list) {
							String resId = res.getResourceId();
							if (res.getLevel() == 0) {
								if (!jeasyuiTreeMap.containsKey(resId)) {
									jeasyuiTreeMap.put(resId,
											new HashMap<String, Object>());
								}

								Map<String, Object> parent = jeasyuiTreeMap
										.get(resId);

								parent.put("id", resId);
								parent.put("text", res.getName());
								parent.put("children",
										new ArrayList<Map<String, Object>>());
								Map<String, Object> attributes = new HashMap<String, Object>();
								attributes.put("level", res.getLevel());
								parent.put("attributes", attributes);
								if (allCheck||roleResourceIds.contains(resId)) {
									parent.put("checked", true);
								}
								jeasyuiTreeDatas.add(parent);
							} else if (StringUtils
									.isNotEmpty(res.getParentId())) {
								List<Map<String, Object>> children = (List<Map<String, Object>>) jeasyuiTreeMap
										.get(res.getParentId()).get("children");
								if (children == null) {
									children = new ArrayList<Map<String, Object>>();
								}
								Map<String, Object> child = new HashMap<String, Object>();
								child.put("id", resId);
								child.put("text", (res.getType() == 2 ? "【功能】"
										: "【菜单】") + res.getName());
								if (res.getType() == 2) {
									child.put("iconCls", "icon-edit");
								}
								Map<String, Object> attributes = new HashMap<String, Object>();
								attributes.put("level", res.getLevel());
								attributes.put("type", res.getType());
								child.put("attributes", attributes);
								if (allCheck||roleResourceIds.contains(resId)) {
									child.put("checked", true);
								}
								children.add(child);

								jeasyuiTreeMap.get(res.getParentId()).put(
										"children", children);
							}
						}

						this.list = jeasyuiTreeDatas;

						return "list";
					} else {
						listResp = new ListResponse(list, ResourceView.class);
						return SUCCESS;

					}
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);
		return ERROR;
	}

}
