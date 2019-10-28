app.controller('ColorController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'colors')
	.then(function(response){
		$scope.listcolors = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'colors')
		.then(function(response){
			$scope.listcolors = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'colors';
		var data = $scope.colors;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật màu sắc thành công', 'Thành công',{timeOut: 3000, escapeHtml: true});
			refreshData();
			$scope.colors = {name:""};
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.edit = function(id){
		jQuery("html, body").animate({ scrollTop: 0 }, "slow");
		$http.get(URL_Main + 'colors/'+id)
		.then(function(response){
			$scope.colors = response.data;
		});
	}
	$scope.changeStatus = function(id,name,status){
		var url = URL_Main + 'colors';
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
			refreshData();
		})
		.catch(function (response){
			console.log(response);
			
		});
	}
	
	$scope.cancel = function(){
		$scope.colors = {name:""};
	}
});