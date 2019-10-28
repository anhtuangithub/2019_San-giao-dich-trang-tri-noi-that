apt.controller('LoginController',function($scope,$http,URL_Home){
	
	
	$scope.createCustomer = function(){
		var url = URL_Home + 'users/dang-ki';
		var data = $scope.info;
		let dataLogin = {email: data.email,password: data.password};
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json','Csrf-Token': '@helper.CSRF.getToken.value'}
		})
		.then(function (response){
			toastr.success('Tạo tài khoản thành công', 'Thành công',{timeOut: 5000, escapeHtml: true});
			window.location.reload();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình tạo tài khoản', 'Gặp lỗi!');
			
		});
	}	
	
	$scope.checkEmail = false;
	$scope.inputError =false;
	$scope.compare = function() {
	    $scope.result = angular.equals($scope.info.password, $scope.info.repassword);
	    	if($scope.result) $scope.inputError =false;
	    	else $scope.inputError = true;
	};
	
	$scope.nameError = false;
	$scope.nameRight = false;
	$scope.ktTen = function(){
		if($scope.info.email == "" ){
			$scope.nameError = false;
			$scope.nameRight = false;
			$scope.checkEmail = false;
		}
		else{
			var requestURL = URL_Home+ 'users/check/' + $scope.info.email;
		 
			$http.get(requestURL)
			.then(function(response){
				if(response.data.message == "false"){
					$scope.nameRight = false;
					$scope.nameError = true;
					$scope.checkEmail = false;
				}	
				else{
					$scope.nameRight = true;
					$scope.nameError = false;
					$scope.checkEmail = true;
				} 
							
			})
			.catch(function(response){
				$scope.nameError = false;
				$scope.nameRight = false;
				$scope.checkEmail = false;
			});
		}
		
	}

	$scope.modalResetPass = function(){
		jQuery('#modalPass').modal('show');
	}

	$scope.resetPass = function(){
		console.log($scope.resetPass.email);
		$http.get(URL_Home +'users/resetPassword?email='+$scope.resetPass.email)
		.then(function(response){
			console.log(response);
		})
		.catch(function(response){
			console.log(response);
		})
	}
	
});