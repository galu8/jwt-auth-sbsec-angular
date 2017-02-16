angular.module('JWTAuthApp')
		.controller(
				'LoginController',
				function($http, $scope, $state, AuthService, UserService, $rootScope) {
	
	$scope.login = function() {
		
		var user = {
			username : $scope.username,
			password : $scope.password
		}
		
		AuthService.loginUser(user)
		.then(function successCallback(response) {
			$scope.password = null;
			// checking if the token is available in the response
			if (response.data.token) {
				$scope.message = '';
				UserService.loadUser(response.data);
				$rootScope.$broadcast('LoginSuccessful');

				$state.go('home');
			}else {
				$scope.message = 'Authetication Failed !';
			}
	    }, function errorCallback(error){
	    	$scope.message = 'Authetication Failed !';
	    });
		
	};
});