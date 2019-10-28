var  app= angular.module('my-app',["ngCkeditor","datatables"]).constant('URL_Main','http://localhost:8080/');

app.directive('ckEditor', function () {
	  return {
	    require: '?ngModel',
	    link: function (scope, elm, attr, ngModel) {
	      var ck = CKEDITOR.replace(elm[0]);
	      if (!ngModel) return;
	      ck.on('instanceReady', function () {
	        ck.setData(ngModel.$viewValue);
	      });
	      function updateModel() {
	        scope.$apply(function () {
	          ngModel.$setViewValue(ck.getData());
	        });
	      }
	      ck.on('change', updateModel);
	      ck.on('key', updateModel);
	      ck.on('dataReady', updateModel);

	      ngModel.$render = function (value) {
	        ck.setData(ngModel.$viewValue);
	      };
	    }
	};
});
app.directive("rateYo", function() {
return {
    restrict: "E",
    scope: {
        rating: "="
    },
    template: "<div id='rateYo'></div>",
    link: function( scope, ele, attrs ) {
        jQuery(ele).rateYo({
            rating: scope.rating,
            starWidth: "20px",      
            ratedFill: "#ffce59",
            readOnly: true          
        });
    }
  };
});
app.directive("rateYoFill", function() {
	return {
	    restrict: "E",
	    scope: {
	        rating: "="
	    },
	    template: "<div id='rateYo2'></div>",
	    link: function( scope, ele, attrs ) {
	        jQuery(ele).rateYo({
	            rating: scope.rating,
	            starWidth: "20px",      
	            ratedFill: "#ffce59",
	            fullStar: true
	        });
	    }
	 };
});


var  apt= angular.module('my-apt',['ngSanitize']).constant('URL_Home','http://localhost:8080/');
apt.directive("rateYo", function() {
	return {
	    restrict: "E",
	    scope: {
	        rating: "="
	    },
	    template: "<span id='rate'></span>",
	    link: function( scope, ele, attrs ) {
	        $(ele).rateYo({
	            rating: scope.rating,
	            starWidth: "20px",      
	            ratedFill: "#ffce59",
	            readOnly: true          
	        });
	    }
	  };
});

apt.directive("rateYoProduct", function() {
	return {
	    restrict: "E",
	    scope: {
	        rating: "="
	    },
	    template: "<span id='ratepro'></span>",
	    link: function( scope, ele, attrs ) {
	        $(ele).rateYo({
	            rating: scope.rating,
	            starWidth: "15px",      
	            ratedFill: "#ffce59",
	            readOnly: true          
	        });
	    }
	  };
});