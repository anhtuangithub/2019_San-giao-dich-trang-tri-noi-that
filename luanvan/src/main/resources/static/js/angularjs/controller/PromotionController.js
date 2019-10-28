app.controller('PromotionController',function($scope,$timeout,$http,URL_Main){
	
	$http.get(URL_Main + 'promotions/khuyen-mai-store')
	.then(function(response){
		$scope.listpromotion = response.data;
	});
	$http.get(URL_Main + 'products/store')
	.then(function(response){
		$scope.products = response.data;
	});
	function refreshData(){
		$http.get(URL_Main + 'promotions/khuyen-mai-store')
		.then(function(response){
			$scope.listpromotion = response.data;
		});
	}
	
	$scope.modal= function(state,id){
		$scope.state = state;
		
		switch(state){
			case "add": 
				$scope.frmTitle = "Thêm khuyến mãi mới";
				$scope.promotion = {
								name:"",
								description:"",
								status:0,
								sale_off:"",
								start_time:"",
								end_time:""
							};
				break;

			case "edit": 
				
				$scope.frmTitle = "Sửa thông tin khuyến mãi";
				
				
				$http.get(URL_Main + 'promotions/'+id)
				.then(function(response){
					$scope.promotion = response.data;
					$scope.promotion.start_time= new Date($scope.promotion.start_time);
					$scope.promotion.end_time= new Date($scope.promotion.end_time);
					$timeout(function(){
						jQuery('.selectpicker').selectpicker('refresh');
					},1);
					
					
				})
				.then(function(){
					var selectedValue = [];
					$scope.promotion.products.map(product => selectedValue.push(product.id));
					$scope.promotion.products = selectedValue;
				});
				
				
				break;
		}
		jQuery('#myModal').modal('show');
	}
	
	$scope.save = function(){
		var url = URL_Main + 'promotions/promotion-products';
		

		$scope.promotion.start_time= new Date($scope.promotion.start_time);
		$scope.promotion.end_time= new Date($scope.promotion.end_time);
		var newArr = [];
		product = $scope.promotion.products;
		if(product != null) product.map(value => newArr.push({id:value}));
		$scope.promotion.products= newArr;
		var data = $scope.promotion;
		
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			
			jQuery('#myModal').modal('hide');
			toastr.success('Cập nhật khuyến mãi thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
			$scope.promotion = {
					name:"",
					description:"",
					status:0,
					sale_off:"",
					start_time:"",
					end_time:""
				};
			
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	// Xử lí modal Img
	$scope.confirmDelete = function(id,name){
		var isConfirmDelete = confirm('Bạn có chắc muốn xóa khuyến mãi '+ name +' này không');
		if(isConfirmDelete){
			$http.delete(URL_Main+'/promotions/'+id)
			.then(function(response){
				toastr.success('Xóa thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
				refreshData();
			})
			.catch(function(response){
				toastr.error('Có lỗi khi xóa', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			});
		}
		else{
			return false;
		}
	}
});