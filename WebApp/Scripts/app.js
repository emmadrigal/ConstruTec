//Creates the "Core"
var construtecAPP = angular.module('construtecAPP', ['ngRoute', 'ui.bootstrap']);

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
		.when('/client',
		{
			controller: 'AppController',
			templateUrl: 'Partials/client.html'
		})
		.otherwise({ redirectTo: '/search' });
});

construtecAPP.controller('ArchitectController', function($scope, $uibModalInstance, projects) {

	$scope.selectedRow = null;

    $scope.addNewProject = function(){
		var newFile = {
			ID: $scope.newProjectID,
			name: $scope.newProjectName,
			address: $scope.newProjectLoc,
			clientID: $scope.newProjectClientID,
			architectID: "1"
		};
		$scope.projects = projects.push(newFile);
		$uibModalInstance.dismiss('cancel');
    };

});

construtecAPP.controller('PhaseFormController', function($scope, $uibModalInstance, phases, listPhases) {
	$scope.listPhases = listPhases;

    $scope.addNewPhase = function(){
		var newFile = {
			name: $scope.newPhaseName,
			start: $scope.newPhaseStart,
			finish: $scope.newPhaseEnd
		};
		$scope.phases = phases.push(newFile);
		$uibModalInstance.dismiss('cancel');
    };

});

construtecAPP.controller('MaterialFormController', function($scope, $uibModalInstance, listMaterials, materials) {
	$scope.listMaterials = listMaterials;

    $scope.addNewMaterial = function(){
		var newFile = {
			name: $scope.newMaterialName,
			quantity: $scope.newMaterialQuantity
		};
		$scope.materials = materials.push(newFile);
		$uibModalInstance.dismiss('cancel');
    };

});

construtecAPP.controller('AppController', function SimpleController($scope, $location, $http, $uibModal){
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

	$scope.listPhases = [{name: "cimientos"}, {name: "paredes"}, {name: "concreto"}, {name: "Techos"}];

	$scope.phases = [
		{name: 'cimientos', start: '1', finish: '2'}
	];

	$scope.listMaterials = [{name: "martillo"}, {name: "clavos"}, {name: "cemento"}, {name: "argollas"}];

	$scope.materials = [
		{name: 'martillo', quantity: '30'}
	];

	$scope.openProjectForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'ArchitectController',
            templateUrl: 'ProjectForm.html',
            resolve: {
            	projects: function() {return $scope.projects;}
			}
        });
    }

    $scope.openPhaseForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'PhaseFormController',
            templateUrl: 'PhaseForm.html',
            resolve: {
            	listPhases: function() {return $scope.listPhases;},
            	phases: function() {return $scope.phases;}
			}
        });
    }

    $scope.openMaterialForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'MaterialFormController',
            templateUrl: 'MaterialForm.html',
            resolve: {
            	listMaterials: function() {return $scope.listMaterials;},
            	materials: function() {return $scope.materials;}
			}
        });
    }

    $scope.deletePhase = function (value){
    	$scope.phases.splice(value, 1);
    }

    $scope.deleteMaterial = function (value){
    	$scope.materials.splice(value, 1);
    }

	$scope.goTo = function ( path ) {
    	$location.path( path );
    };
});