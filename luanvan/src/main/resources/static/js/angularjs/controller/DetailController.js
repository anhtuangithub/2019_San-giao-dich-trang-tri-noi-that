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
			
			// imageZoom("myimage", "myresult");
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
	function imageZoom(imgID, resultID) {
		var img, lens, result, cx, cy;
		img = document.getElementById(imgID);
		console.log(img.src);
		result = document.getElementById(resultID);
		/*create lens:*/
		lens = document.createElement("DIV");
		lens.setAttribute("class", "img-zoom-lens");
		/*insert lens:*/
		img.parentElement.insertBefore(lens, img);
		/*calculate the ratio between result DIV and lens:*/
		cx = result.offsetWidth / lens.offsetWidth;
		cy = result.offsetHeight / lens.offsetHeight;
		/*set background properties for the result DIV:*/
		result.style.backgroundImage = "url('" + img.src + "')";
		result.style.backgroundSize = (img.width * cx) + "px " + (img.height * cy) + "px";
		/*execute a function when someone moves the cursor over the image, or the lens:*/
		lens.addEventListener("mousemove", moveLens);
		img.addEventListener("mousemove", moveLens);
		/*and also for touch screens:*/
		lens.addEventListener("touchmove", moveLens);
		img.addEventListener("touchmove", moveLens);
		  	function moveLens(e) {
			    var pos, x, y;
			    /*prevent any other actions that may occur when moving over the image:*/
			    e.preventDefault();
			    /*get the cursor's x and y positions:*/
			    pos = getCursorPos(e);
			    /*calculate the position of the lens:*/
			    x = pos.x - (lens.offsetWidth / 2);
			    y = pos.y - (lens.offsetHeight / 2);
			    /*prevent the lens from being positioned outside the image:*/
			    if (x > img.width - lens.offsetWidth) {x = img.width - lens.offsetWidth;}
			    if (x < 0) {x = 0;}
			    if (y > img.height - lens.offsetHeight) {y = img.height - lens.offsetHeight;}
			    if (y < 0) {y = 0;}
			    /*set the position of the lens:*/
			    lens.style.left = x + "px";
			    lens.style.top = y + "px";
			    /*display what the lens "sees":*/
			    result.style.backgroundPosition = "-" + (x * cx) + "px -" + (y * cy) + "px";
		  	}
			function getCursorPos(e) {
			    var a, x = 0, y = 0;
			    e = e || window.event;
			    /*get the x and y positions of the image:*/
			    a = img.getBoundingClientRect();
			    /*calculate the cursor's x and y coordinates, relative to the image:*/
			    x = e.pageX - a.left;
			    y = e.pageY - a.top;
			    /*consider any page scrolling:*/
			    x = x - window.pageXOffset;
			    y = y - window.pageYOffset;
			    return {x : x, y : y};
			}
		}
	// $("#mySpriteSpin").spritespin({
 //        // path to the source images.
 //        source: [
 //            "path/to/frame_001.jpg",
 //            "path/to/frame_002.jpg",
 //            "path/to/frame_003.jpg",
 //            "path/to/frame_004.jpg",
 //            "path/to/frame_005.jpg",
 //            "path/to/frame_006.jpg",
 //            "path/to/frame_007.jpg",
 //            "path/to/frame_008.jpg",
 //            "path/to/frame_009.jpg",
 //            "path/to/frame_010.jpg",
 //        ],
 //        width   : 480,  // width in pixels of the window/frame
 //        height  : 327,  // height in pixels of the window/frame
 //    });

	
});
