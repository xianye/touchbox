<%@ page language="java" pageEncoding="UTF-8"%><%@ include file="../includes/head.jsp" %>

	<title>Touchbox创意盒子</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/jeasyui/themes/bootstrap/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx }/jeasyui/themes/icon.css">
	<script type="text/javascript" src="${ctx }/jeasyui/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx }/jeasyui/jquery.easyui.min.js"></script>
</head>
<script language="JavaScript" type="text/javascript">

function addTab(title, href) {
    var tt = $('#main-center');
    if (tt.tabs('exists', title)) {
        tt.tabs('close', title);
    }
    var content = '&nbsp;';
    if (href) {
        content = '<iframe frameborder="0" scrolling="auto" src="' + href + '" style="width:100%;height:99%;"></iframe>';
    }
    tt.tabs('add', {
        title : title,
        closable : true,
        content : content
    });
}

function removeTab(){
    var tab = $('#main-center').tabs('getSelected');
    if (tab){
        var index = $('#main-center').tabs('getTabIndex', tab);
        $('#main-center').tabs('close', index);
    }
}

function setDefaultFrame(){

   addTab("个人资料", "${ctx }/manage/admin/personalInfo");

}

$(function(){

    $(".easyui-accordion .menu-link").click(function(){
        var $this = $(this);
        addTab($this.attr("link-name"), $this.attr("link-url"));
    });
    
    setTimeout(function(){
        setDefaultFrame();
    },500);
});
</script>
<body class="easyui-layout">
		<div data-options="region:'north'" style="background:url(${ctx }/images/sc-1_01.gif); color: #2d5593; height: 50px;">
    <div style="font-size: 20px; font-weight: bold; width: 300px;  position: relative; padding-top:6px; padding-left:30px;float: left; "><img src="${ctx }/images/logo.png" width="233" height="36" alt=""></div>
    <div style="font-size: 12px; color: black;  color:#a5d6f3; padding: 18px 0 0 0px ; text-align: center; position: relative;	float: right;">
        <span id="timediv"  style="width:350px; "></span>
			<span  style="width:300px;"> <img src="http://admin.scsjb.cn/images/admin.gif" alt="" width="10" height="10" align="absmiddle">
				欢迎你：${SESSION_ADMIN.name }   &nbsp;  &nbsp; &nbsp;<a  href="${ctx }/logout" style="color:#ffffff">安全退出>></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
    </div>
		</div>
		<div data-options="region:'south',split:true" style="height:60px;">
			<p class="foot_copyright" align="center"><span>Copyright © 2015-2017, TOUCHBOX.CN, All Rights Reserved&nbsp;</span><a href="http://www.hd315.gov.cn/beian/view.asp?bianhao=010202001032100010" target="_blank"><img src="http://c.csdnimg.cn/pubfooter/images/gongshang_logos.gif" alt="GongshangLogo" title=""></a></p>
		</div>
		<!-- 
		<div data-options="region:'east',split:true" title="East" style="width:180px;">
			<ul class="easyui-tree" data-options="url:'tree_data1.json',method:'get',animate:true,dnd:true"></ul>
		</div> -->
		<div data-options="region:'west',split:true" title="菜单功能" style="width:205px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">

			<s:iterator value="#session.SESSION_ADMIN.menuResources" id="obj" status="st">
				<div title="${obj.name }" <s:if test="#obj.name=='内容管理'">data-options="selected:true"</s:if> style="padding:5px;">
					<s:iterator value="#obj.children" id="child" status="st1">
                    <div align="center" class="menu-link" link-url="${ctx }${child.link}" link-name="${child.name }"><a class="easyui-linkbutton" style="width:100%" data-options="plain:true">${child.name }</a></div>
					</s:iterator>
				</div>
			</s:iterator>

			</div>
		</div>
		<div data-options="region:'center',iconCls:'icon-ok'">
			<div id="main-center" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
			</div>
		</div>
</body>
</html>