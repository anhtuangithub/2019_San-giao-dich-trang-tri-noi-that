app.controller('ManageCustomerController',function($scope,$http,URL_Main){
	
	
	$http.get(URL_Main + 'customers/all-customer-dto')
	.then(function(response){
		$scope.listCustomer = response.data;
		console.log($scope.listCustomer);
	});
	
//	function refreshData(){
//		$http.get(URL_Main + 'reviews')
//		.then(function(response){
//			$scope.listreview = response.data;
//		});
//	}
	
	// Xử lí modal Img
	$scope.modal= function(id){
		$scope.frmTitle = "Chỉnh sửa Review ";
//		$http.get(URL_Main + 'reviews/'+id)
//		.then(function(response){
//			$scope.reviews = response.data;
//		})
//		.then(function(){
//		 	jQuery("#rateYoReview").rateYo({
//		 		starWidth: "20px",
//		    	rating: 0,
//		    	fullStar: true,
//		    	readOnly: true
//		  	});
//		})
//		.then(function(){
//			jQuery("#modalReview .jq-ry-container>.jq-ry-group-wrapper>.jq-ry-group.jq-ry-rated-group").css('width',$scope.reviews.rating*20+'%');
//		});
		
		jQuery('#modalReview').modal('show');
	}
	
//	$scope.save = function(){
//		var url = URL_Main + 'reviews';
//		var data = $scope.reviews;
//		$http({
//			method : 'POST',
//			url : url,
//			data : data,
//			headers : {'Content-type' : 'application/json'}
//		})
//		.then(function (response){
//			refreshData();
//			jQuery('#modalReview').modal('hide');
//		})
//		.catch(function (response){
//			console.log(response);
//			
//		});
//	}
	
});