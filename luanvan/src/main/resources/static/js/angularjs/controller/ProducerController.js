app.controller('ProducerController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'producers')
	.then(function(response){
		$scope.listproducers = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'producers')
		.then(function(response){
			$scope.listproducers = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'producers';
		var data = $scope.producers;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
			$scope.producers = {name:""};

		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.edit = function(id){
		jQuery("html, body").animate({ scrollTop: 0 }, "slow");
		$http.get(URL_Main + 'producers/'+id)
		.then(function(response){
			$scope.producers = response.data;
		});
	}
	$scope.changeStatus = function(id,name,status){
		var url = URL_Main + 'producers';
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
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.cancel = function(){
		$scope.producers = {name:""};
	}
});