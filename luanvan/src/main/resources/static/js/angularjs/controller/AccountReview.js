apt.controller('AccountReview',function($scope,$http,URL_Home){
	
			
	let checkParams = new URLSearchParams(window.location.search);
	
	var numberpage =0;
	if(checkParams.has("page")){
		numberpage = checkParams.get("page");
	}
	$http.get(URL_Home + 'reviews/review-of-user?page='+numberpage)
	.then(function(response){
		$scope.listReview  = response.data;
	}).then(function(){
		

		var totalPages = $scope.listReview.totalPages;
		var currentPage = $scope.listReview.number;
		if(totalPages > 1){
			page(currentPage+1,totalPages);
		}
		
		jQuery(document).ready(function(){
			filterAndvanted();			
		});
		
	});
	
});