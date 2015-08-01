<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>

<script language="JavaScript" type="text/javascript">
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="saveUrl" value="${ctx }/api/user/updatePassword">
        <input type="hidden" name="userId" value="${SESSION_ADMIN.userId}">

        <ol class="breadcrumb">
            <li class="active">修改密码<span style="color:red" ng-if="o.errorCode==0">【修改成功】</span><span style="color:red" ng-if="o.errorCode&&o.errorCode!=0">【{{o.data.errorMsg}}】</span></li>
        </ol>
        <TABLE class="normalTable" border=0 cellSpacing=0 cellPadding=10>
            <TBODY>
            <TR>
                <TD align=right class="input-label"><label>用户名：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.username}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.mobile}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>旧密码：</label></TD>
                <TD class="input-col"><input class="form-control" type="password" id="password" name="password" value="" style="width:250px"
                           placeholder="请输入 旧密码"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>新密码：</label></TD>
                <TD class="input-col"><input class="form-control" type="password" id="newPassword" name="newPassword" value="" style="width:250px"
                           placeholder="请输入 新密码"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>确认密码：</label></TD>
                <TD class="input-col"><input class="form-control" type="password" id="confirmPassword" value="" style="width:250px"
                           placeholder="请输入 确认密码"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label></label></TD>
                <TD class="input-col" colspan="3"><INPUT class="btn btn-primary" value="确认修改" type="button" ng-click="updatePassword()">
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
</div>
</BODY>
</HTML>