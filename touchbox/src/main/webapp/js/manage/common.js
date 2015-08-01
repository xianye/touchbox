var G = {
	basePath:'',
	/**
	 * uploadify上传包装
	 */
	uploadify : function(options) {
		var i = options.index, formData = options.formData, fileTypeDesc = 'All Files', fileTypeExts = options.fileTypeExts,
			formFileName = options.formFileName,
			buttonText = "选择上传图片",
			fileSizeLimit = "5MB";
		if (!formData) {
			formData = {
				'type' : 'image'
			};
		} else if (!formData.type) {
			formData.type = 'image';
		}
		if (!fileTypeExts) {
			fileTypeExts = '*.*';
		}
		if(options.fileSizeLimit){
			fileSizeLimit = options.fileSizeLimit;
		}

		if (formData.type == 'image') {
			fileTypeDesc = 'Image Files';
			fileTypeExts = '*.gif; *.jpg; *.png';
		} else if (formData.type == 'video') {
			fileTypeDesc = 'Video Files';
			fileTypeExts = '*.mp4';
			buttonText = "选择上传视频";
		} else{
			buttonText = "选择上传文件";
		}
		var fileShow = jQuery("#fileShow" + i),
			fileQueue = jQuery("#fileQueue"+i);

		jQuery("#uploadify" + i)
				.uploadify(
						{
							height : 30,
							swf : G.basePath + '/uploadify/uploadify.swf',
							uploader : G.basePath + '/api/upload/exec',
							width : 120,
							formData : formData,
							fileObjName : 'file',
							fileSizeLimit : fileSizeLimit,
							fileTypeDesc : fileTypeDesc,
							fileTypeExts : fileTypeExts,
							queueID : 'fileQueue' + i,
							auto : true,
							multi : false,
							buttonText : buttonText,
							onError : function(event, ID, fileObj, errorObj) {
								fileQueue
										.html("<span class='glyphicon glyphicon-info-sign'></span> 超过文件上传大小限制（5M）！");
								return false;
							},
							onSelectError : function(file, errorCode, errorMsg) {
								fileQueue
										.html("<span class='glyphicon glyphicon-info-sign'></span> 超过文件上传大小限制（5M）！");
								return false;
							},
							onUploadError : function(file, errorCode, errorMsg) {
								fileQueue
										.html("<span class='glyphicon glyphicon-info-sign'></span> 文件上传失败");
							},
							onUploadSuccess : function(file, data, response) {

								if (response && data) {
									var r = JSON.parse(data);
									//console.log(r);
									fileQueue.html("");
									if(r.errorCode==0){
										var uploadFileView = "<a target='_blank' href='"+r.data.resourceDirUrl+r.data.filePath+"'>"+r.data.resourceDirUrl+r.data.filePath+"</a> ";
										if (formData.type == 'image'){
											uploadFileView = "<img src='"+r.data.resourceDirUrl+r.data.filePath+"'/> ";
										}else if (formData.type == 'video'){
										}
										fileShow.html(uploadFileView)
										.append("<input type='hidden' name='"+formFileName+"' value='"+r.data.filePath+"'/>")
										.append(
												jQuery(
														"<a title='删除' class='glyphicon glyphicon-remove' style='vertical-align:top' href='javascript:void(0)'></a>")
														.click(
																function() {
																	G.removeUploadFile(this);
																}));
									}else{
										fileQueue
										.html("<span class='glyphicon glyphicon-info-sign'></span> <span style='color:red'>"+r.errorMsg+"</span>");
									}
								}
							},
							onUploadComplete : function(file) {

							}
						});

	},
	removeUploadFile:function(el){// 删除图片
		if (confirm("确定删除?")) {
			jQuery(el).parent().html("");
		}
	},
	validateFormPattern:{
		mobile:"/^1[0-9]{10}$/",
		email:"/^\w+@\w+(\.[a-z]+)+$/"
	},
	validateUtil:{// 验证方法
		isPositiveInteger:function(numStr){// 是否正整数
			return numStr&&numStr.match(/^[1-9][0-9]*$/);
		},
		isPositiveNumber:function(numStr){// 是否正数,10,10.12,0.01,...
			return numStr&&numStr.match(/^([1-9][0-9]*(\.\d+)?|0\.\d+)$/);
		}
	},
	customOpts:{
	}
}

/**
 * 置顶
 */
function gotoTop() {
	$("body,html").animate({
		scrollTop : 0
	}, 200);
}

/**
 * 显示警告提示信息
 */
function showAlertText(subject, contents, msgLevel) {
	if (document.getElementById("alert1")) {

		if (document.getElementById("alert1Container")) {
			$("#alert1Container").show();
		}
		if (msgLevel) {
			if (msgLevel == 'success') {
				$("#alert1")
						.html(
								$(
										"<div class='alert alert-success alert-dismissible' align='left'/>")
										.html(
												$("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>"))
										.append(
												$("<strong>" + subject
														+ "</strong>")).append(
												" <span>" + contents
														+ "</span>"));
			} else if (msgLevel == 'info') {
				var $alertDiv = $(
						"<div class='alert alert-warning' align='left' role='alert'/>")
						.html(
								$("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>"))
						.append($("<strong>" + subject + "</strong>"));
				var $alertContent = $("<ul></ul>").css({
					color : "red"
				});
				for (var i = 0; i < contents.length; i++) {
					$alertContent.append("<li>" + contents[i] + "</li>");
				}
				$alertDiv.append($alertContent)
				$("#alert1").html($alertDiv);
			} else {
				$("#alert1").html(
						$("<div style='color:red'></div>").append("【").append(
								$("<strong>" + subject + "</strong>")).append(
								" <span>" + contents + "</span>").append("】"));
			}
		} else {
			$("#alert1")
					.html(
							$(
									"<div class='alert alert-warning alert-dismissible' align='left'/>")
									.html(
											$("<button type='button' class='close' data-dismiss='alert' aria-hidden='true'>&times;</button>"))
									.append(
											$("<strong>" + subject
													+ "</strong>")).append(
											" <span style='color:red'>"
													+ contents + "</span>"));
		}

		gotoTop();
	}
}

// 全选生效
function enableCheckAll() {
	$("input[action='checkAll']").click(function() {
		var chkAllEl = this;
		var childName = $(this).attr("child-name");
		$("input[type='checkbox'][name='" + childName + "']").each(function() {
			this.checked = chkAllEl.checked;
		});
	});

	$("input[type='checkbox'][name='dataIds']").click(function() {
		if (this.checked) {
			var checkAllFlag = true;
			$("input[type='checkbox'][name='dataIds']").each(function() {
				checkAllFlag = checkAllFlag && this.checked;
			});

			$("input[type='checkbox'][child-name='dataIds']").each(function() {
				this.checked = checkAllFlag;
			});
		} else {
			$("input[type='checkbox'][child-name='dataIds']").each(function() {
				this.checked = false;
			});
		}
	});
}

/** 根据options参数查询数据 */
function ajaxQuery(options) {
	$.ajax({
		type : "post",
		url : options.url,
		data : options.params,
		cache : false,
		beforeSend : function() {// Handle the beforeSend event
			if (options.beforeQuery) {
				options.beforeQuery();
			}
		},
		success : function(data, textStatus) {
			// alert(data);
			var querySuccess = false;
			if (options.format && options.format == "json") {
				if (data) {
					var result = data;
					if (typeof data === 'string') {
						result = eval("(" + data + ")");
					}
					if (result && options.tplText) {
						var html = juicer(options.tplText(), result);
						options["$el"].html(html);
						querySuccess = true;
					}
				}
			} else {
				options["$el"].html(data);
				querySuccess = true;
			}

			// 查询成功后设置
			if (querySuccess && options.afterQuery) {
				options.afterQuery();
			}
		},
		error : function(data) {
			alert("加载数据失败！");
		}
	});
}

// 根据form表单数据查询
function ajaxFind(options) {

	var myOpts = options;
	myOpts["$el"] = $("#contentMain");
	// myOpts.tplText = "";
	myOpts.beforeQuery = function() {
		myOpts["$el"].html("");
	};
	myOpts.afterQuery = function() {
		enableLinks();
	};
	ajaxQuery(myOpts);
}

/** 到指定页 */
function GotoPage(i) {
	if (document.frm.page)
		document.frm.page.value = i;
	var qForm = $("#frm");// 查询form
	var frmDataOptions = JSON.parse(qForm.attr("dataOptions"));// form配置数据
	var qUrl = frmDataOptions.queryUrl;
	var options = {
		"url" : qUrl,
		"params" : qForm.serialize()
	};
	ajaxFind(options);
}

/** 更新数据状态 */
function ajaxUpdateStatus(options) {
	$.ajax({
		type : "post",
		url : options.url,
		data : options.params,
		cache : false,
		beforeSend : function() {// Handle the beforeSend event
			// $("#contentMain").html("");
		},
		success : function(data, textStatus) {
			// alert(data);
			if (data) {
				var result = data;
				// alert(JSON.stringify(result));
				if (result && result.status == 1) {// 更新成功后进入查询结果页面
					GotoPage(1);
				}
			}
		},
		error : function(data) {
		}
	});
}

/** 保存数据 */
function ajaxSave(options) {
	$.ajax({
		type : "post",
		url : options.url,
		data : options.params,
		cache : false,
		beforeSend : function() {// Handle the beforeSend event
			// $("#contentMain").html("");
		},
		success : function(data, textStatus) {
			// alert(data);
			if (data) {
				var result = data;
				// alert(JSON.stringify(result));
				if (result) {// 更新成功后进入查询结果页面
					if (result.status == 1) {
						window.location = options.backOptions.url;
					} else {
						if (result.msg && result.msg.contents
								&& result.msg.contents[0]) {
							showAlertText("", result.msg.contents,
									result.msg.level);
						}
					}
				}
			}
		},
		error : function(data) {
		}
	});
}

/**
 * 新增或编辑页面
 */
function editPage(options) {
	var editLink = options.url;
	if (options.id) {
		editLink += "&dataId=" + options.id;
	}
	$("#frm").attr("action", editLink);
	// console.log(frm.action);
	document.frm.submit();
}

// 激活链接功能
function enableLinks() {
	enableCheckAll();

	var qForm = $("#frm");// 查询form
	var frmDataOptions = eval("(" + qForm.attr("dataOptions") + ")");// form配置数据
	var qUrl = frmDataOptions.queryUrl + "?t=" + new Date().getTime();
	var updateStatusUrl = frmDataOptions.updateStatusUrl + "?t="
			+ new Date().getTime();
	var editUrl = frmDataOptions.editUrl + "?t=" + new Date().getTime();

	// 单条编辑
	$("#contentMain a[action='edit']").click(function() {
		var $this = $(this);
		editPage({
			id : $this.attr("data-id"),
			url : editUrl
		});
	});

	// 单条删除
	$("#contentMain a[action='delete']").click(function() {
		var $this = $(this);
		var dataId = $this.attr("data-id");

		if (window.confirm("确定要删除ID为“" + dataId + "”的数据项？")) {
			ajaxUpdateStatus({
				url : updateStatusUrl,
				params : {
					dataIds : dataId,
					dataStatus : 2
				}
			});
		}
	});

	// 单条数据状态更新
	$("#contentMain a[action='updateStatus']").click(function() {
		var $this = $(this);
		var dataId = $this.attr("data-id");
		if (window.confirm("确定要" + $this.text() + "ID为“" + dataId + "”的数据项？")) {
			ajaxUpdateStatus({
				url : updateStatusUrl,
				params : {
					dataIds : dataId,
					dataStatus : $this.attr("data-status")
				}
			});
		}
	});
}