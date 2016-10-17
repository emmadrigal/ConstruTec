//Creates the "Core"
var construtecAPP = angular.module('construtecAPP', ['ngRoute', 'ui.bootstrap']);

construtecAPP.config(function ($routeProvider) {
	$routeProvider
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
		.when('/phase',
		{
			controller: 'AppController',
			templateUrl: 'Partials/phase.html'
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

construtecAPP.controller('PhaseFormController', function($filter, $scope, $uibModalInstance, phases, listPhases) {
	$scope.listPhases = listPhases;

    $scope.addNewPhase = function(){

		var newFile = {
			name: $scope.newPhaseName,
			start: $filter('date')($scope.startDate, "dd/MM/yyyy"),
			finish: $filter('date')($scope.finishDate, "dd/MM/yyyy")
		};
		$scope.phases = phases.push(newFile);
		$uibModalInstance.dismiss('cancel');

    };
});

construtecAPP.controller('NewPhaseFormController', function($scope, $uibModalInstance, listPhases) {
	$scope.listPhases = listPhases;

    $scope.addNewPhase = function(){

		var newFile = {
			name: $scope.newPhaseName,
			description: $scope.newPhaseDescription
		};
		console.log($scope.newPhaseName);
		$scope.listPhases = listPhases.push(newFile);
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

construtecAPP.controller('LoginFormController', function($scope, $location, $uibModalInstance) {

	$scope.goTo = function ( path ) {
    	$location.path( path );
    };

	$scope.close = function(){
		$uibModalInstance.dismiss('cancel');
		$scope.goTo('/architect');
	}
});

construtecAPP.controller('AppController',  function SimpleController($scope, $location, $http, $uibModal){
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

	$scope.listPhases = [{name: "Trabajo Preliminar", description: "A"}, {name: "Cimientos", description: "A"}, {name: "Paredes", description: "A"}, {name: "Concreto Reforzado", description: "A"}, {name: "Techos", description: "A"}, {name: "Cielos", description: "A"}, {name: "Repello", description: "A"}, {name: "Entrepisos", description: "A"}, {name: "Pisos", description: "A"}, {name: "Enchapes", description: "A"}, {name: "Instalación Pluvial", description: "A"}, {name: "Instalación Sanitaria", description: "A"}, {name: "Instalación Eléctrica", description: "A"} , {name: "Puertas", description: "A"}, {name: "Cerrajeria", description: "A"}, {name: "Ventanas", description: "A"}, {name: "Closets", description: "A"}, {name: "Mueble de Pintura", description: "A"}, {name: "Escaleras", description: "A"}];

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

    $scope.openLoginForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'LoginFormController',
            templateUrl: 'LoginForm.html'
        });
    }

    $scope.openRegisterForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'AppController',
            templateUrl: 'RegisterForm.html'
        });
    }

    $scope.openNewPhaseForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'NewPhaseFormController',
            templateUrl: 'NewPhaseForm.html',
            resolve: {
            	listPhases: function() {return $scope.listPhases;}
            }
        });
    }

    $scope.deletePhase = function (value){
    	$scope.phases.splice(value, 1);
    }

    $scope.deletePhaseList = function (value){
    	$scope.listPhases.splice(value, 1);
    }

    $scope.deleteMaterial = function (value){
    	$scope.materials.splice(value, 1);
    }

	$scope.goTo = function ( path ) {
    	$location.path( path );
    };
});