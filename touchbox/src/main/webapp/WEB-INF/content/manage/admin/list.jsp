<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController" ng-init="fetchData({
		fetchParams:[{url:'${ctx}/api/role/find?roleGroupId=${roleGroupId}',scopeField:'roles'}]
	})">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/user/findAdmin" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/user/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/admin/edit?roleGroupId=${roleGroupId}" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入手机号码或姓名"/>
						<input type="hidden" name="roleGroupId" value="${roleGroupId}"/>
						</TD>
						<TD style="padding-left: 10px"><label>所属角色：</label></TD>
		                <TD>
		               		<select class="form-control" id="roleId" name="roleId" style="width:200px">
		                		<option value="0">全部</option>
		               			<option value="{{item.roleId}}" ng-repeat="item in roles.list">{{item.name}}</option>
		               		</select>
		                </TD>
						<TD style="padding-left: 10px">
						<INPUT class="btn btn-primary" value="查询" type="button" ng-click="pageQuery(1).current()" />
						<c:if test="${!isSeller}">
						<INPUT class="btn btn-primary" value="添加" type="button" ng-click="editPage()"/></c:if></TD>
					</TR>
				</TBODY>
			</TABLE>

			<!-- 内容主体列表数据，通过JS模板生成 -->
			<div id="contentMain" style="display: none">
				<table class="table table-bordered" style="margin-top: 5px"
					cellspacing="0" cellpadding="0">
					<tbody>
						<tr height="30" align="center" bgcolor="#eaeaea">
							<td width="80" align="left">
								<%--<input type="checkbox"
								action="checkAll" value="0" child-name="dataIds">--%> 编号
							</td>
							<c:if test="${isSeller}"><td>上级ID</td>
							<td>提成比例</td>
							</c:if>
							<td>用户名</td>
							<td>姓名</td>
							<td>所属角色</td>
							<td width="180">创建时间</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.userId}}
							</td>
							<c:if test="${isSeller}"><td>{{item.parentUserId}}</td>
							<td>{{item.salesCut}}</td>
							</c:if>
							<td>{{item.account.username}}</td>
							<td>{{item.name}}</td>
							<td>{{item.roles[0].name}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span></td>
							<td><span ng-if="item.status==1"><c:if test="${isSeller}"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.userId}})">审核</a></c:if><c:if test="${!isSeller}"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.userId}})">查看或编辑</a></c:if> <a
									href="javascript:void(0);"
									ng-click="del({'role.roleId':item.roleId,'statusKey':'role.status'})">删除</a></span></td>
						</tr>

						<!-- 底部分页模板 -->
						<%@ include file="../../includes/paginated_bottom_bootstrap.jsp"%>
					</tbody>
				</table>

			</div>

			<!-- 页面加载效果start -->
			<%@ include file="../../includes/pageLoading.jsp"%>
			<!-- 页面加载效果end -->

		</form>
	</div>
</BODY>
</HTML>
