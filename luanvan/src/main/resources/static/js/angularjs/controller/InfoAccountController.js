apt.controller('InfoAccountController',function($scope,$http,URL_Home){
	
	$http.get(URL_Home + 'users/info')
	.then(function(response){
		$scope.infoUser = response.data;
	});
	
	$http.get(URL_Home + 'orders/account-recent')
	.then(function(response){
		$scope.orderGroups = response.data;
	});
	
});