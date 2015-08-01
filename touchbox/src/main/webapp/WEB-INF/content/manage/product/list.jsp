<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/product/find" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/product/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/product/edit" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入产品名称"/>
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
							<td>产品名称</td>
							<td>类型</td>
							<td>支持方案</td>
							<td>发货仓</td>
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
								value="{{item.roleId}}">--%> {{item.productId}}
							</td>
							<td>{{item.name}}</td>
							<td>{{item.productCat.name}}</td>
							<td><span ng-repeat="periodName in item.periodName"><span ng-if="$index>0">，</span>{{periodName}}</span></td>
							<td>{{item.deliveryWarehouseName}}</td>
							<td>{{item.adminName}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==0">未上架</span><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span><span ng-if="item.status==3">已下架</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.productId}})">编辑</a> <a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.productId':item.productId,'instance.status':3},'confirmText':'确认下架？'})" ng-if="item.status==1">下架</a><a
									href="javascript:void(0);"
									ng-click="updateStatus({params:{'instance.productId':item.productId,'instance.status':1},'confirmText':'确认上架？'})" ng-if="item.status!=1">上架</a> <a
									href="javascript:void(0);"
									ng-click="del({'instance.productId':item.productId,'statusKey':'instance.status'})">删除</a></span></td>
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
