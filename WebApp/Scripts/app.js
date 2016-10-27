//Creates the "Core"
var construtecAPP = angular.module('construtecAPP', ['ngRoute', 'ui.bootstrap', 'ngStorage']);

construtecAPP.config(function ($routeProvider) {
	$routeProvider
		.when('/login',
		{
			controller: 'AppController',
			templateUrl: 'Partials/login.html'
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
		.when('/search',
		{
			controller: 'AppController',
			templateUrl: 'Partials/search.html'
		})
		.when('/admin',
		{
			controller: 'AppController',
			templateUrl: 'Partials/admin.html'
		})
		.otherwise({ redirectTo: '/login' });
});

construtecAPP.controller('ArchitectController', function($http, $scope, $uibModalInstance, projects, id) {

    $scope.addNewProject = function(){
		var newFile = {
			Id_Proyect: $scope.newProjectID,
			Name: $scope.newProjectName,
			Location: $scope.newProjectLoc,
			Id_Client: $scope.newProjectClientID,
			Id_Enginner: id
		};

		$scope.newData(JSON.stringify(newFile));
		$uibModalInstance.dismiss('cancel');
    };

    $scope.newData = function(data) {
		$http.post('http://localhost:50484/Project', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};
});

construtecAPP.controller('PhaseFormController', function($http, $filter, $scope, $uibModalInstance, info, projectId) {
	$scope.info = info;
    $scope.addNewPhase = function(){

    	var  idStage = $scope.obtainStageId();
		var newFile = {
			Divided_Id: 0,
			End_Date: $filter('date')($scope.finishDate, "yyyy-MM-dd"),
			Id_Project: projectId,
			Stage_Id: idStage,
			Start_Date: $filter('date')($scope.startDate, "yyyy-MM-dd"),
			Status: false
		};
		$scope.newData(JSON.stringify(newFile));
		$uibModalInstance.dismiss('cancel');
    };

    $scope.newData = function(data) {
		$http.post('http://localhost:50484/Divided_in', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};

	$scope.obtainStageId = function(){
		var l = $scope.info.length;
		for(var i = 0; i < l; i++)
			{
				if($scope.info[i].Name == $scope.newPhaseName)
				{
			    	return $scope.info[i].Stage_Id;
			  	}
			}
	}
});

construtecAPP.controller('MaterialFormController', function($http, $scope, $uibModalInstance, info, dividedId) {
	$scope.info = info;

    $scope.addNewMaterial = function(){
    	var idMaterial = $scope.obtainMaterialId();
		var newFile = {
			Posseses_Id: 0,
			Id_Material: idMaterial,
			Divided_Id: dividedId,
			Quantity: $scope.newMaterialQuantity
		};
		$scope.newData(JSON.stringify(newFile));
		$uibModalInstance.dismiss('cancel');
    };

    $scope.newData = function(data) {
		$http.post('http://localhost:50484/Posseses', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};

    $scope.obtainMaterialId = function(){
		var l = $scope.info.length;
		for(var i = 0; i < l; i++)
			{
				if($scope.info[i].Name == $scope.newMaterialName)
				{
			    	return $scope.info[i].Id_Material;
			  	}
			}
	};
});

construtecAPP.controller('NewPhaseFormController', function($scope, $http, $uibModalInstance) {

    $scope.addNewPhase = function(){

		var newFile = {
			Name: $scope.newPhaseName,
			Description: $scope.newPhaseDescription,
			Stage_Id: 0
		};
		$scope.newData(JSON.stringify(newFile));
		$uibModalInstance.dismiss('cancel');
    };

    $scope.newData = function(data) {
		$http.post('http://localhost:50484/Stage', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};
});

construtecAPP.controller('NewCommentFormController', function($http, $scope, $uibModalInstance, id) {

	$scope.addNewComment = function(){
		var newFile = {
			Comment_Id: 0,
			Id_Project: id,
			Comentary: $scope.newComment
		};

		$scope.newData(JSON.stringify(newFile));
		console.log(id);
		$uibModalInstance.dismiss('cancel');
	}

	$scope.newData = function(data) {
		$http.post('http://localhost:50484/Commentary', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};
});

construtecAPP.controller('RegisterFormController', function($http, $scope, $uibModalInstance, role) {

	$scope.addNewUser = function(){
		var newFile = {
			Id_Number:  $scope.newClientID,
			Code: $scope.newClientCode,
			Name: $scope.newClientName,
			Phone_Number: $scope.newClientPhoneNumber,
			Role_usuario: role
		};

		$scope.newData(JSON.stringify(newFile));
		$uibModalInstance.dismiss('cancel');
	}

	$scope.newData = function(data) {
		$http.post('http://localhost:50484/Usuario', data).then(
			function(response) {
	   			console.log(response);
    		}, function(error) {
        		console.log(error);
    		});
	};
});

construtecAPP.controller('AppController', function SimpleController($localStorage, $rootScope, $scope, $location, $http, $uibModal, $timeout){

	$scope.makeOrder = function(id){
		$scope.getContent('payDivided/' + id);
		console.log(id);
	}
	$scope.updateMaterials = function(){
		$scope.getContent('populateMaterials')
	}

	$scope.getProjects = function(type){
		$http.get('http://localhost:50484/Project/' + type + $localStorage.id).then(
			function(response){
				$scope.projects = response.data;
			},
			function(response){
				console.log(error);
			});
	}

	$scope.getProjectsByDay = function(){
		$http.get('http://localhost:50484/Project/Proximos_dias').then(
			function(response){
				$scope.projects = response.data;
			},
			function(response){
				console.log(error);
			});
	}

	$scope.getComments = function(id){
		$http.get('http://localhost:50484/Commentary/Proyecto/'+ id).then(
			function(response){
				$scope.info = response.data;
				console.log($scope.info);
			},
			function(response){
				console.log(error);
			});
	}

	$scope.loadProjectPhases = function(id){
		$http.get('http://localhost:50484/Divided_in/Project/' + id).then(
			function(response){
				$scope.phases = response.data;
			},
			function(response){
				console.log(error);
			});
	}

	$scope.loadPhaseMaterials = function(id){
		$http.get('http://localhost:50484/Posseses/Divided_in/' + id).then(
			function(response){
				$scope.materials = response.data;
			},
			function(response){
				console.log(error);
			});
	}
	//Obtains the content for any view
	//Uses the http get method to retreive information from the DB through the RESTful API
	$scope.getContent = function(url){
		$http.get('http://localhost:50484/' + url).then(
			function(response){
				$scope.info = response.data;
			},
			function(response){
				console.log(error);
			});
	};

	$scope.validate = function(){
		$scope.getContent('Usuario/'+ $scope.loginID);
		$timeout(function() {
			$localStorage.id = $scope.info.Id_Number;
			if($scope.info.Id_Number != 0 && $scope.info.Name != null){
				if($scope.info.Role_usuario == 0){
					$timeout(function() {$scope.goTo('/client');}, 100);
				}
				else if($scope.info.Role_usuario == 1){
					$timeout(function() {$scope.goTo('/architect');}, 100);
				}
				else{
					$timeout(function() {$scope.goTo('/admin');}, 100);
				}
			}
			else{console.log("no valido")}
		}, 500);
	}

	$scope.openProjectForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'ArchitectController',
            templateUrl: 'ProjectForm.html',
            resolve: {
            	projects: function() {return $scope.projects;},
            	id: function() {return $localStorage.id;}
			}
        });
        $timeout(function() {$scope.getProjects('Ingeniero/' + $localStorage.id); }, 15000);
    }

    $scope.openPhaseForm = function (projectId) {
    	$scope.getContent('getAllStage');
    	$scope.modalOpen = false;
    	$timeout(function() {
    		var modalInstance = $uibModal.open({
				controller: 'PhaseFormController',
	            templateUrl: 'PhaseForm.html',
	            resolve: {
	            	info: function() {return $scope.info;},
	            	projectId: function() {return projectId;}
				}
	        });
    	}, 100);
    	$timeout(function() {$scope.loadProjectPhases(projectId) }, 10000);
    }

    $scope.openMaterialForm = function (dividedId) {
    	$scope.getContent('getAllMaterial');
    	$timeout(function() {
    		var modalInstance = $uibModal.open({
				controller: 'MaterialFormController',
	            templateUrl: 'MaterialForm.html',
	            resolve: {
	            	info: function() {return $scope.info;},
	            	dividedId: function() {return dividedId;}
				}
        	});
    	}, 100);

    	$timeout(function() {$scope.loadPhaseMaterials(dividedId); }, 10000);
    }

    $scope.openRegisterForm = function (role) {
        var modalInstance = $uibModal.open({
			controller: 'RegisterFormController',
            templateUrl: 'RegisterForm.html',
            resolve: {
            	role: function(){return $scope.role;}
            }
        });
    }

    $scope.openNewPhaseForm = function () {
        var modalInstance = $uibModal.open({
			controller: 'NewPhaseFormController',
            templateUrl: 'NewPhaseForm.html'
        });
    }

    $scope.openCommentForm = function(id){
    	$scope.id = id;
    	console.log(id);
    	var modalInstance = $uibModal.open({
			controller: 'NewCommentFormController',
            templateUrl: 'NewCommentForm.html',
            resolve: {
            	id: function(){return $scope.id;}
            }
        });
    }

    $scope.deletePhase = function (value){
    	var id1 = $scope.phases[value].Divided_Id;
    	var id2 = $scope.phases[value].Id_Project
    	$scope.eliminateData('/Divided_in/' + id1);
    	$timeout(function() {$scope.loadProjectPhases(id2);}, 100);
    }

    $scope.deletePhaseMaterial = function (value){
    	var id1 = $scope.materials[value].Posseses_Id;
    	var id2 = $scope.materials[value].Divided_Id
    	$scope.eliminateData('Posseses/' + id1);
    	$timeout(function() {$scope.loadPhaseMaterials(id2);}, 100);
    }

    $scope.deletePhaseList = function (value){
    	$scope.eliminateData('Stage/' + value)
    }

	$scope.goTo = function ( path ) {
    	$location.path( path );
    };

    $scope.eliminateData = function(url){
		$http.delete('http://localhost:50484/' + url).then(
			function(response){
				console.log(response);
			},
			function(response){
				console.log(error);
			});
	}

	$scope.obtainProjectBudget = function(value){

		var id = $scope.projects[value].Id_Proyect;
		$scope.loadProjectPhases(id);
		$timeout(function() {for (var i = 0 ; i < $scope.phases.length ; i++) {
			$scope.getContent('Stage/' + $scope.phases[i].Stage_Id);
			var name = $scope.info.Name;
			var id2 = $scope.phases[i].Divided_Id;
			$scope.loadPhaseMaterials(id2);

			$scope.sum = 0;
			
			for (var i = 0 ; i < $scope.materials.length ; i++){
				var quant = $scope.materials.Quantity;
				var id3 = $scope.materials.Id_Material;
				$scope.getContent('Material/' + id3);
				var price = $scope.info.Price;

				$scope.sum = $scope.sum + (price * quant);
			}
			var newFile = { phase: name, total: $scope.sum};
			console.log(name)
		}}, 100);
	}
});