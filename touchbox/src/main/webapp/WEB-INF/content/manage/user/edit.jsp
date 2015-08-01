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
        <input type="hidden" name="user.userId" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/user/fetchUser?dataId=${dataId}">
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/user/save">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">客户管理</a></li>
            <li class="active">新增</li>
            <li class="top-bar-right">
            	<INPUT class="btn btn-primary" value="保存" type="button" ng-click="save()">&nbsp;
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
                <TD align=right class="input-label"><label>角色名：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="roleName" name="role.name" ng-model="o.data.name" value="" style="width:250px"
                           placeholder="请输入角色名..."/>
                </TD>
                <TD align=right class="input-label"><label>分类：</label></TD>
                <TD class="input-col">
               		<select class="form-control" id="roleType" name="role.type" ng-model="o.data.type">
               			<option value="1">内部</option>
               			<option value="2">外部合作</option>
               		</select>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>备注：</label></TD>
                <TD class="input-col" colspan="3">
                    <textarea class="form-control" rows="2" id="roleMemo" name="role.memo" ng-model="o.data.memo" placeholder="请输入备注..."
                              style="width:400px"></textarea>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>权限设置：</label></TD>
                <TD class="input-col" colspan="3">
                	<div class="easyui-panel" style="padding: 5px">
						<ul class="easyui-tree" data-options="url:'${ctx }/api/role/getResources?format=jeasyui.tree',method:'get',animate:true,checkbox:true">
					<div style="width:100%;height:68px;padding-top:20px;">
						<div align="center">
							<img src="${ctx }/images/loading_b.gif"><br>
							<div align=center style="font-size: 12pt; color: #999999;">数据加载中...</div>
						</div>
					</div>
						</ul>
					</div>
				</TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
</div>
</BODY>
</HTML>
