app.controller('ManageStoreController',function($scope,$http,URL_Main){
	
	
	$http.get(URL_Main + 'stores/all-store-dto')
	.then(function(response){
		$scope.listStore = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'stores/all-store-dto')
		.then(function(response){
			$scope.listStore = response.data;
		});
	}
	
	$scope.xacNhan = function(storeid,storename){
		Swal.fire({
			  title: 'Bạn có chắc?',
			  text: "Thay đổi tình trạng xét duyệt cho nhà bán hàng " + storename,
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Đồng ý!',
			  cancelButtonText: 'Không, thoát!',
			}).then((result) => {
				if (result.value) {
					$http({
						method : 'POST',
						url : "/stores/xet-duyet",
						data : storeid,
						headers : {'Content-type' : 'application/json'}
					})
					.then(function (response){
						toastr.success('Thay đổi trạng thái xét duyệt thành công', 'Thành công',{timeOut: 1000, escapeHtml: true});
						refreshData();
					})
					.catch(function (response){
						toastr.error('Có lỗi trong quá trình xét duyệt', 'Gặp lỗi!',{timeOut: 1000, escapeHtml: true});
						
					});
				}
			})
	}


});