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

MisApp.controller('MembersModalInstanceCtrl', function ($scope, $modalInstance, members) {
	$scope.options = members.candidate;
	$scope.members = members.member;
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