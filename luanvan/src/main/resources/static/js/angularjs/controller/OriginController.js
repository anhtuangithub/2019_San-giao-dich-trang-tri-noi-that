app.controller('OriginController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'origins')
	.then(function(response){
		$scope.listorigins = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'origins')
		.then(function(response){
			$scope.listorigins = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'origins';
		var data = $scope.origins;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
			$scope.origins = {name:""};

		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.edit = function(id){
		jQuery("html, body").animate({ scrollTop: 0 }, "slow");
		$http.get(URL_Main + 'origins/'+id)
		.then(function(response){
			$scope.origins = response.data;
		});
	}
	
	$scope.cancel = function(){
		$scope.origins = {name:""};
	}
});