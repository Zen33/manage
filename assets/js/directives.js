var MisApp = angular.module('misapp');

MisApp.directive('comfirm', function($state) {
	return {
		templateUrl: 'assets/templates/comfirm.html',
		restrict: 'E',
		controller: function($scope, $http) {
			$scope.loginObj = {
				username: "",
				password: "",
				rememberMe: true
			};
			$scope.login = function() {
				// $http.post('/someUrl', $scope.loginObj).
			  	// success(function(data, status, headers, config) {
				    // this callback will be called asynchronously
				    // when the response is available
				    $state.go('main');
			  	// }).
			  	// error(function(data, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
			  	// });
			};
			$scope.onTextClick = function($event) {
				$event.target.select();
			}
		}
	}
});

MisApp.directive('customtable', function($compile) {
	return {
		templateUrl: 'assets/templates/customtable.html',
		restrict: 'E',
		link: function(scope, elm, attrs) {
			scope.gridOptions = {
				enableRowSelection: true,
    			enableSelectAll: false,
    			enableCellEditOnFocus: true,
    			useExternalPagination: true,
    			paginationPageSizes: [50, 100, 200],
    			paginationPageSize: 20
			};
			scope.$watch(attrs.customData, function(value) {
				scope.gridOptions.columnDefs = value.columnDefs;
		    });


			// $http.get('/data/500_complex.json')
			// .success(function(data) {
			//   $scope.gridOptions.data = data;
			// });

			scope.currentFocused = "";

			scope.getCurrentFocus = function(){
				var rowCol = scope.gridApi.cellNav.getFocusedCell();
				if(rowCol !== null) {
				  	scope.currentFocused = 'Row Id:' + rowCol.row.entity.id + ' col:' + rowCol.col.colDef.name;
				}
			}

			scope.gridOptions.onRegisterApi = function(gridApi) {
				scope.gridApi = gridApi;
				scope.gridApi.pagination.on.paginationChanged(scope, function( currentPage, pageSize){
					scope.getPage(currentPage, pageSize);
				});
    		}

    		scope.clearAll = function() {
				scope.gridApi.selection.clearSelectedRows();
			}

			scope.rmData = function() {
				var selections = scope.gridApi.selection.getSelectedRows();
				selections.filter(function(s) { return s; });
				// $http.put('', selections);
				var currentPage = scope.gridApi.pagination.getPage();
				scope.getPage(currentPage, 20);
			}

			scope.getPage = function(pageNumber, pageSize){
				var startingRow = pageSize * ( pageNumber - 1);   // page number starts at 1, not zero
				var newData = [];
				scope.$watch(attrs.customData, function(value) {
					var data = value.data;
					for( var i = startingRow; i < startingRow + scope.gridOptions.paginationPageSize; i++ ) {
						newData.push( data[i] );
					}
					scope.gridOptions.data = newData;
					scope.gridOptions.totalItems = data.length;
			    });
				
			}

			scope.getPage(1, 20);

			scope.addData = function() {
				var columnDefs = scope.gridOptions.columnDefs;
				var newRow = {};
				for (var i in columnDefs) {
					newRow[columnDefs[i].name] = '';
				}
				// $http.post('', newRow);
			}
		}
	}
});

MisApp.directive('profile', function($state) {
	return {
		templateUrl: 'assets/templates/profile.html',
		restrict: 'E'
	}
});

MisApp.directive('nav', function($state) {
	return {
		templateUrl: 'assets/templates/nav.html',
		restrict: 'E'
	}
});