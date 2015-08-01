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

	G.customOpts['addGoods'] = function($scope){// 添加商品数据
		var goodsList = [],
			goodsItem = {name:"",goodsId:"",periodNum:0},
			msg = "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>&nbsp;",
			specAddMsg = jQuery("#specAddMsg");
	
		if($scope.o.data&&$scope.o.data.goodsList){
			goodsList = $scope.o.data.goodsList;
		}
		jQuery("#goodsAddContainer").find("input:text").each(function(){
			var meObj = jQuery(this),
				key = meObj.attr("id").substring(6);
			goodsItem[key] = jQuery.trim(meObj.val());
		});
	
		//console.log($scope.specifications);
		// 规格数据验证判断
		if(goodsItem['name']==""){
			specAddMsg.html(msg+"请输入名称！");
			return;
		}
		if(goodsItem['goodsId']==""){
			specAddMsg.html(msg+"请输入货品条形码！");
			return;
		}else{
			for(var i=0;i<goodsList.length;i++){
				if(goodsList[i].goodsId==goodsItem['goodsId']){
					specAddMsg.html(msg+"已存在条形码‘"+goodsItem['goodsId']+"’！");
					return;
				}
			}
		}
		specAddMsg.html("");
	
		goodsList.push(goodsItem);
		$scope.o.data.goodsList = goodsList;
	};
	G.customOpts['removeGoods'] = function($scope,me){// 删除商品数据
		if($scope.o.data&&$scope.o.data.goodsList&&window.confirm("确认删除？")){
			//console.log({me:me});
			$scope.o.data.goodsList.splice(me.$index,1);
		}
	};
	G.customOpts['editGoods'] = function($scope,me){// 删除商品数据
		console.log({me:me});
		jQuery("#goods"+me.$index+"edit").show();
		jQuery("#goods"+me.$index+"view").hide();
	};
	G.customOpts['saveGoods'] = function($scope,me){// 添加商品数据
		var goodsList = $scope.o.data.goodsList,
			goodsItem = {name:"",goodsId:""},
			msg = "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>&nbsp;",
			goodsEditMsg = jQuery("#goods"+me.$index+"EditMsg");

		jQuery("#goods"+me.$index+"edit").find("input:text").each(function(){
			var meObj = jQuery(this),
				key = meObj.attr("id").substring(meObj.attr("id").lastIndexOf("_")+1);
			goodsItem[key] = jQuery.trim(meObj.val());
		});

		console.log(goodsItem);
		// 规格数据验证判断
		if(goodsItem['name']==""){
			goodsEditMsg.html(msg+"请输入名称！");
			return;
		}
		if(goodsItem['goodsId']==""){
			goodsEditMsg.html(msg+"请输入货品条形码！");
			return;
		}else{
			for(var i=0;i<goodsList.length;i++){
				if(i!=me.$index&&goodsList[i].goodsId==goodsItem['goodsId']){
					goodsEditMsg.html(msg+"已存在条形码‘"+goodsItem['goodsId']+"’！");
					return;
				}
			}
		}
		goodsList[me.$index].name = goodsItem.name;
		goodsList[me.$index].goodsId = goodsItem.goodsId;
		
		$scope.o.data.goodsList = goodsList;
		G.customOpts['cancelEditGoods']($scope,me);
	};
	G.customOpts['cancelEditGoods'] = function($scope,me){// 取消修改商品数据
		var goodsItem = $scope.o.data.goodsList[me.$index],
			goodsEditMsg = jQuery("#goods"+me.$index+"EditMsg");
		console.log({me:me});
		jQuery("#goods"+me.$index+"view").show();
		jQuery("#goods"+me.$index+"edit").hide();
		goodsEditMsg.html("");
		jQuery("#goods"+me.$index+"edit").find("input:text").each(function(){
			var meObj = jQuery(this),
				key = meObj.attr("id").substring(meObj.attr("id").lastIndexOf("_")+1);
			meObj.val(goodsItem[key]);
		});
	};
	 
	jQuery(function() {
    	 
		G.uploadify({
    		index:1,
    		formFileName:'instance.bannerImageUrl'
    	});
     	
    	G.uploadify({
    		index:2,
    		formFileName:'instance.titleImageUrl',
    		formData:{
    		//	'widthHeightLimit' : '[{w:300,h:300}]'
    		}
    	});

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
		data:{productCatId:0,deliveryWarehouse:'SH',giftClassroomPeriodNum:0,description:'',goodsList:[]}
	})">
    <form onsubmit="return checkForm();" method="post" id="frm" name="frm">
        <input type="hidden" id="backUrl" value="${ctx }/manage/product/list">
        <c:set var="actionName" value="新增"/>
        <c:set var="isModify" value="${not empty dataId}"/>
        <c:if test="${isModify}">
        <input type="hidden" name="instance.productId" id="dataId" value="${dataId}">
        <input type="hidden" id="fetchDataUrl" value="${ctx }/api/product/fetchItem?dataId=${dataId}">
        <input type="hidden" name="instance.status" id="status" value="{{o.data.status}}">
        <c:set var="actionName" value="编辑"/>
        </c:if>
        <input type="hidden" id="saveUrl" value="${ctx }/api/product/save">

        <ol class="breadcrumb top-bar">
            <li><a href="javascript:void(0);" ng-click="goBack()">产品管理</a></li>
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
                <TD align=right class="input-label"><label>产品名称：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="name" name="instance.name" ng-model="o.data.name" value="" style="width:250px"
                           placeholder="请输入 产品名称"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>所属类型：</label></TD>
                <TD class="input-col">
                    <div class="row">
						<div class="col-xs-3">
	               		<select class="form-control" id="productCatId" name="instance.productCatId" ng-model="o.data.productCatId">
		                	<option value="0">请选择</option>
	               			<c:forEach var="pCat" items="${productCatList }">
	               			<option value="${pCat.productCatId }">${pCat.name }</option>	               			
	               			</c:forEach>
	               		</select>
						</div>
					</div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>方案：</label></TD>
                <TD class="input-col">
					<label class="checkbox-inline">
					  <input type="checkbox" name="instance.periods" value="12" ng-checked="o.data.checkedPeriod['12']" > 一年
					</label>
					<label class="checkbox-inline">
					  <input type="checkbox" name="instance.periods" value="6" ng-checked="o.data.checkedPeriod['6']"> 半年
					</label>
					<label class="checkbox-inline">
					  <input type="checkbox" name="instance.periods" value="3" ng-checked="o.data.checkedPeriod['3']"> 3个月
					</label>
					<label class="checkbox-inline">
					  <input type="checkbox" name="instance.periods" value="1" ng-checked="o.data.checkedPeriod['1']"> 1个月
					</label>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>商品名称及编码：</label></TD>
                <TD class="input-col">
					<ul class="list-group" id="goodsContainer">
					  <li class="list-group-item" id="goodsAddContainer"><table><tr>
	                  		<th style="padding-right:5px">期数</th>
	                		<td style="padding-right:5px"><input type="text" id="goods_periodNum" class="form-control" style="width:40px;" value="{{o.data.goodsList.length+1}}" readOnly/></td>
	                  		<th style="padding-right:5px">名称</th>
	                		<td style="padding-right:5px"><input type="text" id="goods_name" class="form-control" placeholder="请输入名称" style="width:180px;"/></td>
	                  		<th style="padding-right:5px">编码</th>
	                		<td style="padding-right:5px"><input type="text" id="goods_goodsId" class="form-control" placeholder="请输入条形码" style="width:200px;"/></td>

	                 		<td style="padding-right:5px"><input type="button" class="btn btn-info" value="添加" ng-click="customHandle({methodName:'addGoods'})"/></td>
	                 		<td style="padding-right:5px"><span id="specAddMsg" class="label label-danger"></span></td>
	                	</tr></table></li>
					  <li class="list-group-item" ng-repeat="item in o.data.goodsList">
					  	<a href="javascript:void(0)" ng-click="customHandle({methodName:'editGoods'})" ng-if="!$last&&item.status!=10"><span class="glyphicon glyphicon-edit" style="float:right" aria-hidden="true"></span></a>
					  	<a href="javascript:void(0)" ng-click="customHandle({methodName:'removeGoods'})" ng-if="$last&&item.status!=10"><span class="glyphicon glyphicon-remove" style="float:right" aria-hidden="true"></span></a><span id="goods{{$index}}view">第{{item.periodNum}}期：{{item.name}}（{{item.goodsId}}）</span><span ng-if="!last" style="display:none" id="goods{{$index}}edit"><table><tr>
	                		<td style="padding-right:5px">第{{item.periodNum}}期：</td>
	                  		<th style="padding-right:5px">名称</th>
	                		<td style="padding-right:5px"><input type="text" id="goods{{$index}}edit_name" class="form-control" placeholder="请输入名称" style="width:180px;" value="{{item.name}}"/></td>
	                  		<th style="padding-right:5px">编码</th>
	                		<td style="padding-right:5px"><input type="text" id="goods{{$index}}edit_goodsId" class="form-control" placeholder="请输入条形码" style="width:200px;" value="{{item.goodsId}}"/></td>
	                 		<td style="padding-right:5px"><input type="button" class="btn btn-info" value="保存" ng-click="customHandle({methodName:'saveGoods'})"/></td>
	                 		<td style="padding-right:5px"><input type="button" class="btn btn-info" value="取消" ng-click="customHandle({methodName:'cancelEditGoods'})"/></td>
	                 		<td style="padding-right:5px"><span id="goods{{$index}}EditMsg" class="label label-danger"></span></td>
	                	</tr></table></span>
					  	<input type="hidden" id="goods{{$index}}_goodsId" name="instance.goodsList[{{$index}}].goodsId" value="{{item.goodsId}}"/>
					  	<input type="hidden" id="goods{{$index}}_name" name="instance.goodsList[{{$index}}].name" value="{{item.name}}"/>
					  	<input type="hidden" id="goods{{$index}}_periodNum" name="instance.goodsList[{{$index}}].periodNum" value="{{item.periodNum}}"/>
					  	<input type="hidden" id="goods{{$index}}_status" name="instance.goodsList[{{$index}}].status" value="{{item.status}}"/>
					  </li>
					</ul>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>原价：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="originalPrice" name="instance.originalPrice" ng-model="o.data.originalPrice" value="" style="width:250px"
                           placeholder="请输入原价"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>现价：</label></TD>
                <TD class="input-col">
                    <input class="form-control" type="text" id="price" name="instance.price" ng-model="o.data.price" value="" style="width:250px"
                           placeholder="请输入现价"/>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>发货仓：</label></TD>
                <TD class="input-col">
                    <div class="row">
						<div class="col-xs-3">
		               		<select class="form-control" id="deliveryWarehouse" name="instance.deliveryWarehouse" ng-model="o.data.deliveryWarehouse">
		                		<option value="SH">上海</option>
		                		<option value="BJ">北京</option>
		               		</select>
					    </div>
					</div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>赠送课堂期数：</label></TD>
                <TD class="input-col">
                    <div class="row">
						<div class="col-xs-3">
	               		<select class="form-control" id="giftClassroomPeriodNum" name="instance.giftClassroomPeriodNum" ng-model="o.data.giftClassroomPeriodNum">
		                	<option value="0">请选择</option>
	               			<option value="1">1</option>	               			
	               			<option value="2">2</option>	               			
	               		</select>
						</div>
					</div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>通栏图片：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify1" /></div>
                	<div class="col-xs-5"><span>格式：png/jpg/jpeg；尺寸：1000*300</span></div>
					</div>
					<div id="fileQueue1"></div>
					<div id="fileShow1"><span ng-show="o.data.bannerImageUrl"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.bannerImageUrl}}"/>
					<input type='hidden' name='instance.bannerImageUrl' value='{{o.data.bannerImageUrl}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>列表图片：</label></TD>
                <TD class="input-col">
                	<div class="row">
                	<div class="col-xs-2"><input type="file" id="uploadify2" /></div>
                	<div class="col-xs-5"><span>格式：png/jpg/jpeg；尺寸：300*300</span></div>
					</div>
					<div id="fileQueue2"></div>
 					<div id="fileShow2"><span ng-show="o.data.titleImageUrl"><img alt="" src="${RESOURCE_DIR_URL}{{o.data.titleImageUrl}}"/>
					<input type='hidden' name='instance.titleImageUrl' value='{{o.data.titleImageUrl}}'/>
					<a title='删除图片' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)' onClick="javascript:G.removeUploadFile(this)"></a></span></div>
                </TD>
            </TR>
            <TR>
                <TD align=right class="input-label"><label>产品介绍：</label></TD>
                <TD class="input-col" colspan="3">
                <input type="hidden" id="myEditorContent" value="{{o.data.description}}"/>
			<!--style给定宽度可以影响编辑器的最终宽度-->
		<script type="text/plain" id="myEditor" style="width:800px;height:360px;"></script>
                </TD>
            </TR>
            </TBODY>
        </TABLE>

    </form>

<script>
var ue = UE.getEditor('myEditor',{
    //这里可以选择自己需要的工具按钮名称,此处仅选择如下七个
    //toolbar:['source | undo redo bold italic underline | forecolor backcolor | removeformat | fontfamily fontsize | justifyleft justifycenter justifyright | link unlink image'],

    toolbars: [['source', '|', 'undo', 'redo', 'bold', 'italic', 'underline', '|', 'forecolor', 'backcolor', 'indent', '|' , 'removeformat', '|',
    'fontfamily', 'fontsize', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify' , '|', 'link', 'unlink', 'insertimage']],
    
	//'fontfamily':[
	//	{ label:'',name:'songti',val:'宋体,SimSun'},
	//	{ label:'',name:'kaiti',val:'楷体,楷体_GB2312, SimKai'},
	//	{ label:'',name:'yahei',val:'微软雅黑,Microsoft YaHei'},
	//	{ label:'',name:'heiti',val:'黑体, SimHei'},
	//	{ label:'',name:'lishu',val:'隶书, SimLi'},
	//	{ label:'',name:'andaleMono',val:'andale mono'},
	//	{ label:'',name:'arial',val:'arial, helvetica,sans-serif'},
	//	{ label:'',name:'arialBlack',val:'arial black,avant garde'},
	//	{ label:'',name:'comicSansMs',val:'comic sans ms'},
	//	{ label:'',name:'impact',val:'impact,chicago'},
	//	{ label:'',name:'timesNewRoman',val:'times new roman'},
	//	{ label:'',name:'verdana',val:'Verdana'}
	//	],
    indent:false,//首行缩进
	fontsize:[10, 11, 12, 14, 16, 18, 20, 24, 36],
    //focus时自动清空初始化时的内容
    autoClearinitialContent:false,
 	// 提交表单时，服务器获取编辑器提交内容的所用的参数，多实例时可以给容器name属性，会将name给定的值最为每个实例的键值，不用每次实例化的时候都设置这个值
    textarea:'instance.description', 
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