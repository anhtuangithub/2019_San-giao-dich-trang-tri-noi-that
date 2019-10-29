apt.controller('UpdatePasswordController',function($scope,$http,URL_Home){
	
	let searchParams = new URLSearchParams(window.location.search);
	$scope.updatePass = function(){
		var url = URL_Home + 'users/savePassword';
		let data = {email : searchParams.get('email'), password: $scope.info.password};
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json','Csrf-Token': '@helper.CSRF.getToken.value'}
		})
		.then(function (response){
			location.replace(URL_Home+'noithat246.vn/dang-nhap?success=true');
			console.log(response);
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật mật khẩu', 'Gặp lỗi!');
			$scope.errors = response.data.errors;
			
		});
	}	
	
	$scope.inputError =false;
	$scope.compare = function() {
	    $scope.result = angular.equals($scope.info.password, $scope.info.repassword);
	    	if($scope.result) $scope.inputError =false;
	    	else $scope.inputError = true;
	};

});