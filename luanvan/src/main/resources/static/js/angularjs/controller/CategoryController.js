app.controller('CategoryController',function($scope,$http,URL_Main){
	
	$scope.checkDeleteCategory = false;
	$scope.displayInput = false;
	$scope.categories={name:"",status:0};
	
	$http.get(URL_Main + 'categories')
	.then(function(response){
		$scope.listcategories = getNestedChildren(response.data, 0);
	});
	
	function refreshData(){
		$http.get(URL_Main + 'categories')
		.then(function(response){
			$scope.listcategories = getNestedChildren(response.data, 0);
		});
		$scope.displayInput = false;
	}
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
	$scope.chooseParentId = function(id,name){
		$scope.categories = { parentId:id , nameParentId:name,status:0} ;
		$scope.displayInput = true;
	}
	$scope.save = function(){
			
		var url = URL_Main + 'categories';
		var data = $scope.categories;
		$http({
			method : 'POST',
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật danh mục thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
			$scope.categories = {name:"",parentId:"",status:0};
			$scope.displayInput = false;
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
		});
	}
	
	$scope.edit = function(id,parentId){
		// $scope.checkDeleteCategory = false;
		// var listcategories
		// var listId = [];
		// var i = 0;
		$http.get(URL_Main + 'categories/'+id)
		.then(function(response){
			$scope.categories = response.data;
			// if(parentId != "0"){
			// 	$scope.displayInput = true;
			// 	$http.get(URL_Main + 'categories/'+parentId)
			// 	.then(function(response){
			// 		$scope.categories = {id:category.id,status:category.status,parentId:parentId, name:category.name, nameParentId: response.data.name};
			// 	});
				
			// 	$http.get(URL_Main + 'categories/parentId/'+parentId)
			// 	.then(function(response){
			// 		listcategories = response.data;
			// 		for(categories of listcategories){
			// 			$http.get(URL_Main + 'products/categories/'+categories.id)
			// 			.then(function(response){
			// 				i+=response.data;
			// 				if(i === 0){
			// 					$scope.checkDeleteCategory = true;
			// 				}
			// 			});
			// 		}
					
			// 	});
			// }
			// else{
			// 	$scope.categories = {id:category.id,name:category.name,status:category.status};
			// 	$http.get(URL_Main + 'products/categories/'+category.id)
			// 	.then(function(response){
			// 		if(response.data==0){
			// 			$http.get(URL_Main + 'categories/parentId/'+category.id)
			// 			.then(function(response){
			// 				if(response.data.length != 0){
			// 					listcategories = response.data;
			// 					for(categories of listcategories){
			// 						$http.get(URL_Main + 'products/categories/'+categories.id)
			// 						.then(function(response){
										
			// 							i+=response.data;
			// 							if(i == 1){
			// 								$scope.checkDeleteCategory = false;
			// 							}
			// 						});
			// 					}
			// 				}
			// 				$scope.checkDeleteCategory = true;
							
			// 			});
			// 		}
			// 	});
				
			// }
				
		});
	}
		
	$scope.cancel = function(){
		$scope.categories = {parentId:"" , nameParentId:"",name:""};
		$scope.checkDeleteCategory = false;
		$scope.displayInput = false;
	};
	
	// $scope.deleteCategories = function(id,name){
	// 	var isConfirmDelete = confirm('Bạn có chắc muốn xóa '+ name +' này không');
	// 	if(isConfirmDelete){
	// 		$http.delete(URL_Main+'categories/'+id)
	// 		.then(function(response){
	// 			toastr.success('Xóa danh mục thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
	// 			refreshData();
	// 			$scope.categories = {name:"",parentId:""};
	// 			$scope.checkDeleteCategory = false;
	// 		})
	// 		.catch(function(response){
	// 			toastr.error('Có lỗi trong quá trình xóa danh mục', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
	// 		});
	// 	}
	// 	else{
	// 		return false;
	// 	}
	// }
});