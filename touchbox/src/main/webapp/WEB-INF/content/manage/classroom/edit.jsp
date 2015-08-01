<%@ page language="java" pageEncoding="UTF-8"%><%@ include
	file="../../includes/head_bootstrap.jsp"%>
<%@ include	file="../../includes/assets/angularjs.jsp"%>
<%@ include	file="../../includes/assets/jeasyui.jsp"%>
<%@ include	file="../../includes/assets/ueditor.jsp"%>
<%@ include	file="../../includes/assets/uploadify.jsp"%>

<c:set var="isModify" value="${not empty dataId}"/>
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
    	 
		G.uploadify({
    		index:1,
    		formFileName:'instance.videoImage'
    	});
     	
    	G.uploadify({
    		index:2,
    		formFileName:'instance.videoSmallImage',
    		formData:{
    		//	'widthHeightLimit' : '[{w:300,h:300}]'
    		}
    	});
    	G.uploadify({
    		index:3,
    		formFileName:'instance.videoUrl',
    		fileSizeLimit:'20MB',
    		formData:{
				type:'video'
    		}
    	});
    	G.uploadify({
    		index:4,
    		formFileName:'instance.imageUrl',
    		formData:{
    		//	'widthHeightLimit' : '[{w:300,h:300}]'
    		}
    	});
    	
    	// 需要延时执行的程序
    	setTimeout(function(){

    	},1000);
    	
     });     
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData({
		data:{productId:0,periodNum:0,description:''},fetchParams:[{url:'${ctx}/api/product/find?pagesize=100',scopeField:'products',async:false}]
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/classroom/list">
        <c:set var="actionName" value="新增"/>
        <c:if test="${isModify}">
        <input type="hidden" name="instance.classroomId" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/classroom/fetchItem?dataId=${dataId}">
        <input type="hidden" name="instance.status" id="status" value="{{o.data.status}}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/classroom/save">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">课堂管理</a></li>
            <li class="active">${actionName }</li>
            <li class="top-bar-right">
				<INPUT class="btn btn-primary" value="发布" type="button" ng-click="save({status:{id:'status',value:1}})">&nbsp;
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
                <TD align=right class="input-label"><label>课堂名称：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="name" name="instance.name" ng-model="o.data.name" value="" style="width:250px"
                           placeholder="请输入 课堂名称"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>所属产品：</label></TD>
                <TD class="input-col">                
                    <div class="row">
						<div class="col-xs-3">
	               		<select class="form-control" id="productId" name="instance.productId" ng-model="o.data.productId">
		                	<c:if test="${!isModify}"><option value="0">请选择</option></c:if>
	               			<option value="{{item.productId}}" ng-repeat="item in products.list" <c:if test="${isModify}">ng-if="item.productId==o.data.productId"</c:if>>{{item.name}}</option>	               			
	               		</select>
						</div>
					</div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>期数：</label></TD>
                <TD class="input-col">
                	<c:if test="${isModify }"><input class="form-control" type="text" id="periodNum" name="instance.periodNum" value="{{o.data.periodNum}}" style="width:40px" readOnly/>
					</c:if><c:if test="${!isModify }">
                    <div class="row">
						<div class="col-xs-3">
		               		<select class="form-control" id="periodNum" name="instance.periodNum" ng-model="o.data.periodNum">
		                		<option value="0">请选择</option>
						<s:bean name="org.apache.struts2.util.Counter">
							<s:param name="first" value="1" />
							<s:param name="last" value="30" />
							<s:iterator>
								<option value="<s:property />"><s:property /></option>
							</s:iterator>
						</s:bean>
		               		</select>
					    </div>
					</div></c:if>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>是否免费：</label></TD>
                <TD class="input-col">
					<input ng-click="o.data.free=true" name="instance.free" type="radio" value="true" ng-checked="o.data.free"> <span class="glyphicon glyphicon-ok" style="color:green" aria-hidden="true"></span>
					<input ng-click="o.data.free=false" name="instance.free" type="radio" value="false" ng-checked="!o.data.free"/> <span class="glyphicon glyphicon-remove" style="color:red" aria-hidden="true"></span>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>视频大图：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify1" /></div>
                	<div class="col-xs-6"><span>格式：png/jpg/jpeg；尺寸：800*600</span></div>
					</div>
					<div id="fileQueue1"></div>
					<div id="fileShow1"><span ng-show="o.data.videoImage"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.videoImage}}"/>
					<input type='hidden' name='instance.videoImage' value='{{o.data.videoImage}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>视频小图：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify2" /></div>
                	<div class="col-xs-5"><span>格式：png/jpg/jpeg；尺寸：150*150</span></div>
					</div>
					<div id="fileQueue2"></div>
 					<div id="fileShow2"><span ng-show="o.data.videoSmallImage"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.videoSmallImage}}"/>
					<input type='hidden' name='instance.videoSmallImage' value='{{o.data.videoSmallImage}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>课堂视频：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify3" /></div>
                	<div class="col-xs-5"><span>不超过20M；格式：mp4</span></div>
					</div>
					<div id="fileQueue3"></div>
 					<div id="fileShow3"><span ng-show="o.data.videoUrl"><a href="${RESOURCE_DIR_URL}{{o.data.videoUrl}}">${RESOURCE_DIR_URL}{{o.data.videoUrl}}</a>
					<input type='hidden' name='instance.videoUrl' value='{{o.data.videoUrl}}'/>
					<a title='删除视频' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>课堂介绍：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify4" /></div>
                	<div class="col-xs-5"><span>格式：png/jpg/jpeg；尺寸：300*300</span></div>
					</div>
					<div id="fileQueue4"></div>
 					<div id="fileShow4"><span ng-show="o.data.imageUrl"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.imageUrl}}"/>
					<input type='hidden' name='instance.imageUrl' value='{{o.data.imageUrl}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                
                	<textarea class="form-control" rows="10" cols="100" id="description" name="instance.description">{{o.data.description}}</textarea>
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>
</div>
</BODY>
</HTML>