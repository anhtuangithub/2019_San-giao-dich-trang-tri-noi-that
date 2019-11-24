apt.controller('InfoAccountController',function($scope,$http,URL_Home){
	
	var shipping =  JSON.parse(localStorage.getItem("info-shipping"));
	$scope.shipping = shipping;
	
	$http.get(URL_Home + 'users/info')
	.then(function(response){
		$scope.infoUser = response.data;
	});
	
	$http.get(URL_Home + 'orders/account-recent')
	.then(function(response){
		$scope.orderGroups = response.data;
	});
	
});