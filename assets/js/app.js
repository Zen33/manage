var MisApp = angular.module('misapp', ['ui.router', 'ui.grid', 'ngTouch', 'ui.grid.edit'
	, 'ui.grid.cellNav', 'ui.grid.pagination', 'ui.grid.selection', 'ui.bootstrap']);

MisApp.config(function($stateProvider, $urlRouterProvider, $httpProvider) {
  $urlRouterProvider.otherwise("/");
  $stateProvider
    .state('authentication', {
		url: "/",
		templateUrl: "index.html"
    })
    .state('main', {
		url: "/main",
		templateUrl: "assets/templates/main.html",
		controller: function($scope, $http) {
	      	$scope.tableData = {
	      		data: [{
					id: "1",
					age: "23",
					address: {
						city: "LA"
					},
					name: "Helicopter"
				}, {
					id: "2",
					age: "30",
					address: {
						city: "LA"
					},
					name: "Helicopter"
				}, {
					id: "3",
					age: "3",
					address: {
						city: "LA"
					},
					name: "Helicopter"
				}, {
					id: "4",
					age: "300",
					address: {
						city: "LA"
					},
					name: "Helicopter"
				}],
				columnDefs: [
					{ name: 'id', enableCellEdit: false },
					{ name: 'name', displayName: 'Name (editOnFocus)', width: 300},
					{ name: 'age', enableCellEditOnFocus:false, displayName:'age (f2/dblClick edit)', width: 200  },
					{ name: 'address.city', enableCellEdit: true, width: 300 }
				]
			}
	    }
	})
});