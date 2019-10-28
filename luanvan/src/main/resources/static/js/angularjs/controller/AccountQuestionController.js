apt.controller('AccountQuestionController',function($scope,$http,URL_Home){
	
			
	let checkParams = new URLSearchParams(window.location.search);
	
	var numberpage =0;
	if(checkParams.has("page")){
		numberpage = checkParams.get("page");
	}
	$http.get(URL_Home + 'questions/question-of-user?page='+numberpage)
	.then(function(response){
		$scope.listQuestion  = response.data;
	}).then(function(){
		
		var totalPages = $scope.listQuestion.totalPages;
		var currentPage = $scope.listQuestion.number;
		if(totalPages > 1){
			page(currentPage+1,totalPages);
		}
		
		jQuery(document).ready(function(){
			filterAndvanted();			
		});
		
	});
	
});