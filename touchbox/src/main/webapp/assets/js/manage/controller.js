/**
 * Created by James on 2015/3/23.
 */
mainApp.controller('listController', [ '$scope', 'services',
		function($scope, services) {
			var afterQuery = function() {
				enableCheckAll();

				window.setTimeout(function(){
					if(document.getElementById("pageLoading")){
						jQuery("#pageLoading").hide();
					}
					if(document.getElementById("contentMain")){
						jQuery("#contentMain").show();
					}
				},100);
			};

			$scope.pageQuery = function(page) {
				
				if(document.getElementById("pageLoading")){
					jQuery("#pageLoading").show();
				}
				if(document.getElementById("contentMain")){
					jQuery("#contentMain").hide();
				}
				
				if (page > 0) {
					document.getElementById("page").value = page;
				}
				var url = jQuery("#queryUrl").val();
				var handler = {
					current : function() {
						services.search($scope, {
							url : url,
							page : page,
							afterQuery : afterQuery
						});
					},
					next : function() {
						services.search($scope, {
							url : url,
							page : page + 1,
							afterQuery : afterQuery
						});
					},
					previous : function() {
						services.search($scope, {
							url : url,
							page : page - 1,
							afterQuery : afterQuery
						});
					}
				};

				return handler;
			};

			$scope.updateStatus = function(options) {
				var url =  jQuery("#updateStatusUrl").val();
				jQuery.messager.confirm('提示', options.confirmText, function(r) {
					if (r) {
						// alert(JSON.stringify(options));
						services.updateStatus($scope, {
							url : url,
							params : options.params,
							afterUpdate : function() {
								$scope.pageQuery(1).current();
							}
						});
					}
				});
			};
			
			$scope.del = function(params) {// 删除
				params[params.statusKey]=2;
				delete params.statusKey;
				$scope.updateStatus({params:params,confirmText:"确认删除?"});
			};
			$scope.freeze = function(params) {// 冻结
				params[params.statusKey]=true;
				delete params.statusKey;
				$scope.updateStatus({params:params,confirmText:"确认冻结?"});
			};
			$scope.activate = function(params) {// 激活
				params[params.statusKey]=false;
				delete params.statusKey;
				$scope.updateStatus({params:params,confirmText:"确认激活?"});
			};
			
			$scope.editPage = function(options){
				var url = jQuery("#editUrl").val();
				if(options&&options.params){
					if(url.indexOf("?")==-1){
						url += "?";
					}else{
						url += "&";
					}
					url += jQuery.param(options.params);
				}
				window.location.href = url;
			};
			
			/**
			 * 自定义导向页面地址
			 */
			$scope.customPage = function(options){
				if(options&& options.url){
					var url = options.url;
					if(options.url.indexOf("#")==0){
						url = jQuery(options.url).val();
					}
					if(options&&options.params){
						if(url.indexOf("?")==-1){
							url += "?";
						}else{
							url += "&";
						}
						url += jQuery.param(options.params);
					}
					window.location.href = url;
				}
			};
			
			$scope.fetchData = function(options){
				if(document.getElementById("fetchDataUrl")){
					var url = jQuery("#fetchDataUrl").val();
					services.fetchData($scope,{url:url});
				}else{
					$scope.o = {
						data :options.data
					};
				}
				
				if(options.fetchParams){
					for(var i=0;i<options.fetchParams.length;i++){
						var url = options.fetchParams[i].url,
							scopeField = options.fetchParams[i].scopeField;
						services.fetchData($scope,{url:url,scopeField:scopeField});
					}
				}
			};

			$scope.pageQuery(1).current();
		} ]);

mainApp.controller('editController', [ '$scope', 'services',
        function($scope, services) {
    		$scope.validateFormPattern = G.validateFormPattern;
	
			$scope.goBack = function(){
				if(document.getElementById("backUrl")){
					var url = jQuery("#backUrl").val();
					window.location.href = url;
				}
			};

			$scope.fetchData = function(options){
				
				if(options&&options.fetchParams){
					
					for(var i=0;i<options.fetchParams.length;i++){
						var fparam = options.fetchParams[i],
							url = fparam.url,
							scopeField = fparam.scopeField;
						if (typeof fparam.async == "undefined") {
							fparam.async = true;
						}
						if(url.indexOf("#")==0){
							url = jQuery(fparam.url).val();
						}
						services.fetchData($scope,{url:url,scopeField:scopeField,async:fparam.async});
					}
				}
				
				// 查询数据后执行方法
				var afterQuery = function(){
						//alert(options.afterFetch);
						if(options&&options.afterFetch){// 抓取数据后执行的方法
							for(var i=0;i<options.afterFetch.length;i++){
								var customHandle = G.customOpts[options.afterFetch[i]];
								if(customHandle){
									customHandle($scope);
								}
							}
						}
				};
				
				//alert(options);
				if(!(options&&options.fetchDataDisabled)){
					if(document.getElementById("fetchDataUrl")){
							var url = jQuery("#fetchDataUrl").val();
							services.fetchData($scope,{url:url,async:true,afterQuery:afterQuery});
					}else{
						$scope.o = {
							data :options.data
						};
					}
				}

			};
			
			$scope.save = function(options) {
				var afterSave = false,
					formValid = false,
					alertOpts = null,
					scopeField = null;
				if(window.checkForm){
					formValid = window.checkForm($scope);
				}
				
				if(!formValid){// form表单数据无效，不执行保存操作
					return;
				}
				
				if(options){
					if(options.beforeSave){
						options.beforeSave();
					}
					afterSave = options.afterSave;
					if(options.status&&options.status.id){
						jQuery("#"+options.status.id).val(options.status.value);
					}
					if(options.alertOpts){
						alertOpts = options.alertOpts;
					}
					if(options.scopeField){
						scopeField = options.scopeField;
					}
				}
				
				// 默认操作提示
				var handleAlert = function(afterAlert){// 参数afterAlert为回调函数
					var resultO = $scope.saveResult;
					if(scopeField){
						resultO = $scope[scopeField];
					}
					if(resultO){
						var successPrompt = "保存成功！",errorPrompt = "未知异常！";
						if(resultO.errorCode==0){
							if(alertOpts&&alertOpts.messages&&alertOpts.messages.success){
								successPrompt = alertOpts.messages.success;
							}
							jQuery.messager.alert('^_^',successPrompt,'info',afterAlert);
						}else{
							// console.log(resultO);
							if(resultO.errorMsg){
								errorPrompt = resultO.errorMsg;
							}
							jQuery.messager.alert('出错啦~',errorPrompt,'error');
							return;
						}
					}
				};
				
				console.log(options);
				if(!afterSave){
					afterSave = function(){
						handleAlert(function(){
							$scope.goBack();
						});
					};
				}else{
					afterSave = function(){
						handleAlert(function(){
							
							if(options.afterSave.fetchParams){
								$scope.fetchData({fetchParams:options.afterSave.fetchParams});
							}
							
							if(!options.afterSave.ajax){
								$scope.goBack();
							}
						});
					};
				}
				
				var url = jQuery("#saveUrl").val();
				services.save($scope,{url:url,scopeField:scopeField,afterSave:afterSave});
            };
            
			$scope.updatePassword = function() {
				$scope.save({afterSave:function(){
					jQuery("input[type='password']").each(function(){
						this.value = "";
					});
				}});
            };
            
            // 自定义操作
            $scope.customHandle = function(options){
            	if(options&&options.methodName&&G.customOpts[options.methodName]){
            		G.customOpts[options.methodName]($scope,this);
            	}
            };
            
}]);