apt.controller('HeaderController',function($scope,$http,URL_Home){
	
	$http.get(URL_Home + 'categories')
	.then(function(response){
		$scope.listcategories = getNestedChildren(response.data, 0);
	});

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

	var spDaXem =  JSON.parse(localStorage.getItem("spDaXem"));
	if(spDaXem != null){
		$http({
			method : 'POST',
			url : URL_Home+"products/cart",
			data : spDaXem,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function(response){
			$scope.spDaXem = response.data;
		})
		.catch(function(response){
			console.log(response);
		});
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
	jQuery("#countItem").text(quantityofcart());
	
	
	jQuery(document).ready(function(){
		jQuery(".bg-loadding").css("display","none");
	});

	$scope.localShipping =  JSON.parse(localStorage.getItem("info-shipping")); 

  	$(document).ready(function () {
        $('input:radio[name=customRadio]').change(function () {
            if ($("input[name='customRadio']:checked").val() == '0') {
                console.log("vo day");
                $('#formAdress').addClass('hidden');
            }
            if ($("input[name='customRadio']:checked").val() == '1') {
                $('#formAdress').removeClass('hidden');  
            }
        });
    });

    $scope.updateShipping = function(){
    	
    	var address =  $scope.saveShipping.address+", "+ $scope.saveShipping.xa.name+", "+ $scope.saveShipping.quan.name +", "+ $scope.saveShipping.tinh.name;
    	var shipping = shipping = {address:address,phone:$scope.localShipping.phone};
    	localStorage.setItem("info-shipping", JSON.stringify(shipping));
    	$scope.localShipping = JSON.parse(localStorage.getItem("info-shipping"));
    	$scope.saveShipping = null;
		$scope.disableSelectQuan =true;
		$scope.disableSelectTinh =true;
    }
	
});