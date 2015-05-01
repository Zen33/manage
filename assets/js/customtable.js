var MisApp = angular.module('misapp');

MisApp.directive('customtable', function($compile, customtable, PlanService, UserService, TrainService, ActionService) {
	return {
		templateUrl: 'assets/templates/customtable.html',
		restrict: 'E',
		scope: {
			customdata: '=',
			active: '='
		},
		link: function(scope, elm, attrs) {
			/* get public and models from main page */
			var token = UserService.getToken();
		    scope.$watch(attrs.active, function(value) {
				scope.active = value;
				switch(value) {
					case 'users': 
						scope.switchToUser();
						break;
					case 'projects':
						scope.switchToProjectModel();
						break;
					case 'home':
						scope.switchToBill();
						break;
				}
		    });

		    scope.switchToUser = function() {
		    	scope.buttonText = "";
		    	scope.back = false;
				scope.gridOptions.columnDefs = customtable.defaultColumnDefs(scope.active);
				scope.gridOptions.data = scope.customdata;
		    };

		    scope.switchToBill = function() {
		    	scope.buttonText = "";
		    	scope.back = false;
		    	scope.gridOptions.columnDefs = customtable.defaultColumnDefs(scope.active);
				scope.gridOptions.data = [];
		    }

			scope.switchToPublicProject = function() {
				scope.radioModel = "public";
				scope.back = false;
				scope.addPublic = false;
				scope.buttonText = "添加公开计划";
				var columnDefs = _.map(customtable.defaultColumnDefs(scope.active), function(columnDef) {
					if (columnDef.displayName !== 'id') {
						columnDef['enableCellEdit'] = false;
						columnDef['allowCellFocus'] = false;
						columnDef['cellEditableCondition'] = false;
					}
					return columnDef;
				});
				scope.gridOptions.columnDefs = columnDefs;
				scope.gridOptions.data = scope.customdata[scope.radioModel];
			};

			scope.switchToProjectModel = function() {
				scope.radioModel = "model";
				scope.back = false;
				scope.addPublic = false;
				scope.buttonText = "添加模板计划";
				scope.gridOptions.data = scope.customdata[scope.radioModel];
				var columnDefs = _.map(customtable.defaultColumnDefs(scope.active), function(columnDef) {
					if (columnDef.displayName !== 'id') {
						columnDef['enableCellEdit'] = true;
					}
					return columnDef;
				});
				columnDefs.push({name: 'active', displayName: '公开', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.activeModel(row.entity)" >公开</button> '});
				columnDefs.push({name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '});
				scope.gridOptions.columnDefs = columnDefs;
			};

			scope.switchToTraining = function() {
				if (scope.active === 'trainings') {
					scope.gridOptions.data = scope.customdata[scope.radioModel];
					scope.gridOptions.columnDefs = customtable.defaultColumnDefs('trainings');
				}
				else {
					if (scope.active === "users") {
						// PlanService.getUserDoingPlan(token).then(function(data) {
						// 	scope.gridOptions.data = customtable.getBodyFromResponse(data);
						// 	scope.gridOptions.columnDefs = customtable.defaultColumnDefs('trainings');
						// });
					}
					else {
						// TrainService.getPlanTrains(scope.planId).then(function(data) {
						// 	scope.gridOptions.data = customtable.getBodyFromResponse(data);
						// 	scope.gridOptions.columnDefs = customtable.defaultColumnDefs('trainings');
						// })
					}
				}
				scope.gridOptions.data = customtable.getBodyFromResponse(customtable.fixtures.user_trainings);
				scope.gridOptions.columnDefs = scope.active === 'users' || scope.radioModel === 'public' ? customtable.defaultColumnDefs('trainings') :
				customtable.defaultColumnDefs('trainings').concat([{name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '},
				{name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '}]);
				scope.buttonText = scope.active === 'projects' ? "添加训练" : "更换计划";
				scope.addPublic = false;
				scope.back = true;
				scope.gridOptions.enableCellEdit = true;
			};

			scope.switchToActions = function() {
				if (scope.active === 'actions') {
					scope.gridOptions.data = scope.customdata[scope.radioModel];
					scope.gridOptions.columnDefs = customtable.defaultColumnDefs('actions');
					scope.gridOptions.columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '})
				}
				else {
					// ActionService.getActions(token).then(function(data) {
					// 	scope.gridOptions.data = customtable.getBodyFromResponse(data);
					// 	scope.gridOptions.columnDefs = customtable.defaultColumnDefs('train');
					// });
				}
				scope.gridOptions.columnDefs = customtable.defaultColumnDefs('actions');
				scope.gridOptions.data = customtable.getBodyFromResponse(customtable.fixtures.subplan_actions);
				scope.gridOptions.columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '})
				scope.buttonText = "";
				scope.back = true;
				scope.addPublic = false;
				scope.gridOptions.enableCellEdit = true;
			};

			scope.handleClickOnRightBtn = function() {
				switch(scope.buttonText) {
					case "更换计划":
						scope.showModal = true;
						scope.modaltitle = "更换计划";
						// PlanService.getPlanModels(token).then(function(data) {
						// 	return data;
						// }, function(err) {
						// 	return err;
						// });
		    			scope.modalData = customtable.getBodyFromResponse(customtable.fixtures.project_models);
		    			break;
		    		case "添加训练":
		    			var columnDefs = scope.gridOptions.columnDefs;
		    			var newRow = {};
		    			for (var i in columnDefs) {
							newRow[columnDefs[i].field] = '';
						}
		    			scope.gridOptions.data.push(newRow);
		    			break;
		    		case "添加模板计划":
		    			var columnDefs = scope.gridOptions.columnDefs;
		    			var newRow = {};
		    			for (var i in columnDefs) {
							newRow[columnDefs[i].field] = '';
						}
		    			scope.gridOptions.data.push(newRow);
		    			break;
		    		case "添加公开计划":
		    			scope.showModal = true;
						scope.modaltitle = "更换计划";
						scope.modalData = scope.customdata['model'];
						break;
				}
			}

			scope.rmData = function(rowEntity) {
				var id = customtable.getId(rowEntity);
				if (id == '') {
					switch(scope.buttonText) {
						case "添加模板计划":
							scope.gridOptions.data.pop();
							break;
						case "添加训练":
							scope.gridOptions.data.pop();
							break;
					}
				}
				else {
					switch(scope.buttonText) {
						case "添加模板计划":
							PlanService.deletePlanModel({id: id, token: token})
							.then(function(resp) {
								scope.customdata['model'] = data;
								scope.gridOptions.data = scope.customdata['model'];
							});
							break;
						case "添加训练":
							break;
					}
				}
			}

			scope.viewPlan = function(rowEntity) {
				scope.active === "users" ? scope.userId = rowEntity.uid : scope.planId = rowEntity.pid;
				scope.switchToTraining();
			}

			scope.viewTraining = function(rowEntity) {
				scope.switchToActions();
			}

			scope.switchProject = function() {
				// get user's token and admin's project models
				scope.table.columnDefs = customtable.defaultColumnDefs('projects');
				// scope.table.data = {
				// 	'public': 
				// 	'model': 
				// }
			}

			scope.reload = function() {
				switch(scope.active) {
					case 'users': 
						scope.switchToUser();
						break;
					case 'projects':
						scope.switchToProjectModel();
				}
					
			}

			scope.activeModel = function( rowEntity) {
				var id = customtable.getId(rowEntity);
				// PlanService.postPublicPlan(token).then(function(data) {
				// 	scope.customdata['public'] = data;
				// })
			}

			scope.saveData = function( rowEntity ) {
				var id = customtable.getId(rowEntity);
				if (id == '') {
					switch(scope.buttonText) {
						case "添加模板计划":
							PlanService.postPlanModel({name: rowEntity.name, intro: rowEntity.intro, duration: rowEntity.duration, token: token})
							.then(function(resp) {
								return PlanService.getPlanModels({token: token});
							})
							.then(function(data) {
								scope.customdata['model'] = data;
								scope.gridOptions.data = scope.customdata['model'];
							});
							break;
						case "添加训练":
							break;
					}
				}
				else {
					switch(scope.buttonText) {
						case "添加模板计划":
							PlanService.patchPlanModel({name: rowEntity.name, intro: rowEntity.intro, duration: rowEntity.duration, token: token})
							.then(function(resp) {
								return PlanService.getPlanModels({token: token});
							})
							.then(function(data) {
								scope.customdata['model'] = data;
								scope.gridOptions.data = scope.customdata['model'];
							});
							break;
						case "添加训练":
							break;
					}
				}
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
		    	};
		    	$scope.back = false;
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