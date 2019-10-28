apt.controller('HomeController',function($scope,$http,URL_Home){
	
	
	refreshData();
	
	function refreshData(){
		$http.get(URL_Home + 'products/promotion-products')
		.then(function(response){
			$scope.products = response.data;
		});
		
		$http.get(URL_Home + 'products/random-home')
		.then(function(response){
			$scope.proRandom = response.data;
		});
	}
	
});