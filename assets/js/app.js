var MisApp = angular.module('misapp', ['ui.router', 'ui.grid', 'ngTouch', 'ui.grid.edit'
	, 'ui.grid.rowEdit', 'ui.grid.cellNav', 'ui.grid.pagination', 'ui.bootstrap', 'base64']);
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
		controller: function($scope, $http, $stateParams) {
			$scope.active = $stateParams.active;
			$scope.userinfo = $stateParams.userinfo;
	    }
	})
});