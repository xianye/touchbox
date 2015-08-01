<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/user/findUser" /> 
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/user/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/user/view" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入关键字"/>
						</TD>
						<TD style="padding-left: 10px">
						<INPUT class="btn btn-primary" value="查询" type="button" ng-click="pageQuery(1).current()" />
						</TD>
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
							<td>手机号码</td>
							<td>家长姓名</td>
							<td width="180">注册时间</td>
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
							<td>{{item.mobile}}</td>
							<td>{{item.name}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="!item.freeze">正常</span><span
								ng-if="item.freeze">已冻结</span></td>
							<td><span ng-if="!item.freeze"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.userId}})">查看详情</a> <a
									href="javascript:void(0);"
									ng-click="freeze({'userId':item.userId,'statusKey':'user.freeze'})">冻结</a></span><span ng-if="item.freeze"><a
									href="javascript:void(0);"
									ng-click="activate({'userId':item.userId,'statusKey':'user.freeze'})">激活</a></span></td>
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
