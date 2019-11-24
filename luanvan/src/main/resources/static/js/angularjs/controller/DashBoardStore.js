app.controller('DashBoardStore',function($scope,$http,URL_Main){
	


	$http.get(URL_Main + 'orders/tong-doanh-thu-store')
	.then(function(response){
		$scope.sumOrder = response.data;

	});

	$http.get(URL_Main + 'orders/don-hang-da-duyet-store')
	.then(function(response){
		$scope.donHangDaDuyet = response.data;

	});

	$http.get(URL_Main + 'reviews/tong-review-store')
	.then(function(response){
		$scope.tongReview = response.data;

	});

	

	$scope.months= [];
	for(var i= 1; i<=12; i++){
		$scope.months.push({id:i,name:"Tháng "+i});
	}
	$scope.years = [];
	$scope.years.push({id:2019,name:"Năm 2019"});
	$scope.years.push({id:2018,name:"Năm 2018"});
	$scope.year = $scope.years[0].id;
	var dataPoints = [0,0,0,0,0,0,0,0,0,0,0,0];
	var labelAutos = $scope.months.map(thang=> thang.name);
	
	var ctx = document.getElementById( "team-chart-custom" );
	    ctx.height = 150; 
	var myChart = new Chart( ctx , {
	        type: 'line',
	        data: {
	            labels: labelAutos,
	            type: 'line',
	            defaultFontFamily: 'Montserrat',
	            datasets: [ 
			            {
		                data: dataPoints,
		                label: "Tổng tiền",
		                backgroundColor: 'rgba(0,103,255,.15)',
		                borderColor: 'rgba(0,103,255,0.5)',
		                borderWidth: 3.5,
		                pointStyle: 'circle',
		                pointRadius: 5,
		                pointBorderColor: 'transparent',
		                pointBackgroundColor: 'rgba(0,103,255,0.5)',
		               },]
	        },
	        options: {
	            responsive: true,
	            tooltips: {
	                mode: 'index',
	                titleFontSize: 12,
	                titleFontColor: '#000',
	                bodyFontColor: '#000',
	                backgroundColor: '#fff',
	                titleFontFamily: 'Montserrat',
	                bodyFontFamily: 'Montserrat',
	                cornerRadius: 3,
	                intersect: false,
	            },
	            legend: {
	                display: false,
	                position: 'top',
	                labels: {
	                    usePointStyle: true,
	                    fontFamily: 'Montserrat',
	                },


	            },
	            scales: {
	                xAxes: [ {
	                    display: true,
	                    gridLines: {
	                        display: false,
	                        drawBorder: false
	                    },
	                    scaleLabel: {
	                        display: false,
	                        labelString: 'Tháng'
	                    }
	                        } ],
	                yAxes: [ {
	                    display: true,
	                    gridLines: {
	                        display: false,
	                        drawBorder: false
	                    },
	                    scaleLabel: {
	                        display: true,
	                        labelString: 'VNĐ'
	                    }
	                        } ]
	            },
	            title: {
	                display: false,
	            }
	        }
		});
	
	function renderChart(){
		myChart.data.labels = $scope.labels ;
		myChart.data.datasets[0].data = $scope.datas ;
		myChart.update();
	  
	}


	chartYear();

	$scope.changeYear = function(){
		chartYear();
	};
	function chartYear(){
		$scope.labels = [];
		$scope.datas = [];
		var temp = []; 
		$http.get(URL_Main + 'orders/thong-ke-nam/store?nam='+$scope.year)
		.then(function(response){
			temp = response.data;

		})
		.then(function(){
			var monthMax = 12;
			var yearCompare = new Date().getFullYear();
			if($scope.year == yearCompare){
				var monthMax = new Date().getMonth()+1;
			} 
			
			$scope.months= [];
			for(var i= 1; i<=monthMax; i++){
				$scope.months.push({id:i,name:"Tháng "+i});
			}

			for(var i = 1; i<=monthMax; i++){
				$scope.labels.push(i);
				$scope.datas.push(0);
			}
			for(var i = 0 ; i < temp.length; i++){
				let index = parseInt(temp[i].thang);
				$scope.datas[index-1]= parseInt(temp[i].tongtien);

			}
		})
		.then(function(){
			renderChart();	
		});
	}

	$scope.changeMonth = function(){
		$scope.labels = [];
		$scope.datas = [];
		var temp = []; 

		$http.get(URL_Main + 'orders/thong-ke-thang/store?nam='+$scope.year+'&thang='+$scope.month)
		.then(function(response){
			temp = response.data;

		})
		.then(function(){
			var monthCompare = new Date().getMonth()+1;

			var dayMax = new Date($scope.year,$scope.month,0).getDate();	
			
			if($scope.month == monthCompare){
				var dayMax = new Date().getDate();
			} 	

			for(var i = 1; i<=dayMax; i++){
				$scope.labels.push(i);
				$scope.datas.push(0);
			}
			for(var i = 0 ; i < temp.length; i++){
				let index = parseInt(temp[i].thang);
				$scope.datas[index-1]= parseInt(temp[i].tongtien);

			}
		})
		.then(function(){
			renderChart();	
		});
	}


	$http.get(URL_Main + 'orders/thong-ke-trang-thai-store')
	.then(function(response){
		$scope.tongStatus= response.data;
	})
	.then(function(){
		var dulieu1 = 0;
		var dulieu2= 0;
		var dulieu3 = 0;
		var dulieu4 = 0;
		var tong = 0;
		$scope.tongStatus.forEach(function(status){
			if(status.id == 1){
				dulieu1 += status.count;
			}
			else if(status.id >1 && status.id < 5){
				dulieu2 += status.count;
			}
			else if(status.id == 5){
				dulieu3 += status.count;
			}
			else if(status.id >5){
				dulieu4 += status.count;
			}
			tong +=status.count;

		})

		var dataChatStatus = [(dulieu1*100/tong).toFixed(2),(dulieu2*100/tong).toFixed(2),(dulieu3*100/tong).toFixed(2),(dulieu4*100/tong).toFixed(2)];

		var labelStatusAuto = ["Chờ xử lý", "Đang xử lý", "Đơn hàng thàng công", "Đơn hàng bị hủy"];

		var chartOrderStatus = document.getElementById( "pieChart" );
	    chartOrderStatus.height = 300;
	    
	    var chartStatus = new Chart( chartOrderStatus, {
	        type: 'pie',
	        data: {
	            datasets: [ {
	                data: dataChatStatus,
	                backgroundColor: [
	                                    "#138496",
	                                    "rgba(0, 123, 255,0.7)",
	                                    "#218838",
	                                    "#dc3545"
	                                ],
	                hoverBackgroundColor: [
	                                    "#138496",
	                                    "rgba(0, 123, 255,0.7)",
	                                    "#218838",
	                                    "#dc3545"
	                                ]

	                            } ],
	            labels: labelStatusAuto
	        },
	        options: {
	            responsive: true
	        }
	    } );
	});



	
});