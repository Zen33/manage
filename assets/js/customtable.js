var MisApp = angular.module('misapp');

MisApp.directive('customtable', function($modal, $compile, customtable, PlanService, UserService, TrainService, ActionService, ServiceService, $state, CalendarService) {
	return {
		templateUrl: 'assets/templates/customtable.html',
		restrict: 'E',
		scope: {
			active: '=',
			customdata: '='
		},
		link: function(scope, elm, attrs) {
			/* get public and models from main page */
			var token = UserService.getToken();
			var customdata = {};
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
					case 'services':
						scope.switchToService();
						break;
					case 'trainings':
						scope.switchToTraining();
						break;
					case 'actions':
						scope.switchToActions();
						break;
					case 'calendar':
						scope.switchToCalendar();
						break;
				}
		    });

		    scope.switchToUser = function() {
		    	scope.alert = {
		    		msg: ""
		    	};
	    		UserService.getUsers({token: token}).then(function(data) {
	    			scope.gridOptions.columnDefs = customtable.defaultColumnDefs('users');
					scope.gridOptions.data = data;
					customdata = data;
				}, function(err) {
					scope.alert = {
			    		msg: err,
			    		type: 'danger'
			    	};
				});
				// return customtable.getBodyFromResponse(customtable.fixtures.users).users;
		    	
		    	scope.buttonText = "";
		    	scope.back = false;
		    };

		    scope.switchToBill = function() {
		    	scope.alert = {
		    		msg: ""
		    	};
		    	scope.buttonText = "";
		    	scope.back = false;
		    	scope.gridOptions.columnDefs = customtable.defaultColumnDefs(scope.active);
				scope.gridOptions.data = [];
		    }

			scope.switchToPublicProject = function() {
				var columnDefs = customtable.defaultColumnDefs('projects');
				scope.alert = {
		    		msg: ""
		    	};
				scope.radioModel = "public";
				scope.back = false;
				scope.buttonText = "添加公开计划";
				scope.gridOptions.columnDefs = columnDefs;
				scope.gridOptions.data = customdata[scope.radioModel];
			};

			scope.switchToProjectModel = function() {
				scope.gridOptions.enableCellEdit = true;
				scope.radioModel = "model";
				var projects = {
					'model': [],
					'public': []
				};
				PlanService.getPlanModels({token: token})
				.then(function(data) {
					projects['model'] = data;
					return PlanService.getPublicPlans({token: token});
				})
				.then(function(data) {
					projects['public'] = data;
					customdata = projects;
					scope.gridOptions.data = customdata[scope.radioModel];
					return projects;
				})
				scope.alert = {
		    		msg: ""
		    	};
				scope.back = false;
				scope.buttonText = "添加模板计划";
				var columnDefs = customtable.defaultColumnDefs('projects');
				columnDefs.push({name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '});
				scope.gridOptions.columnDefs = columnDefs;
			};

			scope.switchToService = function() {
				scope.alert = {
		    		msg: ""
		    	};
				var columnDefs = customtable.defaultColumnDefs('services');
				scope.gridOptions.data = [];
				if (scope.active === "services") {
					ServiceService.getServices({token: token}).then(function(data) {
						customdata = data;
						scope.gridOptions.data = customdata;
						return data;
					}, function(err) {
						return err;
					})
					scope.gridOptions.enableCellEdit = true;
					columnDefs.push({name: 'add', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '});
					columnDefs.push({name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '});
					scope.buttonText = "添加服务";
				}
				else {
					scope.buttonText = "";
					ServiceService.getUserServices({token: token, uid: scope.uid})
					.then(function(data) {
						scope.gridOptions.data = data;
					})
					scope.back = true;
					scope.gridOptions.enableCellEdit = false;
				}
				scope.gridOptions.columnDefs = columnDefs;
			};

			scope.switchToTraining = function() {
				scope.alert = {
		    		msg: ""
		    	};
				var columnDefs = customtable.defaultColumnDefs('trainings');
				scope.gridOptions.data = [];
				if (scope.active === 'trainings') {
					TrainService.getTrainModels({token: token}).then(function(data) {
						customdata = data;
						scope.gridOptions.data = customdata;
						return data;
					}, function(err) {
						return err;
					})
					scope.gridOptions.enableCellEdit = true;
					columnDefs.push({name: 'save', displayName: '保存', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.saveData(row.entity)" >保存</button> '});
					columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '});
					scope.back = false;
					scope.buttonText = "添加训练模板";
				}
				else {
					scope.gridOptions.enableCellEdit = false;
					columnDefs.unshift({ field: 'rank', displayName: '第几天', type: 'number', cellTemplate: '<span>第{{row.entity.rank}}天</span>'});
					if (scope.active === "users") {
						PlanService.getUserDoingPlan({token: token, uid: scope.uid}).then(function(data) {
							scope.gridOptions.data = customtable.getBodyFromResponse(data);
						});
						scope.buttonText = "更换计划";
					}
					else {
						TrainService.getPlanTrains({id: scope.pid}).then(function(data) {
							scope.gridOptions.data = customtable.getBodyFromResponse(data);
						});
						columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '});
						// _.find(columnDefs, function(c) {
						// 	return c.field === "rank";
						// }).enableCellEdit = true;
						scope.buttonText = "添加训练";
					}
					// scope.gridOptions.data = customtable.getBodyFromResponse(customtable.fixtures.user_trainings).subplan;
					scope.back = true;
				}
				scope.gridOptions.columnDefs = columnDefs;
			};

			scope.switchToActions = function() {
				scope.alert = {
		    		msg: ""
		    	};
				var columnDefs = customtable.defaultColumnDefs('actions');
				scope.gridOptions.data = [];
				if (scope.active === 'actions') {
					ActionService.getActionModels({token: token}).then(function(data) {
						customdata = data;
						scope.gridOptions.data = customdata;
						return data;
					}, function(err) {
						return err;
					});
					scope.back = false;
					scope.gridOptions.enableCellEdit = true;
					scope.buttonText = "添加动作模板";
					columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '});
				}
				else {
					ActionService.getSubplanActions({spid: scope.spid}).then(function(data) {
						scope.gridOptions.data = data;
					});
					// scope.gridOptions.data = customtable.getBodyFromResponse(customtable.fixtures.subplan_actions).subplan;
					if (scope.active === "users") {
						scope.gridOptions.enableCellEdit = false;
						scope.back = true;
						scope.buttonText = "";
					}
					else {
						scope.gridOptions.enableCellEdit = false;
						scope.back = true;
						scope.buttonText = "添加动作";
						columnDefs.push({name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '})
					}
				}
				scope.gridOptions.columnDefs = columnDefs;
			};

			scope.switchToCalendar = function() {
            	// customdata = {
            	// 	course: [{
            	// 		service: 
            	// 		calendar: {
            	// 			id:
            	//			cid:
            	// 			stype: 
            	// 			cdate:
            	// 			minutes: 
            	// 			maxlimit: 
            	// 			sign:
            	// 			intro:
            	// 		},
            	// 		members: {
            	// 			member:
            	// 			candidate: 
            	// 		}
            	// 	}],
            	// 	service: []
            	// }
				if (scope.customdata) {
					scope.services = scope.customdata.service;
					scope.gridOptions.data = scope.customdata.course;
	            	scope.gridOptions.enableCellEdit = false;
					var columnDefs = customtable.defaultColumnDefs('calendar');
					scope.gridOptions.columnDefs = columnDefs;
					scope.back = true;
					scope.buttonText = "添加课程";
				}
			};

			scope.handleClickOnRightBtn = function() {
				scope.alert = {
		    		msg: ""
		    	};
				switch(scope.buttonText) {
					case "更换计划":
		    			// scope.modalData = customtable.getBodyFromResponse(customtable.fixtures.project_models).plan;
						PlanService.getPlanModels({token: token}).then(function(data) {
							var modaltitle = "更换计划";
							var options = [];
							for (var index in data) {
								var option = {};
								option.id = data[index].pid;
								option.name = data[index].name;
								option.duration = data[index].duration;
								option.intro = data[index].intro;
								options.push(option);
							}
							scope.open(data, options, modaltitle);
						}, function(err) {
							scope.alert = {
					    		msg: "获取计划模板失败 " + err,
					    		type: "danger"
					    	};
						});
		    			break;
		    		case "添加训练":
		    			// scope.modalData = customtable.getBodyFromResponse(customtable.fixtures.project_models).plan;
						TrainService.getTrainModels({token: token}).then(function(data) {
							var modaltitle = "训练模板";
							var options = [];
							for (var index in data) {
								var option = {};
								option.id = data[index].id;
								option.name = data[index].name;
								option.duration = data[index].duration;
								option.intro = data[index].intro;
								options.push(option);
							}
							scope.open(data, options, modaltitle);
						}, function(err) {
							scope.alert = {
					    		msg: "获取训练模板失败 " + err,
					    		type: "danger"
					    	};
						});
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
		    			// scope.modalData = customtable.getBodyFromResponse(customtable.fixtures.project_models).plan;
						PlanService.getPlanModels({token: token}).then(function(data) {
							var modaltitle = "选择一个计划模板";
							var options = [];
							for (var index in data) {
								var option = {};
								option.id = data[index].pid;
								option.name = data[index].name;
								option.duration = data[index].duration;
								option.intro = data[index].intro;
								options.push(option);
							}
							scope.open(data, options, modaltitle);
						}, function(err) {
							scope.alert = {
					    		msg: "获取计划模板失败 " + err,
					    		type: "danger"
					    	};
						});
						break;
					case "添加服务":
						var columnDefs = scope.gridOptions.columnDefs;
		    			var newRow = {};
		    			for (var i in columnDefs) {
							newRow[columnDefs[i].field] = '';
						}
		    			scope.gridOptions.data.push(newRow);
		    			break;
		    		case "添加动作":
		    			ActionService.getActionModels({token: token}).then(function(data) {
		    				var modaltitle = "动作模板";
							var options = [];
							for (var index in data) {
								var option = {};
								option.id = data[index].id;
								option.name = data[index].name;
								option.duration = data[index].duration;
								option.intro = data[index].intro;
								options.push(option);
							}
							scope.open(data, options, modaltitle);
		    			}, function(err) {
							scope.alert = {
					    		msg: "获取动作模板失败 " + err,
					    		type: "danger"
					    	};
						});
						break;
					case "添加训练模板":
						var columnDefs = scope.gridOptions.columnDefs;
		    			var newRow = {};
		    			for (var i in columnDefs) {
							newRow[columnDefs[i].field] = '';
						}
		    			scope.gridOptions.data.push(newRow);
		    			break;
		    		case "添加动作模板":
		    			var newAction = {
							id: "",
							name: "",	
							intro: "",
							pic: "",
							picType: "",
							url: null,
							mediaType: "",
							category: 0,
							quantiry: 0,
							units: "次数",
							duration: 0
						}
		    			scope.viewAction(newAction);
		    			break;
		    		case "添加课程":
						var modalInstance = $modal.open({
							animation: true,
							templateUrl: 'assets/templates/coursemodal.html',
							controller: 'CourseModalInstanceCtrl',
							resolve: {
								calendar: function() {
									var calendar = {};
									calendar.cid = scope.customdata.calendarId;;
									calendar.cdate = scope.customdata.cdate;
									return calendar;
								},
								services: function() {
									return scope.customdata.service;
								}
							}
						});
						modalInstance.result.then(function (newCourse) {
							newCourse.token = token;
							CalendarService.postCourse(newCourse).then(function(data) {
								scope.customdata = data;
								scope.switchToCalendar();
							}, function (err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						});
						break;
				}
			}

			scope.rmData = function(rowEntity) {
				scope.alert = {
		    		msg: ""
		    	};
	    		switch(scope.buttonText) {
					case "添加模板计划":
						if (rowEntity.pid === '') {
							scope.gridOptions.data.pop();
						}
						else {
							PlanService.deletePlanModel({id: rowEntity.pid, token: token})
							.then(function(data) {
								customdata['model'] = data;
								scope.gridOptions.data = customdata['model'];
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						break;
					case "添加训练":
						if (rowEntity.id === '') {
							scope.gridOptions.data.pop();
						}
						else {
							var params = {
								rid: rowEntity.rid,
								token: token
							}
							TrainService.deletePlanTrain(params)
							.then(function(data) {
								scope.gridOptions.data = data;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						break;
					case "添加服务":
						if (rowEntity.id) {
							var params = {
								token: token,
								sid: rowEntity.sid
							}
							ServiceService.deleteService(params)
							.then(function(data) {
								customdata = data;
								scope.gridOptions.data = customdata;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						else {
							var data = scope.gridOptions.data;
							for (var index in data) {
								if (data[index].$$hashKey === rowEntity.$$hashKey) {
									data.splice(index, 1);
								}
							}
							scope.gridOptions.data = data;
						}
						break;
					case "添加公开计划":
						PlanService.deletePublicPlan({pid: rowEntity.pid, token: token})
						.then(function(data) {
							customdata['public'] = data;
							scope.gridOptions.data = customdata['public'];
						}, function(err) {
							scope.alert = {
								msg: err,
								type: 'danger'
							}
						});
						break;
					case "添加训练模板":
						if (rowEntity.id) {
							var params = {
								token: token,
								id: rowEntity.id
							}
							TrainService.deleteTrainModel(params)
							.then(function(data) {
								customdata = data;
								scope.gridOptions.data = customdata;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						else {
							var data = scope.gridOptions.data;
							for (var index in data) {
								if (data[index].$$hashKey === rowEntity.$$hashKey) {
									data.splice(index, 1);
								}
							}
							scope.gridOptions.data = data;
						};
						break;
					case "添加课程": 
						CalendarService.deleteCourse({token: token, id: rowEntity.calendar.id}).then(function(data) {
							var index = _.findIndex(scope.gridOptions.data, function(datum) {
								return datum.calendar.id === rowEntity.calendar.id;
							});
							scope.gridOptions.data.splice(index, 1);
						}, function(err) {
							scope.alert = {
								msg: err,
								type: 'danger'
							}
						});
						break;
				}
			}

			scope.saveData = function( rowEntity ) {
				scope.alert = {
		    		msg: ""
		    	};
				switch(scope.buttonText) {
					case "添加模板计划":
						if (rowEntity.pid === '') {
							PlanService.postPlanModel({'name': rowEntity.name, 'intro': rowEntity.intro, 'duration': rowEntity.duration || 0, 'token': token})
							.then(function(resp) {
								return PlanService.getPlanModels({token: token});
							})
							.then(function(data) {
								customdata['model'] = data;
								scope.gridOptions.data = customdata['model'];
							});
						}
						else {
							var params = {
								name: rowEntity.name, 
								intro: rowEntity.intro, 
								duration: rowEntity.duration || 0, 
								token: token, 
								id: rowEntity.pid
							}
							PlanService.patchPlanModel(params)
							.then(function(resp) {
								return PlanService.getPlanModels({token: token});
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							})
							.then(function(data) {
								customdata['model'] = data;
								scope.gridOptions.data = customdata['model'];
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						break;
					case "添加训练模板":
						if (!rowEntity.id) {
							TrainService.postTrainModel({'name': rowEntity.name, 'intro': rowEntity.intro, 'duration': rowEntity.duration || 0, 'token': token})
							.then(function(resp) {
								return TrainService.getTrainModels({token: token});
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							})
							.then(function(data) {
								customdata = data;
								scope.gridOptions.data = customdata;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						else {
							var params = {
								name: rowEntity.name, 
								intro: rowEntity.intro, 
								duration: rowEntity.duration || 0, 
								token: token, 
								id: rowEntity.id
							}
							TrainService.patchTrainModel(params)
							.then(function(resp) {
								return TrainService.getTrainModels({token: token});
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							})
							.then(function(data) {
								customdata = data;
								scope.gridOptions.data = customdata;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						break;
					case "添加动作模板":
						console.log("添加动作模板", rowEntity, scope.file)
						if (scope.file) {
							var type = scope.file.type.match(/(\w*)\/(\w*)/);

						}
						// var params = {
						// 	name	
						// 	intro
						// 	pic: ''
						// 	picType: 'jpg',
						// 	media: ''
						// 	mediaType: ''
						// 	category  视频1 或 图片0 int
						// 	quantiry 数量
						// 	units 单位 次数/时长 传汉字即可
						// 	duration 时长
						// };
						// ActionService.postActionModel()
						break;
					case "添加服务":
						// var params = rowEntity;
						// delete params.sid;
						// params.token = token;
						if (rowEntity.sid === '') {
							var params = {
								token: token,
				                name: rowEntity.name,
				                intro: rowEntity.intro,
				                cost: rowEntity.cost || 0,
				                online_times: rowEntity.online_times || 1,
				                offline_times: rowEntity.offline_times || 1,
				                online: rowEntity.online || 0,
				                offline: rowEntity.offline || 0
							}
							ServiceService.postService(params).then(function(resp) {
								return ServiceService.getServices({token: token});
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							})
							.then(function(data) {
								customdata = data;
								scope.gridOptions.data = customdata;
							}, function(err) {
								scope.alert = {
									msg: err,
									type: 'danger'
								}
							});
						}
						else {
							var params = {
								token: token,
								name: rowEntity.name,
								intro: rowEntity.intro,
								cost: rowEntity.cost || 0,
				                online_times: rowEntity.online_times || 1,
				                offline_times: rowEntity.offline_times || 1,
				                online: rowEntity.online || 0,
				                offline: rowEntity.offline || 0,
								sid: rowEntity.sid
							}
							ServiceService.patchService(params).then(function(resp) {
								
							}, function(err) {
								scope.alert = {
									type: 'danger',
									msg: err
								}
							});
						}
						break;
				}
			};

			scope.viewPlan = function(rowEntity) {
				scope.alert = {
		    		msg: ""
		    	};
				scope.active === "users" ? scope.uid = rowEntity.uid : scope.pid = rowEntity.pid;
				scope.switchToTraining();
			}

			scope.viewTraining = function(rowEntity) {
				scope.alert = {
		    		msg: ""
		    	};
				scope.spid = customtable.getId(rowEntity);
				scope.switchToActions();
			}

			// when back button pressed
			scope.handleBack = function() {
				scope.alert = {
		    		msg: ""
		    	};
				switch(scope.active) {
					case 'users':
						if (scope.gridOptions.columnDefs[1].displayName === "动作名称") {
							scope.switchToTraining();
						}
						else {
							scope.switchToUser();
						}
						break;
					case 'projects':
						if (scope.buttonText === "添加训练") {
							scope.radioModel === 'public' ? scope.switchToPublicProject() : scope.switchToProjectModel();
						}
						else {
							scope.switchToTraining();
						}
						break;
					case 'trainings':
						scope.switchToTraining();
						break;
					case 'actions':
						scope.switchToActions();
						break;
					case 'calendar':
						$state.go('main', {active: 'calendar'});
						break;
				}
					
			}

			scope.viewService = function(rowEntity) {
				scope.alert = {
		    		msg: ""
		    	};
				scope.uid = customtable.getId(rowEntity);
				scope.switchToService();
			}
		},
		controller: function($scope, $modal) {
			var initialSettings = function() {
		    	$scope.gridOptions = {
	    			paginationPageSizes: [50, 100, 200],
	    			paginationPageSize: 20,
	    			multiSelect: false,
	    			enableRowHeaderSelection: false,
	    			importerDataAddCallback: function ( grid, newObjects ) {
						$scope.data = $scope.data.concat( newObjects );
						console.log("importerDataAddCallback", $scope.data)
					},
					onRegisterApi: function(gridApi){ 
				      $scope.gridApi = gridApi;
				    }
				};
		    	$scope.radioModel = 'model';
		    	$scope.alert = {
		    		msg: "",
		    		type: 'danger'
		    	};
		    	$scope.back = false;
		    	token = UserService.getToken();
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
		    };

			$scope.handleMembers = function(rowEntity) {
				$scope.alert = {
		    		msg: ""
		    	};
				var modalInstance = $modal.open({
					animation: true,
					templateUrl: 'assets/templates/membersmodal.html',
					controller: 'MembersModalInstanceCtrl',
					resolve: {
						members: function() {
							return CalendarService.getParticipates({token: token, id: rowEntity.calendar.id}).then(function(data) {
								return data;
							}, function(err) {
								$scope.alert = {
									msg: err,
									type: 'danger'
								}
							})
						}
					}
				});

				modalInstance.result.then(function (params) {
					params.token = token;
					params.id = rowEntity.calendar.id;
					CalendarService.patchParticipates(params).then(function(data) {
						rowEntity.members = data;
					}, function(err) {
						$scope.alert = {
							msg: err || "出错",
							type: 'danger'
						}
					});
				}, function(err) {
					console.log(err)
				})
			};

			$scope.viewAction = function(rowEntity) {
				$scope.alert.msg = "";
				var modalInstance = $modal.open({
					animation: true,
					templateUrl: 'assets/templates/uploadmodal.html',
					controller: 'UploadModalInstanceCtrl',
					resolve: {
						editable: function() {
							if ($scope.active === "actions") {
								return true;
							}
							else {
								return false;
							}
						},
						action: function() {
							return rowEntity;
						}
					}
				});

				modalInstance.result.then(function (action) {
					if (action.id) {
						action.token = token;
						ActionService.patchActionModel(action).then(function(data) {

						}, function(err) {
							$scope.alert.msg = err;
						})
					}
					else {
						delete action.id;
						action.token = token;
						ActionService.postActionModel(action).then(function(data) {
							ActionService.getActionModels({token: token})
							.then(function(data) {
								$scope.gridOptions.data = data;
							}, function(err) {
								$scope.alert.msg = err;
							});
						}, function(err) {
							$scope.alert.msg = err;
						})
					}
				}, function () {
					
				});
			};

		    $scope.open = function (originalData, options, title) {
		    	$scope.alert = {
		    		msg: ""
		    	};
				var modalInstance = $modal.open({
					animation: true,
					templateUrl: 'assets/templates/optionsmodal.html',
					controller: 'OptionsModalInstanceCtrl',
					resolve: {
						options: function() {
							return options;
						},
						title: function() {
							return title;
						} 
					}
				});

				modalInstance.result.then(function (id) {
					switch($scope.buttonText) {
						case "更换计划":
							PlanService.postUserPlan({token: token, uid: $scope.uid, pid: id}).then(function(data) {
								$scope.gridOptions.data = data;
							}, function(err) {
								$scope.alert = {
									msg: err,
									type: "danger"
								}
							});
							break;
						case "添加训练":
							TrainService.postPublicTrain({token: token, pid: $scope.pid, spid: id.spid, rank: id.rank}).then(function(data) {
								$scope.gridOptions.data = data;
							}, function(err) {
								$scope.alert = {
									msg: err,
									type: "danger"
								}
							});
							break;
						case "添加公开计划":
							PlanService.postPublicPlan({token: token, pid: id}).then(function(data) {
								$scope.gridOptions.data = data;
							}, function(err) {
								$scope.alert = {
									msg: err,
									type: "danger"
								}
							});
							break;
						case "添加动作":
							ActionService.postSubplanAction({token: token, spid: $scope.spid, did: id}).then(function(data) {
								$scope.gridOptions.data = data;
							}, function(err) {
								$scope.alert = {
									msg: err,
									type: "danger"
								}
							});
							break;
					}
				}, function () {
					
				});
			};

		}
	}
});