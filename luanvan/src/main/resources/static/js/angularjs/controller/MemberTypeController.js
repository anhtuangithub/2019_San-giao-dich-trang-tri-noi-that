app.controller('MemberTypeController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'member-types')
	.then(function(response){
		$scope.listmembertypes = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'member-types')
		.then(function(response){
			$scope.listmembertypes = response.data;
		});
	}
	$scope.save = function(){
		
		var url = URL_Main + 'member-types';
		var data = $scope.membertypes;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			jQuery('#modalMemberType').modal('hide');
			refreshData();
			$scope.membertypes = {name:""};
			
		})
		.catch(function (response){
			console.log(response);
			
		});
	}
	
	$scope.edit = function(id,name){
		$scope.frmTitle = "Chỉnh sửa Thẻ" + name;

		jQuery('#modalMemberType').modal('show');
		
		$http.get(URL_Main + 'member-types/'+id)
		.then(function(response){
			$scope.membertypes = response.data;
			
		});
	}
	
	
	$scope.cancel = function(){
		$scope.membertypes = {name:""};
		jQuery('#modalMemberType').modal('hide');
	}
});