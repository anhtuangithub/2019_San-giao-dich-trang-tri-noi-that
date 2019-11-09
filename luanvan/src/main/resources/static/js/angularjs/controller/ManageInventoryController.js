app.controller('ManageInventoryController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'products/store')
	.then(function(response){
		$scope.products = response.data;
	});

	
	function refreshData(id){
		$http.get(URL_Main + 'products/detail-product/'+id)
		.then(function(response){
			$scope.product = response.data;
		});

		$http.get(URL_Main + 'products/inventory-of-product/'+id)
		.then(function(response){
			$scope.inventorys = response.data;
		});
	}

	$scope.chooseProduct = function(id){
		$scope.productId = id;
		refreshData(id);
	}
	
	$scope.save =function(){
		var data = {products : {id: $scope.productId },
					date_receipt: $scope.inventory.date_receipt,
					quantity : $scope.inventory.quantity};

		var url = URL_Main + 'products/update-quantity';
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật tồn kho thành công', 'Thành công',{timeOut: 3000, escapeHtml: true});
			refreshData($scope.productId);
			$scope.inventory = null;
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
});