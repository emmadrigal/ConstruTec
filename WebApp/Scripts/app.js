//Creates the "Core"
var construtecAPP = angular.module('construtecAPP', ['ngRoute']);

construtecAPP.config(function ($routeProvider) {
	$routeProvider
		.when('/login',
		{
			controller: 'AppController',
			templateUrl: 'Partials/login.html'
		})
		.when('/register',
		{
			controller: 'AppController',
			templateUrl: 'Partials/register.html'
		})
		.when('/search',
		{
			controller: 'AppController',
			templateUrl: 'Partials/search.html'
		})
		.when('/architect',
		{
			controller: 'AppController',
			templateUrl: 'Partials/architect.html'
		})
		.otherwise({ redirectTo: '/search' });
});

construtecAPP.controller('AppController', function SimpleController($scope, $location, $http){
	$scope.customers = [
		{ ID: 187199, name: 'Carlos',lastName: 'Quirós', address: 'Cartago', phoneNumber: 88888888, birthday: '9-10-2016'},
		{ ID: 187197, name: 'Andrea',lastName: 'Quirós', address: 'Cartago', phoneNumber: 88888888, birthday: '6-10-2016'},
		{ ID: 187196, name: 'Pedro',lastName: 'Quirós', address: 'Cartago', phoneNumber: 88888888, birthday: '8-10-2016'}
	];

	$scope.projects = [
		{ ID: 187199, name: 'Carlos', address: 'Cartago', clientID: '123', architectID: '126'},
		{ ID: 187197, name: 'Andrea', address: 'Cartago', clientID: '122', architectID: '129'},
		{ ID: 187196, name: 'Pedro', address: 'Cartago', clientID: '121', architectID: '127'}
	];

	$scope.goTo = function ( path ) {
    	$location.path( path );
    };

    $scope.addNewProject = function(){
    	console.log("HOLA");
    	var newFile = {
			ID: $scope.newProjectID,
			name: $scope.newProjectName,
			address: $scope.newProjectLoc,
			clientID: $scope.newProjectClientID,
			architectID: "1"
		};
		$scope.customers.push(newFile);
    }
});