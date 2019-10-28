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
	});

	$scope.update = function(){
		Swal.fire({
			  title: 'Bạn có chắc?',
			  text: "Gia hạn thành viên gói",
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Đồng ý!',
			  cancelButtonText: 'Không, thoát!',
			}).then((result) => {
				if (result.value) {
					$http.get(URL_Home + 'members/gia-han/'+$scope.giaHan)
					.then(function(response){
						toastr.success('Gia hạn thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
						refreshData();
					})
					.catch(function(response){
						toastr.error('Lỗi khi gia hanj', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
					});	
				}
			})
		}
		
});