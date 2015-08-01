<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<script type="text/javascript">
<!--

jQuery(function() {

	// 需要延时执行的程序
	setTimeout(function(){

	},1000);
	
 }); 

//-->
</script>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/order/freightFind" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/order/freightUpdateStatus" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD><INPUT class="btn btn-primary" value="刷新" type="button" ng-click="pageQuery(1).current()" /></TD>
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
							<td>出货仓</td>
							<td>收货地</td>
							<td>金额</td>
							<td width="180">创建日期</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.freightId}}
							</td>
							<td>{{item.deliveryWarehouseName}}</td>
							<td>{{item.cityName}}</td>
							<td>{{item.fee}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==1">正常</span><span
								ng-if="item.status==2">已删除</span></td>
							<td><span ng-if="item.status!=2"><a	href="javascript:void(0);"
									ng-click="del({'freight.freightId':item.freightId,'statusKey':'freight.status'})">删除</a></span></td>
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
