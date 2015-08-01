<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

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

</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData()">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/user/list">
        <c:if test="${not empty dataId}">
        <input type="hidden" name="userId" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/user/fetchUser?userId=${dataId}">
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/user/resetPassword">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">客户管理</a></li>
            <li class="active">查看</li>
            <li class="top-bar-right">
            	<INPUT class="btn btn-primary" value="重置密码" type="button" ng-click="save({afterSave:{ajax:true},scopeField:'resetPass',alertOpts:{messages:{success:'处理成功！'}}})">&nbsp;
                <INPUT class="btn btn-primary" value="返回" type="button" ng-click="goBack()"/>            
            </li>
        </ol>
        <TABLE class="normalTable" border=0 cellSpacing=0 cellPadding=10>
            <TBODY>
            <TR>
                <TD colspan="4" align="left">
                    <div align="left" class="col-sm-offset-2 col-sm-12" id="alert1"></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>登录账号：</label></TD>
                <TD class="input-col">
                    {{o.data.username}}
                </TD>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">
               		{{o.data.mobile}}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>家长姓名：</label></TD>
                <TD class="input-col" colspan="3">
                    {{o.data.name}}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>注册时间：</label></TD>
                <TD class="input-col" colspan="3">
                	{{o.data.createTime}}
				</TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
</div>
</BODY>
</HTML>
