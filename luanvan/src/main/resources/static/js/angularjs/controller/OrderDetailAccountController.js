apt.controller('OrderDetailAccountController',function($scope,$http,URL_Home){
	
	
	var thisPath = window.location.pathname;
	var id = thisPath.substring(thisPath.lastIndexOf("-")+1);
	
	$http.get(URL_Home + 'orders/account-order-detail/'+id)
	.then(function(response){
		$scope.orderGroup = response.data;
		$scope.checkDestroy = true;
		for(i=0; i< $scope.orderGroup.orders.length; i++){
			if($scope.orderGroup.orders[i].orderStatus.id ==1){
				$scope.checkDestroy = false;
			}
		}

	})
	.catch(function(response){
		history.back();
	});
	
	
	//Tạo nút sao cho đánh giá
	 jQuery(function () {
         jQuery("#rateYo").rateYo({
             starWidth: "20px",
             rating:5,
             fullStar: true
         });
      
     });

	 $scope.modal= function(productid,orderid){
		jQuery("#rateYo").remove();
		jQuery('#modalViews').modal('show');
		
		$http.get(URL_Home + 'reviews/check-review-of-customer/'+productid+'/'+orderid)
		.then(function(response){
			jQuery("#feedback").html('<div id="rateYo"></div>');
			$scope.review = response.data;
			jQuery(function () {
		         jQuery("#rateYo").rateYo({
		             starWidth: "20px",
		             rating:$scope.review.rating,
		             fullStar: true
		         });
		      
		     });
		})
		.catch(function(response){
			$scope.review =null;
		})
		 //Get giá trị sao vừa đánh giá
	  	$scope.addReview = function(){
	    /* get rating */
		  	var $rateYo = jQuery("#rateYo").rateYo();
		  	var url = URL_Home + "reviews";
		  	var rating = $rateYo.rateYo("rating");
		  	var data = {"id":$scope.review.id,
		  				"content":jQuery('#content').val(),
		  				"rating": rating,
		  				products:{"id":productid },
		  				order:{id:orderid}
		  				};

		  	$http({
				method : 'POST',
				url : url,
				data : data,
				headers : {'Content-type' : 'application/json'}
			})
			.then(function (response){
				toastr.success('Thêm bình luận thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
				jQuery('#modalViews').modal('hide');
			})
			.catch(function (response){
				toastr.error('Có lỗi trong quá trình thêm bình luận', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
				
			});
		  
	  	};
	};

	$scope.huyDathang = function(orderGroupId){

		Swal.fire({
			  title: 'Bạn có chắc?',
			  text: "Hủy đơn hàng này không !!!",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Đồng ý!',
			  cancelButtonText: 'Không, thoát!',
			}).then((result) => {
				if (result.value) {
					$http.delete(URL_Home + 'orders/huy-don-hang/'+orderGroupId)
					.then(function(response){
						toastr.success('Hủy thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});

					})
					.catch(function(response){
						toastr.error('Hủy hóa đơn thất bại', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
					});
				}
			})
	};

     
});

