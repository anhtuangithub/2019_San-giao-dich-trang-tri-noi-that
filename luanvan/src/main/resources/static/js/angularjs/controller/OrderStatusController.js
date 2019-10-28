app.controller('OrderStatusController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'orderstatus')
	.then(function(response){
		$scope.liststatus = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'orderstatus')
		.then(function(response){
			$scope.liststatus = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'orderstatus';
		var data = $scope.status;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			refreshData();
			$scope.status = {name:""};
		})
		.catch(function (response){
			console.log(response);
			
		});
	}
	
	$scope.edit = function(id){
		jQuery("html, body").animate({ scrollTop: 0 }, "slow");
		$http.get(URL_Main + 'orderstatus/'+id)
		.then(function(response){
			$scope.status = response.data;
		});
	}
	$scope.changeStatus = function(id,name,status){
		var url = URL_Main + 'orderstatus';
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
		$scope.status = {name:""};
	}
});