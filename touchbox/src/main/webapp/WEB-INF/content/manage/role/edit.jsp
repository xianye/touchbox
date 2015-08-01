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
        var $roleName = $("#roleName");
        $roleName.val($.trim($roleName.val()));
        if ($roleName.val() == "") {
            alert("角色名不能为空");
            return false;
        }
        return true;
    }

	jQuery(function(){
		var resourceNameKey = "resourceIds";
		$('#resTree').tree({
			onLoadSuccess:function(node,data){// 加载成功，生成form表单数据
				//console.log({node:node,data:data});
				for(var i=0;i<data.length;i++){
					var node1 = data[i],
						nodeId=node1.id,
						resourceIdsKey = resourceNameKey+nodeId;
						
					if(node1.attributes.level==0){
						for(var j=0;j<node1.children.length;j++){
							node2 = node1.children[j];
							if(node1['_checked']||node2["_checked"]){
								nodeId=node2.id;
								resourceIdsKey = resourceNameKey+nodeId;
								if(!document.getElementById(resourceIdsKey)){
									jQuery("#frm").append("<input type=\"hidden\" id=\""+resourceIdsKey+"\" name=\""+resourceNameKey+"\" value=\""+nodeId+"\"/>");
								}
							}
						}		
					}else{
						if(document.getElementById(resourceIdsKey)) break;
						jQuery("#frm").append("<input type=\"hidden\" id=\""+resourceIdsKey+"\" name=\""+resourceNameKey+"\" value=\""+nodeId+"\"/>");
					}
				}
			},
			onCheck: function(node,checked){// 树点击选中事件处理
				var nodeId=node.id,
					resourceIdsKey = resourceNameKey+nodeId;
				//alert(checked);
				if(document.getElementById(resourceIdsKey)){
					jQuery("#"+resourceIdsKey).remove();
				}
				if(checked){
					if(node.attributes.level==0){
						for(var i=0;i<node.children.length;i++){
							var node1 = node.children[i],
								nodeId=node1.id;
							resourceIdsKey = resourceNameKey+nodeId;
							if(!document.getElementById(resourceIdsKey)){
								jQuery("#frm").append("<input type=\"hidden\" id=\""+resourceIdsKey+"\" name=\""+resourceNameKey+"\" value=\""+nodeId+"\"/>");
							}
						}
					}else{
						jQuery("#frm").append("<input type=\"hidden\" id=\""+resourceIdsKey+"\" name=\""+resourceNameKey+"\" value=\""+nodeId+"\"/>");
					}
				}
				//console.log(node);
			}
		});
	});
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData({
						data:{type:1,groupId:0}
					})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/role/list">
        <c:set var="actionName" value="新增"/>
        <c:if test="${not empty dataId}">
        <input type="hidden" name="role.roleId" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/role/fetchRole?dataId=${dataId}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/role/save">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">角色管理</a></li>
            <li class="active">${actionName}</li>
            <li class="top-bar-right">
            	<INPUT class="btn btn-primary" value="保存" type="button" ng-click="save()" ng-if="o.data.groupId!=1">&nbsp;
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
                           placeholder="请输入角色名..." ng-if="o.data.groupId!=1"/>
                    <span ng-if="o.data.groupId==1">{{o.data.name}}</span>
                </TD>
                <TD align=right class="input-label"><label>分类：</label></TD>
                <TD class="input-col">
               		<select class="form-control" id="roleType" name="role.type" ng-model="o.data.type" ng-if="o.data.groupId!=1">
               			<option value="1">内部</option>
               			<option value="2">外部合作</option>
               		</select>
                    <span ng-if="o.data.groupId==1">{{o.data.typeName}}</span>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>分组：</label></TD>
                <TD class="input-col">
               		<select class="form-control" id="roleGroupId" name="role.groupId" ng-model="o.data.groupId" ng-if="o.data.groupId!=1">
               			<option value="0">不限</option>
               			<option value="2">销售员</option>
               			<option value="3">客服</option>
               		</select>
                    <span ng-if="o.data.groupId==1">{{o.data.groupName}}</span>
                </TD>
                <TD align=right class="input-label"><label>备注：</label></TD>
                <TD class="input-col">
                    <textarea class="form-control" rows="2" id="roleMemo" name="role.memo" ng-model="o.data.memo" placeholder="请输入备注..."
                              style="width:400px" ng-if="o.data.groupId!=1"></textarea>
                    <span ng-if="o.data.groupId==1">{{o.data.memo}}</span>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>权限设置：</label></TD>
                <TD class="input-col" colspan="3">
                	<div class="easyui-panel" style="padding: 5px">
						<ul id="resTree" class="easyui-tree" data-options="url:'${ctx }/api/role/getResources?format=jeasyui.tree<c:if test="${not empty dataId}">&role.roleId=${dataId}</c:if>',method:'get',animate:true,checkbox:true">
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
