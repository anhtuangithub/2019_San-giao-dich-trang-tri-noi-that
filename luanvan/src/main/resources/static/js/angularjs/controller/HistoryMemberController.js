apt.controller('HistoryMemberController',function($scope,$http,URL_Home){
	

	refreshData();

	function refreshData(){
		$http.get(URL_Home + 'members/lich-su-dang-ki')
		.then(function(response){
			$scope.members  = response.data;
		});
	}

	$http.get(URL_Home + 'member-types')
	.then(function(response){
		$scope.memberTypes  = response.data;
	})
	.then(function(){
		$http.get(URL_Home + 'orders/exchange')
		.then(function(response){
			$scope.gia  = response.data;
		});
	});

	$scope.GiaHanTK = function(){
		$('#paypal-button').removeClass('hidden');
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
	                        "total": Math.round($scope.giaHan.price/$scope.gia.bantienmat),
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
	                            "price": Math.round($scope.giaHan.price/$scope.gia.bantienmat),
	                            "currency": "USD"
	                          },
	                        ],
	                      }
	                    }
	                  ]
	             });
	         },


	         onAuthorize: function (data, actions) {
	             return actions.payment.execute()
	             .then(function () {
	            	$http.get(URL_Home + 'members/gia-han/'+$scope.giaHan.id)
					.then(function(response){
						toastr.success('Gia hạn thành công. Vui lòng đăng xuất để cập nhật lại.', 'Thành công',{timeOut: 3000, escapeHtml: true});
						refreshData();
					})
					.catch(function(response){
						toastr.error('Lỗi khi gia hạn', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
					});	
	             
	             });
	         }
	     }, '#paypal-button');
	// $scope.update = function(){
	// 	Swal.fire({
	// 		  title: 'Bạn có chắc?',
	// 		  text: "Gia hạn thành viên gói",
	// 		  type: 'warning',
	// 		  showCancelButton: true,
	// 		  confirmButtonColor: '#3085d6',
	// 		  cancelButtonColor: '#d33',
	// 		  confirmButtonText: 'Đồng ý!',
	// 		  cancelButtonText: 'Không, thoát!',
	// 		}).then((result) => {
	// 			if (result.value) {
	// 				$http.get(URL_Home + 'members/gia-han/'+$scope.giaHan)
	// 				.then(function(response){
	// 					toastr.success('Gia hạn thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
	// 					refreshData();
	// 				})
	// 				.catch(function(response){
	// 					toastr.error('Lỗi khi gia hanj', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
	// 				});	
	// 			}
	// 		})
	// }
	
		
});