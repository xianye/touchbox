<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/role/find" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/role/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/role/edit" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD><INPUT class="btn btn-primary"
							value="刷新" type="button" ng-click="pageQuery(1).current()" /> <INPUT
							class="btn btn-primary" ng-click="editPage()" value="添加"
							type="button" /></TD>
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
							<td>角色名</td>
							<td>分类</td>
							<td>分组</td>
							<td>备注</td>
							<td width="180">创建时间</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.roleId}}
							</td>
							<td>{{item.name}}</td>
							<td>{{item.typeName}}</td>
							<td>{{item.groupName}}</td>
							<td>{{item.memo}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span></td>
							<td><span ng-if="item.status==1">
									<span ng-if="item.groupId==1"><a
										href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.roleId}})">查看</a>
									</span><span ng-if="item.groupId!=1"><a
										href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.roleId}})">查看或编辑</a> <a
										href="javascript:void(0);"
										ng-click="del({'role.roleId':item.roleId,'statusKey':'role.status'})">删除</a></span>
								</span>
							</td>
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
