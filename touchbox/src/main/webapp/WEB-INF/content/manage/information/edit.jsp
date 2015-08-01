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
    	// 需要延时执行的程序
    	setTimeout(function(){
    		ue.execCommand('fontfamily', '微软雅黑');
    		ue.execCommand('fontsize', '14px');
    		ue.execCommand('insertHtml', document.getElementById("myEditorContent").value);
    	},1000);
     });
</script>
<style>
</style>
<BODY class="gui-body" ng-app="mainApp">

<div class="gui-main" ng-controller="editController" ng-init="fetchData({
		data:{content:''}
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/information/list?type=${type}">
        <c:set var="actionName" value="新增"/>
        <c:set var="isModify" value="${not empty dataId}"/>
        <c:if test="${isModify}">
        <input type="hidden" name="instance.id" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/information/fetchItem?dataId=${dataId}">
        <input type="hidden" name="instance.status" id="status" value="{{o.data.status}}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/information/save?type=${type}">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">${modelName}</a></li>
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
                <TD colspan="2" align="left">
                    <div align="left" class="col-sm-offset-2 col-sm-12" id="alert1"></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>标题：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="title" name="instance.title" ng-model="o.data.title" value="" style="width:450px"
                           placeholder="请输入 标题"/>
                </TD>
            </TR>
            <c:if test="${type eq 2}"><!-- 相关新闻附加字段  -->
<script language="JavaScript" type="text/javascript">
	jQuery(function() {
		// 图片上传初始化
		G.uploadify({
        	index:1,
        	formFileName:'instance.titleImageUrl',
        	formData:{
        		//'widthHeightLimit' : '[{w:300,h:200}]'
        	}
		});
    });
</script>
            <TR>
                <TD align=right class="input-label"><label>标题图片：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify1" /></div>
                	<div class="col-xs-5"><span>格式：png/jpg/jpeg；尺寸：300*200</span></div>
					</div>
					<div id="fileQueue1"></div>
					<div id="fileShow1"><span ng-show="o.data.titleImageUrl"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.titleImageUrl}}"/>
					<input type='hidden' name='instance.titleImageUrl' value='{{o.data.titleImageUrl}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>摘要：</label></TD>
                <TD class="input-col">
					<textarea class="form-control" name="instance.memo" rows="3" cols="70" style="width:450px" placeholder="请输入摘要"></textarea>
                </TD>
            </TR>
            </c:if>
            <TR>
                <TD align=right class="input-label"><label>内容：</label></TD>
                <TD class="input-col">
                <input type="hidden" id="myEditorContent" value="{{o.data.content}}"/>
			<!--style给定宽度可以影响编辑器的最终宽度-->
		<script type="text/plain" id="myEditor" style="width:800px;height:360px;"></script>
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>

<script>
var ue = UE.getEditor('myEditor',{
    toolbars: [['source', '|', 'undo', 'redo', 'bold', 'italic', 'underline', '|', 'forecolor', 'backcolor', 'indent', '|' , 'removeformat', '|',
    'fontfamily', 'fontsize', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify' , '|', 'link', 'unlink', 'insertimage']],
    indent:false,//首行缩进
	fontsize:[10, 11, 12, 14, 16, 18, 20, 24, 36],
    //focus时自动清空初始化时的内容
    autoClearinitialContent:false,
 	// 提交表单时，服务器获取编辑器提交内容的所用的参数，多实例时可以给容器name属性，会将name给定的值最为每个实例的键值，不用每次实例化的时候都设置这个值
    textarea:'instance.content', 
    //关闭字数统计
    wordCount:false,
    //关闭elementPath
    elementPathEnabled:false,
    //默认的编辑区域高度
    initialFrameHeight:300
    //浮动时工具栏距离浏览器顶部的高度，用于某些具有固定头部的页面
    ,topOffset:50
    //更多其他参数，请参考umeditor.config.js中的配置项
    ,zIndex : 900     //编辑器层级的基数,默认是900
    //更多其他参数，请参考ueditor.config.js中的配置项
});

</script>
    
</div>
</BODY>
</HTML>