app.controller('QAController',function($scope,$http,URL_Main){
	
	
	$http.get(URL_Main + 'questions/cau-hoi-cua-store')
	.then(function(response){
		$scope.listquestion = response.data;
	});
	
	function refreshData(){
		$http.get(URL_Main + 'questions/cau-hoi-cua-store')
		.then(function(response){
			$scope.listquestion = response.data;
		});
	}
	
	
	// Xử lí modal
	$scope.modal= function(id){
		$scope.questions = id;
		$scope.answer = {questions:{id:id},stores:{id:1}};
		$scope.frmTitle = "Chi tiết Câu hỏi";
		$scope.frmanswer = "Thêm câu trả lời mới";
		
		$http.get(URL_Main + 'questions/'+id)
		.then(function(response){
			$scope.question = response.data;
		});
		$http.get(URL_Main + 'answers/question/'+id)
		.then(function(response){
			$scope.listanswer = response.data;
		});
		
		jQuery('#modalQA').modal('show');
	}
	
	$scope.save = function(id){
		if(id != undefined){
			var method = "put";
			var data = $scope.answer.content;
			var url = URL_Main + 'answers/'+id;
		}
			
		else{
			var method = "post";
			var data = $scope.answer;
			var url = URL_Main + 'answers';
		}
			
		$http({
			method : method,
			url : url,
			data : data,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			$http.get(URL_Main + 'answers/question/'+$scope.questions)
			.then(function(response){
				$scope.listanswer = response.data;
				toastr.success('Thêm câu trả lời thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
				$scope.frmanswer = "Thêm câu trả lời mới";
				$scope.answer = {questions:{id:$scope.questions},stores:{id:1}};
			});
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình thêm câu trả lời', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	
	$scope.changeStatus = function(id){
		var url = URL_Main + 'questions/'+id;
		$http({
			method : 'PUT',
			url : url,
			headers : {'Content-type' : 'application/json'}
		})
		.then(function (response){
			toastr.success('Cập nhật thành công', 'Thành công',{timeOut: 2000, escapeHtml: true});
			refreshData();
		})
		.catch(function (response){
			toastr.error('Có lỗi trong quá trình cập nhật', 'Gặp lỗi!',{timeOut: 2000, escapeHtml: true});
			
		});
	}
	$scope.getAnswer = function(id,content){
		$scope.frmanswer = "Chỉnh sửa câu trả lời: "+ content;
		$http.get(URL_Main + 'answers/'+id)
		.then(function(response){
			$scope.answer = response.data;
		});
	}
});