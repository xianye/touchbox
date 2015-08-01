/**
 * Created by James on 2015/3/23.
 */
var mainApp = angular.module('mainApp', [ 'ngResource','ngMessages' ]);
mainApp.factory('services',['$http',function($http) {

	var service = { // our factory definition
		search : function($scope, options) {// 查询数据列表
			if(document.getElementById("page")){
				if(options.page){
					document.getElementById("page").value = options.page;
				}else{
					document.getElementById("page").value = 1;
				}
			}
			var url = options.url, 
				data = null, 
				postCfg = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				};
			
			if(!options.formIgnore){// 未忽略form表单参数
				data = jQuery(document.forms["frm"]).serialize();
			}
			
			$http.post(url, data, postCfg).success(function(data) {
				
				if(data.maxpage&&data.page){
					data.isFirstPage = (data.page==1);
					data.isLastPage = (data.page==data.maxpage);
					data.isMiddlePage = (data.page>1&&data.page<data.maxpage);
				}
				
				$scope.responseData = data;
				console.log($scope.responseData);
				if(data.pagesize&&document.getElementById("pagesize")){
					document.getElementById("pagesize").value = data.pagesize;
				}
				
				if(options.afterQuery){
					options.afterQuery();
				}
			});
		},
		updateStatus:function($scope, options) {// 更新数据状态（含批量）
			var url = options.url, 
				params = jQuery.param(options.params), 
				postCfg = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				};
				//alert(params);
			$http.post(url, params, postCfg).success(function(data) {
				$scope.responseData = data;
				console.log($scope);
				
				options.afterUpdate();
			});
		},		
		fetchData : function($scope, options) {// 查询数据
			var url = options.url, 
				params = options.params,
				scopeField = options.scopeField,
				postCfg = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				};
			
			if (typeof options.async == "undefined") {
				options.async = true;
			}
						
			if(options.async){
				$http.post(url, params, postCfg).success(function(data) {				
					if(scopeField){
						$scope[scopeField] = data;
					}else{
						$scope.o = data;
					}
					console.log({async:options.async,scope:$scope});
					
					if(options.afterQuery){
						options.afterQuery();
					}
				});
			}else{
				jQuery.ajax({
					async:false,
					url:url,
					cache:false, 
					type:'POST',
					dataType:'json',
					data:params,
					success:function(data) {
						if(scopeField){
							$scope[scopeField] = data;
						}else{
							$scope.o = data;
						}
						console.log({async:options.async,scope:$scope});
						
						if(options.afterQuery){
							options.afterQuery();
						}
					},
					error : function() {
						alert("fetchData异常！");  
					}				
				});
			}

		},		
		save : function($scope, options) {// 保存数据
			var url = options.url, 
				data = jQuery(document.forms["frm"]).serialize(), 
				scopeField = options.scopeField,
				postCfg = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8'
					}
				};

			$http.post(url, data, postCfg).success(function(data) {

				if(scopeField){
					$scope[scopeField] = data;
				}else{
					$scope.saveResult = data;
				}
				
				console.log($scope);
				
				if(options.afterSave){
					options.afterSave();
				}
			});
		}
	};
	return service;
}]);