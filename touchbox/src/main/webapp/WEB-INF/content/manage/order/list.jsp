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
			<input type="hidden" id="queryUrl" value="${ctx}/api/order/find" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/order/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/order/view" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD><INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入关键字"/></TD>
						<TD style="padding-left: 10px"><label>日期：</label></TD>
						<TD><input id="startTime" name="startTime" type="text" class="easyui-datebox"/>到<input id="endTime" name="endTime" type="text" class="easyui-datebox"/></TD>
						<TD style="padding-left: 10px">
						<INPUT class="btn btn-primary" value="查询" type="button" ng-click="pageQuery(1).current()" />
						<INPUT class="btn btn-primary" value="导入" type="button"/>
						<INPUT class="btn btn-primary" value="导出" type="button"/></TD>
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
							<td>购买产品</td>
							<td>购买者姓名</td>
							<td>手机号码</td>
							<td>优惠码</td>
							<td>上层ID</td>
							<td>单价</td>
							<td>数量</td>
							<td>金额</td>
							<td>已寄送期数</td>
							<td width="180">购买日期</td>
							<td width="150">状态</td>
							<td width="150">操作</td>
						</tr>
						<tr class="g-tr tr-default" onmouseover="this.bgColor='#eaeaea';"
							onmouseout="this.bgColor='#FFFFFF';" align="center"
							bgcolor="#FFFFFF" ng-repeat="item in responseData.list">
							<td align="left">
								<%-- <input type="checkbox" name="dataIds"
								value="{{item.roleId}}">--%> {{item.orderId}}
							</td>
							<td>{{item.snapshot.name}}</td>
							<td>{{item.parentName}}</td>
							<td>{{item.parentMobile}}</td>
							<td>{{item.salesCode}}</td>
							<td>{{item.salesCode}}</td>
							<td>{{item.snapshot.price}}</td>
							<td>{{item.number}}</td>
							<td>{{item.fee}}</td>
							<td>{{item.deliveredPeriodNum}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==0">待支付</span><span ng-if="item.status==1">已支付</span><span
								ng-if="item.status==2">已删除</span><span ng-if="item.status==3">已发货</span><span 
								ng-if="item.status==4">已退款</span><span ng-if="item.status==5">已关闭</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.orderId}})">查看详细</a></span></td>
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
