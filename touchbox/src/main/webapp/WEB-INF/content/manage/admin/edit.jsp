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
    	var validateFields = ['admin.username','roleId','admin.name','admin.mobile','admin.email'];
    	 
    	//console.log($scope);
    	if($scope.o.data.roles[0].roleId==0||$scope.frm.$invalid){
        	for(var i=0;i<validateFields.length;i++){
    			if($scope.frm[validateFields[i]]){
    	    		$scope.frm[validateFields[i]].$pristine = false;
    			}
        	}
    		return false;
    	}
    	
        return true;
    }

	jQuery(function(){
		//jQuery("#mobile").attr("ng-pattern",G.validateFormPattern.mobile);
	});
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData({
		data:{sex:0,roles:[{roleId:0}]},
		fetchParams:[{url:'${ctx}/api/role/find?roleGroupId=${roleGroupId}',scopeField:'roles',async:false}]
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="formValid" value="{{frm.$valid}}">
        <input type="hidden" id="backUrl" value="${ctx }/manage/admin/list?roleGroupId=${roleGroupId}">
        <c:set var="actionName" value="新增"/>
        <c:set var="isModify" value="${not empty dataId}"/>
        <c:if test="${isModify}">
        <input type="hidden" name="userId" id="dataId" value="${dataId}">
        <input type="hidden" name="admin.username" value="{{o.data.username}}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/user/fetchAdmin?userId=${dataId}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/user/saveAdmin">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">${modelName}管理</a></li>
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
                    <div align="left" class="col-sm-offset-2 alert alert-warning alert-dismissible" id="alert1" style="margin-top:40px;margin-bottom:0px;width:300px;display:none;">
                    	<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  						<li>提交的数据有误，请检查!</li>
                    </div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>用户名：</label></TD>
                <TD class="input-col"><c:if test="${isModify}">{{o.data.username}}</c:if><c:if test="${!isModify}">
					<div class="form-group has-feedback" ng-class="{'has-success' : !frm['admin.username'].$pristine && frm['admin.username'].$valid, 'has-error' : !frm['admin.username'].$pristine && frm['admin.username'].$invalid }">
						<input class="form-control" type="text" id="username" name="admin.username" ng-model="o.data.username" value="" style="width:250px"
                           placeholder="请输入 用户名..." required ng-pattern="/^[0-9a-z]{4,11}$/" ng-blur="frm['admin.username'].$pristine=false"/>
                       	<span ng-show="!frm['admin.username'].$pristine && frm['admin.username'].$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                       	<span ng-show="!frm['admin.username'].$pristine && frm['admin.username'].$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </div>
                    <div class="errorMsg" ng-messages="frm['admin.username'].$error" ng-if="!frm['admin.username'].$pristine">
						<div ng-message="required" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 必填项</div>
                        <div ng-message="pattern" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 用户名需为字母加数字且长度大于等于4小于等于11的字符</div>
                    </div>

                 	</c:if>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>所属角色：</label></TD>
                <TD class="input-col">
                	<div class="form-group">
               		<select class="form-control" id="roleId" name="roleId" style="width:200px" ng-model="o.data.roles[0].roleId" ng-blur="frm['roleId'].$pristine=false">
                		<option value="0">请选择</option>
               			<option value="{{item.roleId}}" ng-repeat="item in roles.list">{{item.name}}</option>
               		</select>
               		</div>
                    <div class="errorMsg" ng-if="!frm['roleId'].$pristine" ng-show="o.data.roles[0].roleId==0">
						<div class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 必填项，请选择所属角色</div>
                    </div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>姓名：</label></TD>
                <TD class="input-col">
					<div class="form-group has-feedback" ng-class="{'has-success' : !frm['admin.name'].$pristine && frm['admin.name'].$valid, 'has-error' : !frm['admin.name'].$pristine && frm['admin.name'].$invalid }">
                    	<input class="form-control" type="text" id="name" name="admin.name" ng-model="o.data.name" value="" style="width:250px"
                           placeholder="请输入姓名" required ng-blur="frm['admin.name'].$pristine=false" />
                       	<span ng-show="!frm['admin.name'].$pristine && frm['admin.name'].$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                       	<span ng-show="!frm['admin.name'].$pristine && frm['admin.name'].$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </div>
                    <div class="errorMsg" ng-messages="frm['admin.name'].$error" ng-if="!frm['admin.name'].$pristine">
                        <div ng-message="required" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 必填项</div>
                    </div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>性别：</label></TD>
                <TD class="input-col">
               		<select class="form-control" id="sex" name="admin.sex" ng-model="o.data.sex" style="width:200px">
                		<option value="0">请选择</option>
                		<option value="1">男</option>
                		<option value="2">女</option>
               		</select>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">
					<div class="form-group has-feedback" ng-class="{'has-success' : !frm['admin.mobile'].$pristine && frm['admin.mobile'].$valid, 'has-error' : !frm['admin.mobile'].$pristine && frm['admin.mobile'].$invalid }">
                    	<input class="form-control" type="text" id="mobile" name="admin.mobile" ng-model="o.data.mobile" value="" style="width:250px" 
                           placeholder="请输入手机号码" required ng-pattern="/^1[0-9]{10}$/" ng-blur="frm['admin.mobile'].$pristine=false"/>
                       	<span ng-show="!frm['admin.mobile'].$pristine && frm['admin.mobile'].$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                       	<span ng-show="!frm['admin.mobile'].$pristine && frm['admin.mobile'].$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </div>
                    <div class="errorMsg" ng-messages="frm['admin.mobile'].$error" ng-if="!frm['admin.mobile'].$pristine">
                        <div ng-message="required" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 必填项</div>
                        <div ng-message="pattern" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 手机号码必须为11位数字的字符</div>
                    </div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>E-MAIL：</label></TD>
                <TD class="input-col">
					<div class="form-group has-feedback" ng-class="{'has-success' : !frm['admin.email'].$pristine && frm['admin.email'].$valid, 'has-error' : !frm['admin.email'].$pristine && frm['admin.email'].$invalid }">
                    	<input class="form-control" type="email" id="email" name="admin.email" ng-model="o.data.email" value="" style="width:250px"
                           placeholder="请输入邮箱地址" required ng-pattern="/^\w+@\w+(\.[a-z]+)+$/" ng-blur="frm['admin.email'].$pristine=false"/>
                       	<span ng-show="!frm['admin.email'].$pristine && frm['admin.email'].$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                       	<span ng-show="!frm['admin.email'].$pristine && frm['admin.email'].$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    </div>
                    <div class="errorMsg" ng-messages="frm['admin.email'].$error" ng-if="!frm['admin.email'].$pristine">
                        <div ng-message="required" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 必填项</div>
                        <div ng-message="pattern" class="label label-danger"><span class="glyphicon glyphicon-remove-sign"></span> 邮箱地址错误，请输入正确的邮箱地址</div>
                    </div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>备注：</label></TD>
                <TD class="input-col">
                    <textarea class="form-control" rows="2" id="memo" name="admin.memo" ng-model="o.data.memo" placeholder="请输入备注..."
                              style="width:400px"></textarea>
                </TD>
            </TR>
            </TBODY>
        </TABLE>
    </form>
</div>
</BODY>
</HTML>