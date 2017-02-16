angular.module('JWTAuthApp')
.factory('AuthService',function($http){
	return {
		loginUser: function(user){
			return $http({
				method: 'post',
				url: 'http://localhost:8080/authenticate',
				params: {
					username:user.username,
					password:user.password
				}
			});
		}
	}
});