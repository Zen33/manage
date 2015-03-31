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
			scope.gridOptions = { };
			scope.gridOptions.enableCellEditOnFocus = true;
			scope.gridOptions.useExternalPagination = true;
			scope.gridOptions.paginationPageSizes = [1, 2, 3];
		    scope.gridOptions.paginationPageSize = 20;
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
				// var columnDefs = scope.gridOptions.columnDefs;
				// for (var i in columnDefs) {
				// 	newRow[columnDefs[i].name] =  
				// }
				// var newRow = {
				// 	scope.gridOptions.columnDefs
				// }
				// scope.gridOptions.data.push({

				// })
			}
		}
	}
});