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
			<input type="hidden" id="queryUrl" value="${ctx}/api/delivery/find" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/delivery/updateStatus" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/delivery/view" />
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD><INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入关键字"/></TD>
						<TD style="padding-left: 10px"><label>日期：</label></TD>
						<TD><input id="startTime" name="startTime" type="text" class="easyui-datebox"/>到<input id="endTime" name="endTime" type="text" class="easyui-datebox"/></TD>
						<TD style="padding-left: 10px">
						<INPUT class="btn btn-primary" value="查询" type="button" ng-click="pageQuery(1).current()" /> </TD>
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
							<td>订单号</td>
							<td>姓名</td>
							<td>手机号码</td>
							<td>地址</td>
							<td>商品编码</td>
							<td>货品名称</td>
							<td>出库数量</td>
							<td width="180">创建日期</td>
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
							<td>{{item.orderId}}</td>
							<td>{{item.name}}</td>
							<td>{{item.mobile}}</td>
							<td>{{item.address}}</td>
							<td>{{item.goodsId}}</td>
							<td>{{item.itemName}}</td>
							<td>{{item.itemCount}}</td>
							<td>{{item.createTime}}</td>
							<td><span ng-if="item.status==0">待寄送</span><span ng-if="item.status==1">已寄送</span><span
								ng-if="item.status==2">已删除</span><span ng-if="item.status==3">发送中</span><span 
								ng-if="item.status==4">已取消</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.id}})">查看详细</a></span></td>
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
