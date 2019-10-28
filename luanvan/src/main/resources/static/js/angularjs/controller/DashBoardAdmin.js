app.controller('DashBoardAdmin',function($scope,$http,URL_Main){
	

	$http.get(URL_Main + 'orders/tong-doanh-thu')
	.then(function(response){
		$scope.sumOrderAdmin = response.data;
	});

	$http.get(URL_Main + 'orders/don-hang-da-duyet')
	.then(function(response){
		$scope.donHangDaDuyet = response.data;
	});


	$http.get(URL_Main + 'reviews/tong-review')
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
		chartYear()
	};
	function chartYear(){
		$scope.labels = [];
		$scope.datas = [];
		var temp = []; 
		$http.get(URL_Main + 'orders/thong-ke-nam?nam='+$scope.year)
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

		$http.get(URL_Main + 'orders/thong-ke-thang?nam='+$scope.year+'&thang='+$scope.month)
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



	// var ctx = document.getElementById( "pieChart" );
 //    ctx.height = 300;
 //    var myChart = new Chart( ctx, {
 //        type: 'pie',
 //        data: {
 //            datasets: [ {
 //                data: [ 45, 25, 20, 10 ],
 //                backgroundColor: [
 //                                    "rgba(0, 123, 255,0.9)",
 //                                    "rgba(0, 123, 255,0.7)",
 //                                    "rgba(0, 123, 255,0.5)",
 //                                    "rgba(0,0,0,0.07)"
 //                                ],
 //                hoverBackgroundColor: [
 //                                    "rgba(0, 123, 255,0.9)",
 //                                    "rgba(0, 123, 255,0.7)",
 //                                    "rgba(0, 123, 255,0.5)",
 //                                    "rgba(0,0,0,0.07)"
 //                                ]

 //                            } ],
 //            labels: [
 //                            "green",
 //                            "green",
 //                            "green"
 //                        ]
 //        },
 //        options: {
 //            responsive: true
 //        }
 //    } );


	
//	var dataPoints = [];
//
//	var options =  {
//		animationEnabled: true,
//		theme: "light2",
//		title: {
//			text: "Doanh thu theo tháng"
//		},
//		axisX: {
//			valueFormatString: "MM-YYYY",
//		},
//		axisY: {
//			title: "VNĐ",
//			titleFontSize: 24,
//			includeZero: false
//		},
//		data: [{
//			type: "line",
//			markerType: "square",
//			yValueFormatString: "#,### vnđ",
//			dataPoints: dataPoints
//		}]
//	};
//	addData($scope.dashboard);
//	function addData(data){
//		for(var i = 0 ; i < data.length; i++){
//			dataPoints.push({
//				x: new Date(data[i].nam,data[i].thang-1),
//				y: parseInt(data[i].tongtien)
//			});
//		}
//		jQuery('#chartContainer').CanvasJSChart(options);
//	}

	
});