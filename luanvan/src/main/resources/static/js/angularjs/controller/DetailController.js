apt.controller('DetailController',function($scope,$http,URL_Home){
	
	
	var thisPath = window.location.pathname;
	var id = thisPath.substring(thisPath.lastIndexOf("/")+1);
	
	refreshData();
	sanPhamDaXem();
	
	function sanPhamDaXem(){
		var spDaXem =  JSON.parse(localStorage.getItem("spDaXem"));
		var dem = 0;
		//Quá 5 cái xóa phần từ sản phẩm thêm vào đầu tiên
		if( spDaXem != null ){
			if(spDaXem.length > 10){
				spDaXem.splice(0,1);
				localStorage.setItem("spDaXem", JSON.stringify(spDaXem));	
			}
			
		}
		if(spDaXem == null){
			spDaXem = [];
			dem = 1;
			spDaXem.push({id:id});
		}
		else{
			spDaXem.forEach(function(pro){
				if(pro.id == id){
					dem +=1;
				}
			})
			if(dem == 0){
				spDaXem.push({id:id});
			}
		}
		localStorage.setItem("spDaXem", JSON.stringify(spDaXem));
	}
	
	function refreshData(){
		jQuery("#countItem").text(quantityofcart());
		$scope.quantityOrderDetail = 1;
		
		$http.get(URL_Home + 'products/detail-product/'+id)
		.then(function(response){
			$scope.product = response.data;
			
		})
		.then(function(){
			jQuery(function () {
			 	jQuery("#rateYoProduct").rateYo({
			 		starWidth: "20px",
			    	rating: $scope.product.avgstart,
			    	readOnly: true
			  	});
			 
			});
		})
		.then(function(){
			$http.get(URL_Home + 'products/related-product/'+$scope.product.categorys.id)
			.then(function(response){
				$scope.productRelated = response.data;
			})
			
		});
		
		$http.get(URL_Home + 'products/hinh-cua-san-pham/'+id)
		.then(function(response){
			$scope.listImages = response.data;
		});
		
		$http.get(URL_Home + 'reviews/product-detail/'+id)
		.then(function(response){
			$scope.reviews = response.data;
		});
		
		
		$http.get(URL_Home + 'questions/detail-of-product/'+id)
		.then(function(response){
			$scope.questions = response.data;
		});
		
	}
	
	
	var cart =  JSON.parse(localStorage.getItem("cart"));
	
	$scope.muahang = function(){
		var slCon = $scope.product.inventory_number;
		var slMua = Number(jQuery('#quantity').val());
		var dem = 0;
		if(cart == null && slCon >= slMua){
			cart = [];
			dem = 1;
			cart.push({id:id,quantity:slMua});
		}
		else{
			cart.forEach(function(pro){
				if(pro.id == id){
					if(slCon >= (pro.quantity + slMua)){
						pro.quantity = pro.quantity + slMua;
					}
					dem +=1;
				}
			})
			if(dem == 0){
				cart.push({id:id,quantity:slMua});
			}
		}
		localStorage.setItem("cart", JSON.stringify(cart));
		refreshData();
		
	}
	$scope.muangay = function(){
		var slCon = $scope.product.inventory_number;
		var slMua = Number(jQuery('#quantity').val());
		var dem = 0;
		if(cart == null && slCon >= slMua){
			cart = [];
			dem = 1;
			cart.push({id:id,quantity:slMua});
		}
		else{
			cart.forEach(function(pro){
				if(pro.id == id){
					if(slCon >= (pro.quantity + slMua)){
						pro.quantity = pro.quantity + slMua;
					}
					dem +=1;
				}
			})
			if(dem == 0){
				cart.push({id:id,quantity:slMua});
			}
		}
		localStorage.setItem("cart", JSON.stringify(cart));
		window.location ="/noithat246.vn/gio-hang";
		
	}
	
	function quantityofcart(){
		var cart =  JSON.parse(localStorage.getItem("cart"));
		var dem = 0;
		if(cart == null){
			return 0;
		}
		else{
			cart.forEach(function(pro){
				dem += pro.quantity;
			})
			return dem;
		}
	}
	
	$scope.cauHoi = function(){
		var data = {content:$scope.question,products:{id:id}};
		console.log(data);
		$http({
			method : "post",
			url : URL_Home + "questions",
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			$scope.question= null;
			toastr.success('Câu hỏi của bạn đã được ghi nhận', 'Thành công',{timeOut: 2000, escapeHtml: true});
		})
		.catch(function (response){
			toastr.error('Có lỗi khi đặt câu hỏi', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}

	
});
