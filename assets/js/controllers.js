var MisApp = angular.module('misapp');
MisApp.controller('OptionsModalInstanceCtrl', function ($scope, $modalInstance, options, title) {

	$scope.options = options;
	$scope.title = title;
	$scope.optionsChecked = {};

	for (var index in options) {
		$scope.optionsChecked[options[index].id] = false;
	}

	$scope.onChange = function(id) {
		for (var index in $scope.options) {
			if ($scope.options[index].id === id) {
				// if ($scope.optionsChecked[id]) {
				// 	$scope.optionsChecked[id] = false;
				// }
				// else {
					$scope.optionsChecked[id] = true;
				// }
			}
			else {
				$scope.optionsChecked[$scope.options[index].id] = false;
			}
		}
	};

	$scope.ok = function () {
		var id = -1;
		_.forEach($scope.optionsChecked, function(value, key) { 
			if (value) {
				id = key;
			} 
		});
		if (id === -1) {
			$modalInstance.dismiss('cancel');
		}
		else {
			$modalInstance.close(id);
		}
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

MisApp.controller('CourseModalInstanceCtrl', function ($scope, $modalInstance, calendar, services, CalendarService) {
	var calendar = calendar;
	$scope.services = services;
	$scope.newCourse = {
		cid: calendar.cid,
		cdate: calendar.cdate,
		sid: null,
		maxlimit: 0,
		stype: 1,
		intro: ""
	}
	$scope.ismeridian = false;
	$scope.hstep = 1;
  	$scope.mstep = 15;
  	$scope.mytime = new Date();
	$scope.ok = function () {
		$scope.newCourse.cdate = calendar.cdate * 10000 + $scope.mytime.getHours() * 100 + $scope.mytime.getMinutes();
		$scope.newCourse.sid = $scope.newCourse.sid ? $scope.newCourse.sid.sid : null;
		$scope.newCourse.stype = parseInt($scope.newCourse.stype);
		$modalInstance.close($scope.newCourse);
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

MisApp.controller('MembersModalInstanceCtrl', function ($scope, $modalInstance, candidate, member) {
	$scope.options = candidate;
	$scope.members = member;
	$scope.added = {};
	$scope.removed = {};

	for (var index in $scope.options) {
		$scope.added[$scope.options[index].uid] = false;
	}
	for (var index in $scope.members) {
		$scope.removed[$scope.members[index].uid] = false;
	}

	$scope.ok = function () {
		var assign = _.filter($scope.options, function(datum) { return $scope.added[datum.uid]; });
		var unassign = _.filter($scope.members, function(datum) { return $scope.removed[datum.uid]; });
		assign = _.map(assign, function(datum) { return datum.uid; });
		unassign = _.map(unassign, function(datum) { return datum.uid; });
		$modalInstance.close({assign: assign, unassign: unassign});
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});

MisApp.controller('StateCtrl', function($stateParams, PlanService, customtable, UserService, $q, ServiceService, TrainService, ActionService, CalendarService) {
	// $scope.state = $state;
	$scope.switchTab = function(nextTab) {
		// $state.go('main', {active: nextTab});
		// return;
		getTableData(nextTab);
		$scope.active = nextTab;
		$scope.tableVisible = (nextTab !== "courses" && nextTab!== "messages");
		$scope.chatboxVisible = (nextTab === "messages");
		$scope.calendarVisible = (nextTab === "courses");
		$scope.tableData = tableData;
	};
	var token = UserService.getToken();
	var deferred = $q.defer();
	function getTableData (active) {
		switch(active) {
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
	
});