angular.module('JWTAuthApp')
.controller('NavController', function($http, $scope,$rootScope,UserService, $state, $rootScope) {
	
	//$scope.user = UserService.getUser();
	$scope.user = $rootScope.user;
	
	$scope.$on('LoginSuccessful', function() {
		$scope.user = UserService.getUser();
		//$scope.user = $rootScope.user;
	});
	
	$scope.$on('LogoutSuccessful', function() {

		$scope.user = null;
	});
	
	$scope.logout = function() {
		UserService.clearUser();
		$rootScope.$broadcast('LogoutSuccessful');
		$state.go('login');
	};
});