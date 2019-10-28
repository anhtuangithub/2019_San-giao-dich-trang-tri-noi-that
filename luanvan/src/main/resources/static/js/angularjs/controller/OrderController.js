app.controller('OrderController',function($scope,$http,URL_Main){
	
	
	$http.get(URL_Main + 'orders/by-store')
	.then(function(response){
		$scope.listOrder = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'orders/by-store')
		.then(function(response){
			$scope.listOrder = response.data;
		});
	}
	
	
	//Open modal
	$scope.modal= function(id){
		$scope.frmTitle = "Thông tin chi tiết đơn hàng ";
		
		$http.get(URL_Main + 'orders/'+id)
		.then(function(response){
			$scope.order = response.data;
		});

		$http.get(URL_Main + 'orders/list-detail-order/'+id)
		.then(function(response){
			$scope.orderDetails = response.data;
		});
		
		jQuery('#modalOrder').modal('show');
	}
	
	$scope.xacNhanDH = function(id){
		changeStatus(id,2);
	};
	$scope.shipping = function(id){
		changeStatus(id,3);
	};
	$scope.daGiao = function(id){
		changeStatus(id,5);
	};
	$scope.huyDH = function(id){
		if(confirm("Bạn đồng ý hủy đơn hàng này!!!"))
		changeStatus(id,7);
	};
	function changeStatus(id,statusId){
		var url = URL_Main + 'orders/update-status/'+id+'/status/'+statusId;
		$http({
			method : 'POST',
			url : url,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			jQuery('#modalOrder').modal('hide');
			refreshData();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
		});
	}
	
	
	
	
});