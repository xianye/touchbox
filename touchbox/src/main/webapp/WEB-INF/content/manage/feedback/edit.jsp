<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>
<%@ include	file="../../includes/assets/ueditor.jsp"%>
<%@ include	file="../../includes/assets/uploadify.jsp"%>

<script language="JavaScript" type="text/javascript">
	/**
	 * 提交表单检查
	 *
	 * @param $scope 控制器缓存空间
	 * @returns {boolean}
	 */
	function checkForm($scope) {
		//TODO 表单验证
        return true;
    }

	jQuery(function() {

     });
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData({
		data:{type:1}
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/feedback/list?entityType=${entityType}">
        <c:set var="actionName" value="新增"/>
        <c:set var="isModify" value="${not empty dataId}"/>
        <c:if test="${isModify}">
        <input type="hidden" name="instance.id" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/feedback/fetchItem?dataId=${dataId}">
        <input type="hidden" name="instance.status" id="status" value="{{o.data.status}}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/feedback/save?entityType=${entityType}">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">${modelName}</a></li>
            <li class="active">${actionName }</li>
            <li class="top-bar-right">
            	<INPUT class="btn btn-primary" value="保存" type="button" ng-click="save()">&nbsp;
                <INPUT class="btn btn-primary" value="返回" type="button" ng-click="goBack()"/>            
            </li>
        </ol>
        <TABLE class="normalTable" border=0 cellSpacing=0 cellPadding=10>
            <TBODY>
            <TR>
                <TD colspan="2" align="left">
                    <div align="left" class="col-sm-offset-2 col-sm-12" id="alert1"></div>
                </TD>
            </TR>
		<c:if test="${entityType eq 1}"><!-- 问题提报附加字段  -->
            <TR>
                <TD align=right class="input-label"><label>咨询分类：</label></TD>
                <TD class="input-col">
					<select class="form-control" id="type" name="instance.type" style="width:250px" ng-model="o.data.type">
		            	<option value="1">产品及物流</option>
		            	<option value="2">销售员</option>
		            	<option value="3">网站与 app使用</option>
		            	<option value="4">其他--退款、其他问题、合作</option>
		            </select>
                </TD>
            </TR>
		</c:if>
            <TR>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="mobile" name="instance.mobile" ng-model="o.data.mobile" value="" style="width:250px"
                           placeholder="请输入咨询手机号码"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>家长姓名：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="name" name="instance.name" ng-model="o.data.name" value="" style="width:250px"
                           placeholder="请输入咨询人姓名"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>内容描述：</label></TD>
                <TD class="input-col">
					<textarea class="form-control" name="instance.content" rows="3" cols="70" ng-model="o.data.content" style="width:450px" placeholder="请输入内容"></textarea>
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
    
</div>
</BODY>
</HTML>