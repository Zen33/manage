var MisApp = angular.module('misapp', ['ui.router', 'ui.grid', 'ngTouch', 'ui.grid.edit'
	, 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.pagination', 'ui.grid.selection', 'ui.bootstrap']);
MisApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
	$httpProvider.defaults.useXDomain = true;
	delete $httpProvider.defaults.headers.common['X-Requested-With'];
	$urlRouterProvider.otherwise("/");
	$stateProvider
    .state('authentication', {
		url: "/",
		templateUrl: "index.html"
    })
    .state('main', {
		url: "/main/:params",
		params: {active: null, userinfo: null},
		templateUrl: "assets/templates/main.html",
		resolve: {
			tableData: function($stateParams, PlanService, customtable, UserService, $q) {
				var token = UserService.getToken();
				var deferred = $q.defer();
				switch($stateParams.active) {
					case 'users': 
						// UserService.getUsers(token).then(function(data) {
						// 	return data;
						// }, function(err) {
						// 	return err;
						// });
    					return customtable.getBodyFromResponse(customtable.fixtures.users);
					case 'projects':
						var projects = {
							'model': [],
							'public': []
						}
						// return PlanService.getPlanModels(token)
						// .then(function(data) {
						// 	projects['model'] = data;
						// 	return PlanService.getPublicPlans(token);
						// })
						// .then(function(data) {
						// 	projects['public'] = response;
						// 	return projects;
						// }))
    					projects['model'] = customtable.getBodyFromResponse(customtable.fixtures.project_models);
    					projects['public'] = customtable.getBodyFromResponse(customtable.fixtures.public_projects);
    					return projects;

				}
			}
		},
		controller: function($scope, $http, $stateParams, tableData) {
			$scope.active = $stateParams.active;
			$scope.userinfo = $stateParams.userinfo;
			$scope.tableVisible = ($stateParams.active !== "courses" && $stateParams.active !== "messages");
			$scope.chatboxVisible = ($stateParams.active === "messages");
			// $scope.calendarVisible = ($stateParams.active === "courses");
			$scope.tableData = tableData;
	    }
	})
});