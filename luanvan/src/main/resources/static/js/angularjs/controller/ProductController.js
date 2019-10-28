app.controller('ProductController',function($scope,$http,URL_Main){
	
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
	
	$http.get(URL_Main + 'products/store')
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
		$http.get(URL_Main + 'products/store')
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
	
	document.getElementById("avatar").onchange = function () {
	    var reader = new FileReader();

	    reader.onload = function (e) {
	        document.getElementById("image").src = e.target.result;
	    };
	    reader.readAsDataURL(this.files[0]);
	    return reader;
	};
	// Choose one category for Product when add new product
	$scope.chooseCategory = function(id,name){
		$scope.product = {
				categorys:{id:id,name:name}
		};
	}
	
	$scope.changePrice =function() {
			$scope.priceRoot.id = null;
	};
	
	var product;
	var color;
	var unitPrice;
	var priceRoot;
	var newArr = [];
	var methodProduct ="";
	$scope.save = function(){
		if($scope.state == "add"){
			methodProduct = "post";
		}
		if($scope.state == "edit") methodProduct ="put";
		
		product = $scope.product;
		color = $scope.colors;
		unitPrice = $scope.unitPrice;
		priceRoot = $scope.priceRoot;
		if(color != null) color.map(value => newArr.push({id:value}));
		jQuery('#myModal').modal('hide');
	}
	
	
	jQuery("#frmPro").on("submit", function(event){
		
        event.preventDefault();
        
        jQuery('#description').each(function () {
           for(var description in CKEDITOR.instances)
                CKEDITOR.instances[description].updateElement();
        });  
        var formData = new FormData(document.getElementById('frmPro'));
        var dataProduct = {
        		product:product,
        		colors:newArr
        }

        formData.append("product",JSON.stringify(dataProduct));
        if(unitPrice != undefined){
        	formData.append("unitPrice",JSON.stringify(unitPrice));
        }
        formData.append("priceRoot",JSON.stringify(priceRoot));
        
        if(jQuery("#avatar").val() != "" || methodProduct == "put"){
        	 jQuery.ajax({
                url: URL_Main+"products/uploadFile",
                method: methodProduct,
                data:formData,
                contentType: false,
                cache: false,
                enctype: 'multipart/form-data',
                processData: false,
                success: function(response)
                {
                      
	    //      		Swal.fire({
					//   	position: 'center',
					//  	type: 'success',
					//   	title: 'Thao tác thành công',
					//   	showConfirmButton: false,
					//   	timer: 1500
					// });    
					toastr.success('Cập nhật sản phẩm thành công', 'Thành công');
			        refreshData();
                  	//Xóa đường dẫn đã thêm vào input
                  	jQuery("#avatar").val('').clone(true);
                      
                },
                error: function(response)
                {
                    toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!');
                }

          });
        }
        else
        {
         $scope.warning=true;
        }

     
    });
	
	$scope.modal= function(state,id){
		$scope.state = state;
		$scope.showInputPrice = false;
		switch(state){
			case "add":
				jQuery("ul.nav-tabs li a").removeClass("active");
				jQuery(".tab-content div").removeClass("active");
				jQuery("ul.nav-tabs li:first-child a").addClass("active");
				jQuery("#home").addClass("active");
				$scope.frmTitle = "Thêm Sản Phẩm mới";
				$scope.product = null;
				$scope.unitPrice = null;
				$scope.priceRoot = null;
				
				break;

			case "edit":
				
				jQuery("ul.nav-tabs li a").removeClass("active");
				jQuery(".tab-content div").removeClass("active");
				jQuery("ul.nav-tabs li:first-child a").addClass("active");
				jQuery("#home").addClass("active");
				
				
				$scope.frmTitle = "Sửa thông tin sản phẩm ";
				$scope.unitPrice = null;
				$http.get(URL_Main + 'products/'+id)
				.then(function(response){
					$scope.product = response.data;
//					jQuery("#colors").selectpicker();
//					var colorpicker = [];
//					for(i = 0 ; i < $scope.product.colors.length; i++ ){
//						colorpicker.push($scope.product.colors[i].id);
//						
//					}
//					jQuery('.selectpicker').selectpicker('refresh');
//					jQuery("#colors").selectpicker('val',colorpicker);
			
				});
				$http.get(URL_Main + 'products/price/'+id)
				.then(function(response){
					$scope.Prices = response.data;
						
					});
				$http.get(URL_Main + 'products/priceRoot/'+id)
				.then(function(response){
					$scope.priceRoot = response.data;
						
					});
				break;
		}
		jQuery('#myModal').modal('show');
	}
	
	$scope.themDongia = function(){
		$scope.showInputPrice = true;
	}
	// Xử lí modal Img
	$scope.modalImg= function(productid){
		
		$scope.productId = productid;

		$http.get(URL_Main + 'products/hinh-cua-san-pham/'+$scope.productId)
		.then(function(response){
			$scope.listImage = response.data;
		});

		$scope.productid = productid;
		$scope.frmTitle = "Cập nhật hình ảnh ";
		jQuery('#modalImg').modal('show');
	}
	
	
	jQuery("#frmImg").on("submit", function(event){
		var formData = new FormData(this);
        formData.append("product",$scope.productid);
        if(jQuery("#imgs").val() != ""){
        	 jQuery.ajax({
                url: URL_Main+"products/mutiple-image",
                method: "POST",
                data:formData,
                contentType: false,
                cache: false,
                enctype: 'multipart/form-data',
                processData: false,
                success: function(response)
                {
                	toastr.success('Cập nhật hình ảnh thành công', 'Thành công',{timeOut: 3000, escapeHtml: true});
                	jQuery("#imgs").val('').clone(true);
                	 $http.get(URL_Main + 'products/hinh-cua-san-pham/'+$scope.productId)
					.then(function(response){
						$scope.listImage = response.data;
					});
                      
                },
                error: function(response)
                {
                    toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 3000, escapeHtml: true});
                }

          });
        }
        else
        {
         $scope.warning=true;
        }
    });

	$scope.updateSTT = function(){
		var statuses = [];
		jQuery('#frmEditImg input[name="stt[]"]').each(function() {
		  	statuses.push(jQuery(this).val());
		});
		for(var i = 0; i < $scope.listImage.length ; i++){
			$scope.listImage[i].status = statuses[i];
		}
		var url = URL_Main + 'products/update-stt-image/'+$scope.productId;
		var data = $scope.listImage;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thứ tự hình ảnh thành công', 'Thành công',{timeOut: 3000, escapeHtml: true});
			$http.get(URL_Main + 'products/hinh-cua-san-pham/'+$scope.productId)
			.then(function(response){
				$scope.listImage = response.data;
			});
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 3000, escapeHtml: true});
			
		});
	}

    //Modal ->form xóa hình ảnh
	jQuery("#frmEditImg").on("submit", function(event){

  		var data = [];
		jQuery('#frmEditImg input[name="deleteImg[]"]:checked').each(function() {
		  data.push(jQuery(this).val());
		});
		console.log(data);
		
	     
        jQuery.ajax({
          	headers: {'X-CSRF-TOKEN': jQuery('meta[name="csrf-token"]').attr('content'),'Content-type' : 'application/json'},
            url: URL_Main+"products/image/deleteSelect/"+$scope.productId,
            method: "POST",
            data: data,
            dataType: 'JSON',
            success: function(response)
            {
    //             $http.get(URL_Main + 'products/hinh-cua-san-pham/'+$scope.productId)
				// .then(function(response){
				// 	$scope.listImage = response.data;
				// });
                
            },
            error: function(response)
            {
                console.log(response);
            }

        });
 
	});

	$scope.filterChild = function(){
		if($scope.childCate == -1){
			$scope.filterReverse ="";	
		}
		else
			$scope.filterReverse = $scope.childCate ;
	}

	$scope.deleteProduct = function(productid, productname){
		Swal.fire({
			  title: 'Bạn có chắc?',
			  text: "Xóa sản phẩm này " + productname,
			  type: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Đồng ý!',
			  cancelButtonText: 'Không, thoát!',
			}).then((result) => {
				if (result.value) {
					 $http.delete(URL_Main + 'products/'+productid)
					.then(function(response){
						toastr.success('Xóa sản phẩm thành công', 'Thành công',{timeOut: 1000, escapeHtml: true});
						jQuery('#myModal').modal('hide');
						refreshData();
					})
					.catch(function (response){
						toastr.error('Có lỗi trong quá trình xóa sản phẩm', 'Gặp lỗi!');
						
					});
				}
			})
		
			
	}
});
