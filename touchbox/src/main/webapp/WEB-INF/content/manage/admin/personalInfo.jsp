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

        <ol class="breadcrumb">
            <li class="active">我的资料</li>
        </ol>
        <TABLE class="normalTable" border=0 cellSpacing=0 cellPadding=10>
            <TBODY>
            <TR>
                <TD align=right class="input-label"><label>用户名：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.username}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>所属角色：</label></TD>
                <TD class="input-col">
					${SESSION_ADMIN.roleList[0].name}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>姓名：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.name}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>性别：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.sexName}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.mobile}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>E-MAIL：</label></TD>
                <TD class="input-col">${SESSION_ADMIN.email}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>备注：</label></TD>
                <TD class="input-col" colspan="3">${SESSION_ADMIN.memo}
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
</div>
</BODY>
</HTML>
