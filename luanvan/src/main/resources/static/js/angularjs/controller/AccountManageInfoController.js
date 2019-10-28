apt.controller('AccountManageInfoController',function($scope,$http,URL_Home){
	
			

	refreshdata();

	function refreshdata(){
		$http.get(URL_Home + 'customers/thong-tin')
		.then(function(response){
			$scope.saveCustomer  = response.data;
			$scope.saveCustomer.birthday = new Date($scope.saveCustomer.birthday);
		});
	}

	$scope.createCustomer = function(){
		var url = URL_Home + 'customers';
		var data = $scope.saveCustomer;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json','Csrf-Token': '@helper.CSRF.getToken.value'}
		})
		.then(function (response){
			toastr.success('Cập nhật thông tin tài khoản thành công', 'Thành công',{timeOut: 3000, escapeHtml: true});
			refreshdata();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật thông tin', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}	
});