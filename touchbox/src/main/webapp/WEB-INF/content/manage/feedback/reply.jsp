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
		data:{type:1},
		fetchParams:[{url:'${ctx }/api/feedback/replyFind?entityType=${entityType}&dataId=${dataId}',scopeField:'replyList'}]
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/feedback/list?entityType=${entityType}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/feedback/fetchItem?dataId=${dataId}">
        <input type="hidden" id="saveUrl" value="${ctx }/api/feedback/replySave?entityType=${entityType}">
        <input type="hidden" id="replyFindUrl" value="${ctx }/api/feedback/replyFind?entityType=${entityType}&dataId=${dataId}">
        <input type="hidden" name="reply.feedbackId" id="dataId" value="${dataId}">
        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">${modelName}</a></li>
            <li class="active">回复</li>
            <li class="top-bar-right">
            	&nbsp;
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
                <TD class="input-col">{{o.data.typeName}}
                </TD>
            </TR>
		</c:if>
            <TR>
                <TD align=right class="input-label"><label>手机号码：</label></TD>
                <TD class="input-col">{{o.data.mobile}}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>家长姓名：</label></TD>
                <TD class="input-col">{{o.data.name}}
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>内容描述：</label></TD>
                <TD class="input-col">{{o.data.content}}</TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>回复：</label></TD>
                <TD class="input-col" style="positon:relative">
                	<span style="position:absolute; left:680px;padding-top:38px;">
					<INPUT class="btn btn-primary" value="回复" type="button" ng-click="save({afterSave:{ajax:true,fetchParams:[{url:'#replyFindUrl',scopeField:'replyList'}]}})">
					</span>
                
					<textarea class="form-control" name="reply.content" rows="3" cols="70" style="width:500px" placeholder="请输入回复内容"></textarea>

                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>历史回复：</label></TD>
                <TD class="input-col" width="840">
<ul class="list-group">
  <li class="list-group-item" ng-repeat="item in replyList.list" style="positon:relative"><span class="badge" style="position:absolute; right:5px; bottom:5px">{{item.replierName}} 回复于 {{item.createTime}}</span> {{item.content}}<br/>&nbsp;</li>
</ul>
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
    
</div>
</BODY>
</HTML>