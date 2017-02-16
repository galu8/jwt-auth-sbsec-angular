angular.module('JWTAuthApp')
.service('UserService',function($http,$cookies){
	var user = null;
	var userToken = "";
	
	function getClaimsFromToken(token) {
        var claims = {};
        if (typeof token !== 'undefined') {
            var encoded = token.split('.')[1];
            claims = JSON.parse(urlBase64Decode(encoded));
        }
        return claims;
    }
	
	function urlBase64Decode(str) {
		var output = str.replace('-', '+').replace('_', '/');
		switch (output.length % 4) {
			case 0:
				break;
			case 2:
				output += '==';
				break;
			case 3:
				output += '=';
				break;
			default:
				throw 'Illegal base64url string!';
		}
		return window.atob(output);
	}
	
	return {

		loadUser: function(data){
			user = data.user;
			userToken = data.token;
			$http.defaults.headers.common['Authorization'] = 'Bearer ' + userToken;
			$cookies.putObject("userCookie", {user: user, token: userToken});
		},
		getUser: function(){
			if (!user) {
				var userCookie = $cookies.getObject("userCookie");
				if(userCookie){
					$http.defaults.headers.common['Authorization'] = 'Bearer ' + userCookie.token;
					user = userCookie.user;
				}			
				
			}
			return user;
		},
		clearUser: function() {
			user= null;
			userToken = null;
			$http.defaults.headers.common['Authorization'] = undefined;
			$cookies.remove("userCookie");
		}
		
		
	}
})
.factory('UserFactory',function($http){
	return {
		addUser: function(user){
			return $http({
				method: 'post',
				url: 'http://localhost:8080/api/users',
				data: user 
			});
		},
		editUser: function(user){
			return $http({
				method: 'put',
				url: 'http://localhost:8080/api/users',
				data: user 
			});
		},
		deleteUser: function(userId){
			return $http({
				method: 'delete',
				url: 'http://localhost:8080/api/users/' + userId,
			});
		}
	}
})
;