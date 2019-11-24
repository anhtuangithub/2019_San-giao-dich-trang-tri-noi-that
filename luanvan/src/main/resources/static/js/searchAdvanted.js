		let urlSearch = window.location.href;
		let searchParams = new URLSearchParams(window.location.search);

		function FilterAdvanced(param,paramvalue) {
			let produceridtontai2 = "&"+param+"="+searchParams.get(param);
			let produceridtontai1 = "?"+param+"="+searchParams.get(param);
			
			urlSearch = urlSearch.replace('&page='+searchParams.get('page'),'');
			
			let urlRedirect = urlSearch;
			//Nếu kiểm tra có catagoryid thì set = '';
			if(urlSearch.indexOf('&'+param) > -1){
				urlRedirect = urlSearch.replace(produceridtontai2,'');
			}
			if(urlSearch.indexOf('?'+param) > -1){
				urlRedirect = urlSearch.replace(produceridtontai1,'');
			}
			let addProducer = "?"+param+"="+paramvalue;
			if(urlSearch.indexOf('?') > -1){
				addProducer = "&"+param+"="+paramvalue;
			}

			location.replace(urlRedirect+addProducer);
		}

		function showCoDau(array, param){
			for(let i= 0 ;i < array.length ; i++){
				if(array[i].id == searchParams.get(param)){
					return array[i];
					break;
				}
			}
			
			
		}

		function filterAndvanted(map){
			
			var txt = "";
			if(searchParams.has("star")){
				txt = jQuery("<span class='ant-tag' id='ant-star'>").text("ít nhất "+ searchParams.get("star")+" sao x");
				jQuery("#filtered").append(txt);
			}

			if(searchParams.has("producer")){
				var codau = showCoDau(map.producers,"producer");
				txt = jQuery("<span class='ant-tag' id='ant-producer'>").text("Nhà SX: "+ codau.name +" x");
				jQuery("#filtered").append(txt);
			}
			if(searchParams.has("material")){
				var codau = showCoDau(map.materials,"material");
				txt = jQuery("<span class='ant-tag' id='ant-material'>").text("Vật liệu: "+ codau.name +" x");
				jQuery("#filtered").append(txt);
			}
			if(searchParams.has("origin")){
				var codau = showCoDau(map.origins,"origin");
				txt = jQuery("<span class='ant-tag' id='ant-origin'>").text("Xuất xứ: "+ codau.name+" x");
				jQuery("#filtered").append(txt);
			}

			if(searchParams.has("category")){
				var codau = showCoDau(map.categorys,"category");
				txt = jQuery("<span class='ant-tag' id='ant-category'>").text("Danh mục: "+ codau.name +" x");
				jQuery("#filtered").append(txt);
			}

			if(searchParams.has("filter")){
				
				if(searchParams.get("filter") == "hang-moi"){
					txt = jQuery("<span class='ant-tag' id='ant-filter'>").text("Sắp xếp theo: hàng mới x");
					jQuery("#filtered").append(txt);
					jQuery("#filter-new").addClass("active");
				}
				if(searchParams.get("filter") == "gia-thap"){
					txt = jQuery("<span class='ant-tag' id='ant-filter'>").text("Sắp xếp theo: giá thấp x");
					jQuery("#filtered").append(txt);
					jQuery("#filter-priceMin").addClass("active");

				}
				if(searchParams.get("filter") == "gia-cao"){
					txt = jQuery("<span class='ant-tag' id='ant-filter'>").text("Sắp xếp theo: giá cao x");
					jQuery("#filtered").append(txt);
					jQuery("#filter-priceMax").addClass("active");
				}
				if(searchParams.get("filter") == "ban-chay"){
					txt = jQuery("<span class='ant-tag' id='ant-filter'>").text("Sắp xếp theo: bán chạy x");
					jQuery("#filtered").append(txt);
					jQuery("#filter-bestsealler").addClass("active");
				}
			}
			
			if(searchParams.has("category")||searchParams.has("filter")||searchParams.has("origin") || searchParams.has("material")|| searchParams.has("producer") || searchParams.has("star")){
				txt = jQuery("<span style='color:#f68b35; font-size:12px;'id='ant-deleteall' >Xóa hết</span>");
				jQuery("#filtered").append(txt);
			}

			jQuery('#ant-producer').click(function(){
				DeleteParrametered("producer",searchParams.get("producer"));
			});
			jQuery('#ant-star').click(function(){
				DeleteParrametered("star",searchParams.get("star"));
			});
			jQuery('#ant-material').click(function(){
				DeleteParrametered("material",searchParams.get("material"));
			});
			jQuery('#ant-origin').click(function(){
				DeleteParrametered("origin",searchParams.get("origin"));
			});
			
			jQuery('#ant-category').click(function(){
				DeleteParrametered("category",searchParams.get("category"));
			});

			jQuery('#ant-filter').click(function(){
				DeleteParrametered("filter",searchParams.get("filter"));
			});

			jQuery('#ant-deleteall').click(function(){
				DeleteAllParam();
			});
		}
		function DeleteAllParam(){
			var arrPa =  ["star","producer","origin","material","filter","category","page"];
			let urlRedirect = urlSearch;
			arrPa.forEach(function(pa){
				let produceridtontai = "&"+pa+"="+searchParams.get(pa);
				let produceridtontai2 = "?"+pa+"="+searchParams.get(pa);
				if(urlSearch.indexOf('&'+pa) > -1){
					urlRedirect = urlRedirect.replace(produceridtontai,'');
				}
				else if(urlSearch.indexOf('?'+pa) > -1){
					urlRedirect = urlRedirect.replace(produceridtontai2,'');
				}	
				
			});
			location.replace(urlRedirect);
		}
		function DeleteParrametered(param,paramvalue){
			let produceridtontai = "&"+param+"="+searchParams.get(param);
			let produceridtontai2 = "?"+param+"="+searchParams.get(param);
			let urlRedirect = urlSearch;
			if(urlSearch.indexOf('&'+param) > -1){
				urlRedirect = urlRedirect.replace(produceridtontai,'');
			}
			else if(urlSearch.indexOf('?'+param) > -1){
				urlRedirect = urlRedirect.replace(produceridtontai2,'');
			}
			location.replace(urlRedirect);

		}


		function urlPage(numberPage) {
			let produceridtontai2 = "&page="+searchParams.get("page");
			let produceridtontai1 = "?page="+searchParams.get("page");
			let urlRedirect = urlSearch;

			if(urlSearch.indexOf('?page') > -1){
				urlRedirect = urlRedirect.replace(produceridtontai1,'');
			}
			if(urlSearch.indexOf('&page') > -1){
				urlRedirect = urlRedirect.replace(produceridtontai2,'');
			}
			let addProducer = "?page="+numberPage;
			if(urlRedirect.indexOf('?') > -1){
				addProducer = "&page="+numberPage;
			}
			return urlRedirect+addProducer;
		}
		function page(currentPage,totalPages){
			var i =currentPage;
			var txt = "";
			var url = "";
			urlPrevious = urlPage(currentPage-1);
			urlNext = urlPage(i+1);
			urlFirst = urlPage(1);
			urlLast = urlPage(totalPages);
			var solanlap = i+4;

			//Trờ về trang đầu
			if(i > 1){
				pageFirst = jQuery("<li class='page-item'><a class='page-link' href='"+urlFirst+"'> Trang đầu </a></li>");
				page = jQuery("<li class='page-item'><a class='page-link' href='"+urlPrevious+"'> << </a></li>");
				jQuery("#pageable-custom").append(pageFirst);
				jQuery("#pageable-custom").append(page);
				
			}
			//Trang ở giữa
			if(solanlap <= totalPages){
				for(i ; i <= totalPages; i++ ){
					if(i >solanlap){
						break;
					}
					url = urlPage(i);
					page = jQuery("<li class='page-item' id='page_"+i+"'><a class='page-link' href='"+url+"' >"+i+"</a></li>");
					jQuery("#pageable-custom").append(page);	
				}
			}

			// Khi trang hiện tại lớn hơn 4 lần
			else{
				var minus = totalPages -4;
				if(minus <= 0){
					minus=1;
				}
				for(minus ; minus <= totalPages; minus++ ){
					
					url = urlPage(minus);
					page = jQuery("<li class='page-item' id='page_"+minus+"'><a class='page-link' href='"+url+"' >"+minus+"</a></li>");
					jQuery("#pageable-custom").append(page);	
				}
			}
			
			// trang kế tiếp
			if(currentPage+1 <= totalPages){
				pageLast = jQuery("<li class='page-item'><a class='page-link' href='"+urlLast+"'> Trang cuối </a></li>");
				page = jQuery("<li class='page-item'><a class='page-link' href='"+urlNext+"'> >> </a></li>");
				jQuery("#pageable-custom").append(page);
				jQuery("#pageable-custom").append(pageLast);
			}

			jQuery("#page_"+currentPage).addClass("active");

		}

		$('.filter-container').find('.over-flow').css({height:'100px', overflow:'hidden'});

		$('.show-more').on('click', function() {
			
			$(this).parent().find('.over-flow').animate({height:'100%'});

			$(this).css("display","none");
			$(this).parent().find('.close-show-more-custom').css("display","block");
		});

		$('.close-show-more-custom').click(function(){
			$(this).parent().find('.over-flow').animate({height:'100px'});

			$(this).css("display","none");
			$(this).parent().find('.show-more').css("display","block");
		})