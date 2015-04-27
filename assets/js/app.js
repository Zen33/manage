var MisApp = angular.module('misapp', ['ui.router', 'ui.grid', 'ngTouch', 'ui.grid.edit'
	, 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.pagination', 'ui.grid.selection', 'ui.bootstrap', 'misapp.service']);
MisApp.run(function(customtable) {
});
MisApp.config(function($stateProvider, $urlRouterProvider, $httpProvider, customtable) {
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
		params: {active: null, userinfo: null, tableVisible: true},
		templateUrl: "assets/templates/main.html",
		resolve: {
			tableData: function($stateParams, PlanService) {
				// PlanService.getPlans({uid: $stateParams.userinfo.uid}).then(function(resp) {
					
				// }, function(error) {
				// 	return error;
				// });
			}
		},
		controller: function($scope, $http, $stateParams, tableData) {
			$scope.active = $stateParams.active;
			$scope.userinfo = $stateParams.userinfo;
			$scope.tableVisible = $stateParams.tableVisible;
			$scope.chatboxVisible = !$stateParams.tableVisible;
			$scope.tableData = tableData;
	    }
	})
});