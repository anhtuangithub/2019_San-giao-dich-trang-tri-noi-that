app.controller('QuanController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'districts')
	.then(function(response){
		$scope.dsLoaiQuan = response.data;
		console.log($scope.dsLoaiQuan);
	});
	
	
});

