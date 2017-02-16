angular.module('JWTAuthApp')
.controller('HomeController', function($http, $scope,$rootScope, UserService) {

	//$scope.user = UserService.getUser();
	$scope.user = $rootScope.user;
	
});