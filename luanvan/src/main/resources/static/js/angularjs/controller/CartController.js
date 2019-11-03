apt.controller('CartController',function($scope,$http,URL_Home){
	
	var cart =  JSON.parse(localStorage.getItem("cart"));
	
	var shipping =  JSON.parse(localStorage.getItem("info-shipping"));
	$scope.shipping = shipping;
	
	$scope.hideOrder = false;
	$scope.showOrder = true;
	$scope.disableSelectQuan =true;
	$scope.disableSelectTinh =true;
	
	$http.get(URL_Home + 'provinces')
	.then(function(response){
		$scope.dsTinh  = response.data;
	});

	$scope.filterQuan = function(){
		$scope.disableSelectTinh = false;
		$http.get(URL_Home + 'districts/findbyprovince/'+$scope.saveShipping.tinh.provinceid)
		.then(function(response){
			$scope.dsQuan = response.data;
		});

	}
	$scope.filterXa = function(){
		$scope.disableSelectQuan =false;
		$http.get(URL_Home + 'wards/findbydistrict/'+$scope.saveShipping.quan.districtid)
		.then(function(response){
			$scope.dsXa = response.data;
		});

	}

	$scope.dathang = function(){
		if($scope.logged){
			$scope.hideOrder = true;
			$scope.showOrder = false;
		}
		else{
			window.location = "../noithat246.vn/dang-nhap";
		}
		
		$http.get(URL_Home + 'orders/exchange')
		.then(function(response){
			$scope.gia  = response.data;
		});
		
	}
	$scope.suaThongTin = function(){
		$("#updateStorge").removeClass("hidden");
		$("#defaultAddress").addClass("hidden");
		
	}
	$scope.huySuaShipping = function(){
		$("#updateStorge").addClass("hidden");
		$("#defaultAddress").removeClass("hidden");
	}
	
	$scope.submitAddress = function(){
		$("#updateStorge").addClass("hidden");
		$("#defaultAddress").removeClass("hidden");
		if(shipping != null || shipping == null){
			shipping = [];
			var address = $scope.saveShipping.address+", "+$scope.saveShipping.xa.name+", "+ $scope.saveShipping.quan.name +", "+ $scope.saveShipping.tinh.name;
			shipping = {address:address,phone:$scope.saveShipping.phone};
		}
		localStorage.setItem("info-shipping", JSON.stringify(shipping));
		$scope.shipping =  JSON.parse(localStorage.getItem("info-shipping"));
		$scope.saveShipping = null;
		$scope.disableSelectQuan =true;
		$scope.disableSelectTinh =true;
	
	}
	
	if(cart != null) refreshData();
	$scope.quantityOfCart = quantityofcart();
	function refreshData(){
		
		jQuery("#countItem").text(quantityofcart());
		$scope.quantityOfCart = quantityofcart();
		
		var url = URL_Home + 'products/cart';
		
		$http({
			method : 'POST',
			url : url,
			data : cart,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			$scope.cart = groupBy(response.data, "stores");
			$scope.total = getTotal(response.data);
			$scope.canBuy = canBuy(response.data);
		})
		.catch(function (response){
			console.log(response);
			
		});
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
	
	function getTotal(cart){
		var total = 0;
		cart.forEach(function(pro){
			total +=pro.buyQuantity*pro.price_new;
		})
		return total;
	}
	
	function canBuy(cart){
		for(pro of cart){
			if(pro.inventory_number <= 0) return true;
		}
		return false;
	}
	
	//Map quantity version 1
//	function listCart(carts, locals){
//		carts.forEach(function(cart){
//			locals.forEach(function(local){
//				if(cart.id == local.id){
//					cart.quantity = local.quantity;
//				}
//			})
//		})
//		return carts;
//	}
	
	
	// function updateCart(objectArray){
	// 	localStorage.removeItem("cart");
	// 	var cart = [];
	// 	for(var i = 0; i < objectArray.length ; i++){
	// 		cart.push({id:objectArray[i].id,quantity:objectArray[i].buyQuantity});
	// 	}
	// 	localStorage.setItem("cart", JSON.stringify(cart));
	// }

	function groupBy(objectArray, property) {
		return objectArray.reduce(function (acc, obj) {
			var key = obj[property].id;
			if (!acc[key]) {
				acc[key] = [];
			}
			acc[key].push(obj);
			return acc;
		}, {});
	}

	
	
	$scope.addItem = function(id,slton){
		cart.forEach(function(itemCart){
			if(itemCart.id == id && slton > itemCart.quantity){
				itemCart.quantity += 1;
			}
		})
		localStorage.setItem("cart", JSON.stringify(cart));
		refreshData();
	}
	
	$scope.minusItem = function(id){
		for (var i =0; i< cart.length; i++) {
		    if (cart[i].id == id && cart[i].quantity > 1) {
		    	cart[i].quantity -= 1;
//		    	if(cart[i].quantity == 0){
//		    		cart.splice(i, 1);
//		    	}
		    }
		}
		localStorage.setItem("cart", JSON.stringify(cart));
		refreshData();
	}
	
	$scope.deleteItem = function(id){
		for (var i =0; i< cart.length; i++) {
		    if (cart[i].id == id) {
		    	cart.splice(i, 1);
		    }
		}
		localStorage.setItem("cart", JSON.stringify(cart));
		refreshData();
	}
	
	
	$scope.thanhtoan = function(){
		thanhtoan(1);
	};
	function thanhtoan(paymentType){
		var url = URL_Home + 'orders/orderGroup';
		var listOrders = [];
		var grouped = $scope.cart;
		var infoOrderGroup = {
				address:$scope.shipping.address,
				phone:$scope.shipping.phone,
				paymentType: {id: paymentType}};
		
		for (var property in grouped) {
			//tạo 1 order
			var order = {
				address: $scope.shipping.address,
				phone:$scope.shipping.phone,
				paymentType: {id: 1},
				stores: {id: Number(property)},
				ordersDetailDTO: []
			}

			grouped[property].forEach(function(ele){
				//tạo order detail
				var orderDetails = { product:{id: null}, quantity: null,promotion:{id: null} };
				orderDetails.product.id = ele.id;
				orderDetails.quantity = ele.buyQuantity;
				orderDetails.promotion.id = ele.maxPromotion.id;
				order.ordersDetailDTO.push(orderDetails);
			});
			listOrders.push(order);
		}
		
		$http({
			method : 'POST',
			url : url,
			data : {orderGroup:infoOrderGroup,orderDTO:listOrders},
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			if(response.data.message != undefined){
				toastr.error('Có lỗi trong quá trình đặt hàng', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
				refreshData();
				
			}
			else{
				toastr.success('Đặt hàng thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
				window.location.reload();
				localStorage.removeItem("cart");
			}
		})
		.catch(function (response){
			console.log(response);			
		});
	}
	
	
	
// paypal
	    paypal.Button.render({
	         env: 'sandbox',
	         style: {
	             color:  'gold',
	             shape:  'rect',
	             label:  'paypal',
	             height: 44,
	             tagline : false,
	             size:'responsive',
	         },

	         funding: {
	             allowed: [
	             paypal.FUNDING.CARD,
	             paypal.FUNDING.CREDIT
	             ],
	             disallowed: []
	         },

	         client: {
	             sandbox: 'AcY_RqiSajU7n4gcqvWS4wtQNRU2vYG1WZwi7rIpcf1U1-PXyPogoQ6rTBUPuNE6qfLOAkX4iRrlaQH8',
	             production: ''
	         },

	         
	         payment: function(data, actions) {
	             return actions.payment.create({
	                 "transactions": [
	                    {
	                      "amount": {
	                        "total": Math.round(document.getElementById('total').value/$scope.gia.bantienmat),
	                        "currency": "USD",
	                      },
	                      "description": "The payment transaction description.",
	                      "payment_options": {
	                        "allowed_payment_method": "INSTANT_FUNDING_SOURCE"
	                      },
	                      "soft_descriptor": "ECHI5786786",
	                      "item_list": {
	                        "items": [
	                          {
	                            "name": "Tổng số tiền của hóa đơn",
	                            "quantity": "1",
	                            "price": Math.round(document.getElementById('total').value/$scope.gia.bantienmat),
	                            "currency": "USD"
	                          },
	                        ],
//	                        "shipping_address": {
//	                          "recipient_name": "Brian Robinson",
//	                          "line1": $scope.shipping.address,
//	                          "line2": "Unit #34",
//	                          "city": $scope.shipping.address,
//	                          "country_code": "US",
//	                          "postal_code": "95131",
//	                          "phone": $scope.shipping.phone,
//	                          "state": "CA"
//	                        }
	                      }
	                    }
	                  ]
	             });
	         },


	         onAuthorize: function (data, actions) {
	             return actions.payment.execute()
	             .then(function () {
	            	 //Hình thức thanh toán bằng paypal id bằng 2
	            	 thanhtoan(2);
	             
	             });
	         }
	     }, '#paypal-button');
	    
	   
});