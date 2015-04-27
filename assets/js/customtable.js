var MisApp = angular.module('misapp');

MisApp.directive('customtable', function($compile, customtable) {
	return {
		templateUrl: 'assets/templates/customtable.html',
		restrict: 'E',
		link: function(scope, elm, attrs) {
			/* get public and models from main page */
			scope.table = {}
			scope.$watch(attrs.customData, function(value) {
				scope.table.data = value;
		    });
		    scope.$watch(attrs.active, function(value) {
				scope.active = value;
				scope.switchToUser();
		    });

		    scope.switchToUser = function() {
		    	scope.buttonText = "添加用户";
		    	scope.radioModel = 'model';
				scope.addPublic = false;
				scope.table.columnDefs = customtable.defaultColumnDefs(scope.active);
				var columnDefs = _.map(angular.copy(scope.table.columnDefs), function(columnDef) {
					if (columnDef.displayName !== 'id') {
						columnDef['enableCellEdit'] = false;
					}
					return columnDef;
				});
				scope.gridOptions.columnDefs = columnDefs;
				scope.gridOptions.data = scope.table.data[scope.radioModel];
		    };

			scope.switchToPublicProject = function() {
				scope.gridOptions.enableRowHeaderSelection = true;
				scope.radioModel = "public";
				scope.addPublic = false;
				scope.buttonText = "添加公开计划";
				var columnDefs = _.map(angular.copy(scope.table.columnDefs), function(columnDef) {
					if (columnDef.displayName !== 'id') {
						columnDef['enableCellEdit'] = false;
						columnDef['allowCellFocus'] = false;
						columnDef['cellEditableCondition'] = false;
					}
					return columnDef;
				});
				scope.gridOptions.columnDefs = columnDefs;
				scope.gridOptions.data = scope.table.data[scope.radioModel];
			};

			scope.switchToProjectModel = function() {
				scope.radioModel = "model";
				scope.gridOptions.enableRowHeaderSelection = true;
				scope.addPublic = false;
				scope.buttonText = "添加模板计划";
				var columnDefs = angular.copy(scope.table.columnDefs);
				columnDefs.push({name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '})
				scope.gridOptions.columnDefs = columnDefs;
				scope.gridOptions.data = scope.table.data[scope.radioModel];
			};

			scope.switchToTraining = function() {
				scope.gridOptions.columnDefs = customtable.defaultColumnDefs('train');
				scope.buttonText = "添加动作";
				scope.addPublic = false;
				scope.gridOptions.enableCellEdit = false;
				scope.gridOptions.data = customtable.fixtures.user_trainings;
			}

			scope.addData = function() {
				if (scope.radioModel === "public") {
					scope.radioModel = "model";
					scope.loadTable();
					scope.addPublic = true;
				}
				else {
					scope.addPublic = false;
					var columnDefs = scope.gridOptions.columnDefs;
					var newRow = {};
					for (var i in columnDefs) {
						newRow[columnDefs[i].name] = '';
					}
					scope.gridOptions.data.push(newRow);
					scope.gridOptions.multiSelect = false;
				}
			}

			scope.rmData = function(rowEntity) {
				// call delete
				console.log(scope.gridOptions.getRowIdentity(rowEntity));
			}

			scope.viewPlan = function(rowEntity) {
				// get user's schedules
				scope.switchToTraining();
			}

			scope.switchProject = function() {
				// get user's token and admin's project models
				scope.table.columnDefs = customtable.defaultColumnDefs('projects');
				scope.table.data = {
					'public': 
					'model': 
				}
			}

			scope.saveData = function( rowEntity ) {
				console.log("saveRow", rowEntity)
			}; 
		},
		controller: function($scope) {
			var initialSettings = function() {
		    	$scope.gridOptions = {
					enableRowSelection: true,
	    			paginationPageSizes: [50, 100, 200],
	    			paginationPageSize: 20,
	    			multiSelect: false,
	    			enableRowHeaderSelection: false
				};
		    	$scope.radioModel = 'model';
		    	$scope.alert = {
		    		msg: "从下列计划模板中选择一个作为公开模板发布"
		    	}
		    	$scope.addPublic = false;
			}
			initialSettings();

			$scope.gridOptions.onRegisterApi = function(gridApi){
			    $scope.gridApi = gridApi;
			};

		    $scope.loadTable = function() {
		    	if ($scope.radioModel === "public") {
					$scope.radioModel = "model";
				}
				else {
					$scope.radioModel = "public"
				}
		    	$scope.radioModel === 'public' ? $scope.switchToPublicProject() : $scope.switchToProjectModel();
		    }
		}
	}
});