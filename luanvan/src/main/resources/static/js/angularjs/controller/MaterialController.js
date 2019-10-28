app.controller('MaterialController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'materials')
	.then(function(response){
		$scope.listmaterials = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'materials')
		.then(function(response){
			$scope.listmaterials = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'materials';
		var data = $scope.materials;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
			$scope.materials = {name:""};
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.edit = function(id){
		jQuery("html, body").animate({ scrollTop: 0 }, "slow");
		$http.get(URL_Main + 'materials/'+id)
		.then(function(response){
			$scope.materials = response.data;
		});
	}
	$scope.changeStatus = function(id,name,status){
		var url = URL_Main + 'materials';
		var tempstatus = 0;
		if(status == 1)
			tempstatus = 0;
		else tempstatus = 1;
		var data = {id:id,name:name, status:tempstatus};
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật trạng thái thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật trạng thái', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.cancel = function(){
		$scope.materials = {name:""};
	}
});