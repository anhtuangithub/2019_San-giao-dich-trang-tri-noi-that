app.controller('ProductAdminController',function($scope,$http,URL_Main){
	
	$http.get(URL_Main + 'categories')
	.then(function(response){
		$scope.listcategories = getNestedChildren(response.data, 0);
	});
	
	function getNestedChildren(arr, parent) {
	    var out = [];
	    for(var i in arr) {
	        if(arr[i].parentId == parent) {
	            var categories = getNestedChildren(arr, arr[i].id);
	            if(categories.length) {
	                arr[i].categories = categories;
	            }
	            out.push(arr[i]);
	        }
	    }
	    return out;
	}
	
	$http.get(URL_Main + 'producers')
	.then(function(response){
		$scope.listproducers = response.data;
	});
	
	$http.get(URL_Main + 'materials')
	.then(function(response){
		$scope.listmaterials = response.data;
	});
	
	$http.get(URL_Main + 'origins')
	.then(function(response){
		$scope.listorigins = response.data;
	});
	
	$http.get(URL_Main + 'products')
	.then(function(response){
		$scope.listproduct = response.data;
	});
	
	$http.get(URL_Main + 'colors')
	.then(function(response){
		$scope.listcolor = response.data;
	});
	
	//List child Category
	$http.get(URL_Main + 'categories/list-category-child')
	.then(function(response){
		$scope.listChildCate = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'products')
		.then(function(response){
			$scope.listproduct = response.data;
		});
	}
	
	$scope.dataListProp = {
			frm_details_oldTr :null
		};
	function showMemberDetails(m){
		$scope.colors= m.color;
		return '<tr><td colspan="5">' +
		'<table align="center" class="table table-bordered"><tbody>'+
			'<tr><th  style="text-align:left">' +
				'Tên Sản Phẩm: </th>'+
				'<td>'+m.name+'</td>'+
			'</tr>'+
			'<tr><th  style="text-align:left">' +
				'Xuất xứ: </th>'+
				'<td >'+m.origins.name+'</td>'+
			'</tr>'+
			'<tr><th  style="text-align:left">' +
				'Nhà sản xuất: </th>'+
				'<td>'+m.producers.name+'</td>'+
			'</tr>'+
			'<tr><th  style="text-align:left">' +
				'Nguyên liệu: </th>'+
				'<td>'+m.materials.name+'</td>'+
			'</tr>'+
			'</tbody></table></td>'+
			'<td colspan="3">'+'<img style="max-width:150px;" src="/img/uploads/'+m.avatar+'"/></td></tr>';
			
	}

	$scope.frmCreateEdit_Show =function(editID){
		i= indexOfMember(editID);
		item = $scope.listproduct[i];
		tr = jQuery('#tr_'+editID);
		icon = jQuery(tr).find('.btn-detail span');
		if(jQuery(icon).prop('class') == 'fa fa-eye-slash'){
			jQuery(icon).prop('class', 'fa  fa-eye');
			jQuery(tr).next('tr').remove();
			$scope.dataListProp.frm_details_oldTr = null;
		}
		else{
			if($scope.dataListProp.frm_details_oldTr != null){
				oldTr =jQuery($scope.dataListProp.frm_details_oldTr);
				oldIcon = jQuery(oldTr).find('.btn-detail span');
				jQuery(oldIcon).prop('class', ' fa  fa-eye-slash');
				jQuery(oldTr).next('tr').remove();
			}
			jQuery(icon).prop('class', 'fa  fa-eye');
			jQuery(tr).after(showMemberDetails(item));
				$scope.dataListProp.frm_details_oldTr = tr;
		}
	}
	function indexOfMember(cusID){
		for(i= 0; i < $scope.listproduct.length; i++ ){
			if($scope.listproduct[i].id == cusID)
					return i;
		}
		return -1;
	}
	
	
	var product;
	var color;
	var unitPrice;
	var priceRoot;
	var newArr = [];
	var methodProduct ="";
	$scope.save = function(){
		refreshData();
		jQuery('#myModal').modal('hide');
	}
	

	
	$scope.modal= function(state,id){
		$scope.productid = id;
		$scope.state = state;
		
		jQuery("ul.nav-tabs li a").removeClass("active");
		jQuery(".tab-content div").removeClass("active");
		jQuery("ul.nav-tabs li:first-child a").addClass("active");
		jQuery("#home").addClass("active");
		
		
		$scope.frmTitle = "Thông tin sản phẩm ";
		$http.get(URL_Main + 'products/'+id)
		.then(function(response){
			$scope.product = response.data;
	
		});
		$http.get(URL_Main + 'products/price/'+id)
		.then(function(response){
			$scope.Prices = response.data;
				
		});
		$http.get(URL_Main + 'products/priceRoot/'+id)
		.then(function(response){
			$scope.priceRoot = response.data;
				
		});
		
		jQuery('#myModal').modal('show');
	}
	
	$scope.changeStatus = function(productid, productname){
		Swal.fire({
			  title: 'Bạn có chắc?',
			  text: "Thay đổi trạng thái của " + productname,
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Đồng ý!',
			  cancelButtonText: 'Không, thoát!',
			}).then((result) => {
				if (result.value) {
					 $http.get(URL_Main + 'products/change-status/'+productid)
					.then(function(response){
						toastr.success('Thay đổi trạng thái sản phẩm thành công', 'Thành công',{timeOut: 1000, escapeHtml: true});
						jQuery('#myModal').modal('hide');
						refreshData();
					})
					.catch(function (response){
						toastr.error('Có lỗi trong quá trình thay đổi trạng thái', 'Gặp lỗi!');
						
					});
				}
			})
		
			
	}
	
	
	
});
