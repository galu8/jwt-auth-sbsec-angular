angular.module('JWTAuthApp')
.controller('RegisterController', function($http, $scope) {
	
	$scope.submit = function() {
		$http.post('register', $scope.appUser)
		.success(function(res) {
			$scope.appUser = null;
			$scope.confirmPassword = null;
			$scope.register.$setPristine();
			$scope.message = "Registration successfull !";
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	
});