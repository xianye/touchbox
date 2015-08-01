<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/information/find?type=${type}" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/information/updateStatus?type=${type}" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/information/edit?type=${type}" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入关键字"/>
						</TD>
						<TD style="padding-left: 10px">
						<INPUT class="btn btn-primary" value="查询" type="button" ng-click="pageQuery(1).current()" />
						<INPUT class="btn btn-primary" value="添加" type="button" ng-click="editPage()"/></TD>
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
							<td>标题</td>
							<td>编辑人</td>
							<td width="180">编辑时间</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.id}}
							</td>
							<td>{{item.title}}</td>
							<td>{{item.authorName}}</td>
							<td>{{item.editTime}}</td>
							<td><span ng-if="item.status==0">未发布</span><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span><span ng-if="item.status==3">已撤稿</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.id}})">编辑</a> <a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.id':item.id,'instance.status':3},'confirmText':'确认撤稿？'})" ng-if="item.status==1">撤稿</a><a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.id':item.id,'instance.status':1},'confirmText':'确认发布？'})" ng-if="item.status!=1">发布</a> <a
									href="javascript:void(0);"
									ng-click="del({'instance.id':item.id,'statusKey':'instance.status'})">删除</a></span></td>
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
