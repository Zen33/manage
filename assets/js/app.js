var MisApp = angular.module('misapp', ['ui.router', 'ui.grid', 'ngTouch', 'ui.grid.edit'
	, 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.pagination', 'ui.bootstrap']);
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
		url: "/:params",
		params: {active: null, userinfo: null},
		templateUrl: "assets/templates/main.html",
		resolve: {
			tableData: function($stateParams, PlanService, customtable, UserService, $q, ServiceService, TrainService, ActionService, CalendarService) {
				var token = UserService.getToken();
				var deferred = $q.defer();
				switch($stateParams.active) {
					case 'users': 
						// return UserService.getUsers({token: token}).then(function(data) {
						// 	return data;
						// }, function(err) {
						// 	return err;
						// });
    					return customtable.getBodyFromResponse(customtable.fixtures.users).users;
					case 'projects':
						var projects = {
							'model': [],
							'public': []
						};
						// return PlanService.getPlanModels({token: token})
						// .then(function(data) {
						// 	projects['model'] = data;
						// 	return PlanService.getPublicPlans({token: token});
						// })
						// .then(function(data) {
						// 	projects['public'] = data;
						// 	return projects;
						// })
    					projects['model'] = customtable.getBodyFromResponse(customtable.fixtures.project_models).plan;
    					projects['public'] = customtable.getBodyFromResponse(customtable.fixtures.public_projects).plan;
    					return projects;
    				case 'services':
    					return customtable.getBodyFromResponse(customtable.fixtures.coach_services).services;
    					// return ServiceService.getServices({token: token}).then(function(data) {
    					// 	return data;
    					// }, function(err) {
    					// 	return err;
    					// })
    				case 'trainings':
    					return customtable.getBodyFromResponse(customtable.fixtures.training_models).subplan;
    					// return TrainService.getTrainModels({token: token}).then(function(data) {
    					// 	return data;
    					// }, function(err) {
    					// 	return err;
    					// })
    				case 'actions':
    					return customtable.getBodyFromResponse(customtable.fixtures.action_models).desc;
    					// return ActionService.getActionModels({token: token}).then(function(data) {
    					// 	return data;
    					// }, function(err) {
    					// 	return err;
    					// });
    				case 'courses':
    					// return customtable.getBodyFromResponse(customtable.fixtures.current_course_schedules).calendar;
    					return CalendarService.getCalendar({token: token, page: 0}).then(function(data) {
			        		var raw = data;
			        		return customtable.formatCalendar(raw);
			        	});
				}
			}
		},
		controller: function($scope, $http, $stateParams, tableData) {
			$scope.active = $stateParams.active;
			$scope.userinfo = $stateParams.userinfo;
			$scope.tableVisible = ($stateParams.active !== "courses" && $stateParams.active !== "messages");
			$scope.chatboxVisible = ($stateParams.active === "messages");
			$scope.calendarVisible = ($stateParams.active === "courses");
			$scope.tableData = tableData;
	    }
	})
});