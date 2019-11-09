apt.controller('DKTVController',function($scope,$http,URL_Home){
	
	var thisPath = window.location.search.substr(1);
	var id = thisPath.substring(thisPath.lastIndexOf("id")+3);
	
	$http.get(URL_Home + 'member-types/'+id)
	.then(function(response){
		$scope.membertype = response.data;
	});
	
	$http.get(URL_Home + 'provinces')
	.then(function(response){
		$scope.dsTinh  = response.data;
	});

	$scope.disableSelectQuan =true;
	$scope.disableSelectTinh =true;

	$scope.filterQuan = function(){
		$scope.disableSelectTinh = false;
		$http.get(URL_Home + 'districts/findbyprovince/'+$scope.info.stores.province.provinceid)
		.then(function(response){
			$scope.dsQuan = response.data;
		});

	}
	$scope.filterXa = function(){
		$scope.disableSelectQuan =false;
		$http.get(URL_Home + 'wards/findbydistrict/'+$scope.info.stores.district.districtid)
		.then(function(response){
			$scope.dsXa = response.data;
		});

	}
	$scope.createStore = function(){
		var url = URL_Home + 'users/dktv/'+id;
		var data = $scope.info;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json','Csrf-Token': '@helper.CSRF.getToken.value'}
		})
		.then(function (response){
			toastr.success('Đăng kí thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			window.location = "/noithat246.vn";
		})
		.catch(function (response){
			$scope.errors = response.data.errors;
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
});