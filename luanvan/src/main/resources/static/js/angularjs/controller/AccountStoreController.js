apt.controller('AccountStoreController',function($scope,$http,URL_Home){
	

	
	$http.get(URL_Home + 'provinces')
	.then(function(response){
		$scope.provinces  = response.data;
	});

	$http.get(URL_Home + 'stores/thong-tin')
	.then(function(response){
		$scope.store = response.data;
		
	}).then(function(){
		$http.get(URL_Home + 'districts/findbyprovince/'+$scope.store.province.provinceid)
		.then(function(response){
			$scope.districts = response.data;
		});
	}).then(function(){
		$http.get(URL_Home + 'wards/findbydistrict/'+$scope.store.district.districtid)
		.then(function(response){
			$scope.wards = response.data;
		});
	});

	$scope.filterQuan = function(){
		$http.get(URL_Home + 'districts/findbyprovince/'+$scope.store.province.provinceid)
		.then(function(response){
			$scope.districts = response.data;
		});

	}
	$scope.filterXa = function(){
		$http.get(URL_Home + 'wards/findbydistrict/'+$scope.store.district.districtid)
		.then(function(response){
			$scope.wards = response.data;
		});

	}

	$scope.update = function(){
		var data = {name:$scope.store.name,
						address: $scope.store.address,
						district: {districtid: $scope.store.district.districtid},
						id: 1,
						introduce: $scope.store.introduce,
						phone: $scope.store.phone,
						province: {provinceid: $scope.store.province.provinceid},
						ward: {wardid: $scope.store.ward.wardid},
						website: $scope.store.website
					};
		$http({
			method : 'POST',
			url : URL_Home + 'stores',
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			$http.get(URL_Home + 'stores/thong-tin')
			.then(function(response){
				$scope.store = response.data;
				
			});

		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}


	
});