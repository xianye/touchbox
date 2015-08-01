<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/classroom/find" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/classroom/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/classroom/edit" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入课堂名称"/>
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
							<td>课堂名称</td>
							<td>所属产品</td>
							<td>期数</td>
							<td>是否免费</td>
							<td>编辑人</td>
							<td width="180">创建时间</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.classroomId}}
							</td>
							<td>{{item.name}}</td>
							<td>{{item.product.name}}</td>
							<td>第{{item.periodNum}}期</td>
							<td><span ng-if="item.free" class="glyphicon glyphicon-ok" style="color:green" aria-hidden="true"></span><span ng-if="!item.free" class="glyphicon glyphicon-remove" style="color:red" aria-hidden="true"></span></td>
							<td>{{item.adminName}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==0">未上架</span><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span><span ng-if="item.status==3">已下架</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.classroomId}})">编辑</a> <a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.classroomId':item.classroomId,'instance.status':3},'confirmText':'确认下架？'})" ng-if="item.status==1">下架</a><a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.classroomId':item.classroomId,'instance.status':1},'confirmText':'确认上架？'})" ng-if="item.status!=1">上架</a> <a
									href="javascript:void(0);"
									ng-click="del({'instance.classroomId':item.classroomId,'statusKey':'instance.status'})">删除</a></span></td>
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