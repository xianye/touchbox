<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

	<div class="gui-main" ng-controller="listController">
		<form onsubmit="return checkForm();" method="post" id="frm" name="frm">
			<input type="hidden" id="queryUrl" value="${ctx}/api/feedback/find?entityType=${entityType}" />
			<input type="hidden" id="updateStatusUrl" value="${ctx}/api/feedback/updateStatus?entityType=${entityType}" />
			<input type="hidden" id="editUrl" value="${ctx}/manage/feedback/edit?entityType=${entityType}" />
			<input type="hidden" id="replyUrl" value="${ctx}/manage/feedback/reply?entityType=${entityType}" ng-model="replyUrl"/>
			<TABLE border=0 cellSpacing=0 cellPadding=0>
				<TBODY>
					<TR>
						<TD>
						<INPUT class="form-control" id="keyword" name="keyword" placeholder="请输入关键字"/>
						</TD>
						<TD style="padding-left: 10px"><label>状态：</label></TD>
		                <TD>
		               		<select class="form-control" id="status" name="status" style="width:100px">
		                		<option value="-1">全部</option>
		                		<option value="0">未回复</option>
		                		<option value="1">已回复</option>
		               		</select>
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
							<td>描述</td>
						<c:if test="${entityType eq 1}"><!-- 问题提报附加字段  -->
							<td>类型</td>
						</c:if>
							<td>咨询人</td>
							<td>咨询手机号码</td>
							<td>处理人</td>
							<td width="180">处理时间</td>
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
							<td>{{item.content}}</td>
						<c:if test="${entityType eq 1}"><!-- 问题提报附加字段  -->
							<td>{{item.typeName}}</td>
						</c:if>
							<td>{{item.name}}</td>
							<td>{{item.mobile}}</td>
							<td>{{item.handlerName}}</td>
							<td>{{item.handleTime}}</td>
							<td><span ng-if="item.status==0">待回复</span><span ng-if="item.status==1">已回复</span><span
								ng-if="item.status==2">已删除</span></td>
							<td><span ng-if="item.status!=2"><a
									href="javascript:void(0);" ng-click="editPage({params:{'dataId':item.id}})">编辑</a> <a
									href="javascript:void(0);" ng-click="customPage({url:'#replyUrl',params:{'dataId':item.id}})">回复</a>{{replyUrl}} <a
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
