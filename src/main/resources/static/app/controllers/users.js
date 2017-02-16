angular.module('JWTAuthApp')
.controller('UsersController', function($http, $scope,UserFactory) {
	
	var edit = false;
	$scope.buttonText = 'Create';
	
	var init = function() {
		$http.get('api/users').success(function(res) {
			$scope.users = res;
			
			$scope.userForm.$setPristine();
			$scope.message='';
			$scope.userData = null;
			$scope.buttonText = 'Create';
			
		}).error(function(error) {
			$scope.message = error.message;
		});
	};
	
	$scope.initEdit = function(userData) {
		edit = true;
		$scope.userData = userData;
		$scope.message='';
		$scope.buttonText = 'Update';
	};
	
	$scope.initAddUser = function() {
		edit = false;
		$scope.userData = null;
		$scope.userForm.$setPristine();
		$scope.message='';
		$scope.buttonText = 'Create';
	};
	
	$scope.deleteUser = function(userData) {
		UserFactory.deleteUser(userData.userId)
		.then(function successCallback(response) {
			$scope.deleteMessage ="Success!";
			init();
	  	}, function errorCallback(error){
	  		$scope.deleteMessage = error.message;
	    });
	};
	
	var editUser = function(){
		UserFactory.editUser($scope.userData)
		.then(function successCallback(response) {
			$scope.userData = null;
			$scope.confirmPassword = null;
			$scope.userForm.$setPristine();
			$scope.message = "User Updated!";
			init();
	  	}, function errorCallback(error){
	    	$scope.message = error.message;
	    });
	}

	
	var addUser = function(){
		UserFactory.addUser($scope.userData)
		.then(function successCallback(response) {
			$scope.userData = null;
			$scope.confirmPassword = null;
			$scope.userForm.$setPristine();
			$scope.message = "User Created";
			init();
	  	}, function errorCallback(error){
	    	$scope.message = error.message;
	    });
		
	}
	
	$scope.submit = function() {
		if(edit){
			editUser();
		}else{
			addUser();	
		}
	};
	
	//Calling the init method the first time we access to the controller
	init();
 
 
});