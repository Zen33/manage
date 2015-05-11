var MisApp = angular.module('misapp');
MisApp.controller('OptionsModalInstanceCtrl', function ($scope, $modalInstance, options, title) {

	$scope.options = options;
	$scope.title = title;
	$scope.optionsChecked = {};
	$scope.rank = 1

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
			if (title === "训练模板") {
				var params = {
					spid: id,
					rank: $scope.rank
				}
				$modalInstance.close(params);
			}
			else {
				$modalInstance.close(id);
			}
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

MisApp.controller('ProfileModalInstanceCtrl', function ($scope, $modalInstance, uesrinfo) {
	$scope.userinfo = Object.create(userinfo);
	$scope.alert = {
		msg: ''
	}
	$scope.ad = {};
	var birthday = $scope.userinfo.birthday;
	var icon = $scope.userinfo.icon;
	$scope.uploadAds = function() {
		console.log($scope.ad)
	}
	$scope.handleBirthday = function() {
		$scope.alert = {
			msg: ''
		}
		birthday = $scope.userinfo.birthday.match(/(\d\d\d\d)-(\d\d)-(\d\d)/);
		birthday.shift();
		birthday = parseInt(birthday.join(''));
	}
	$scope.save = function() {
		$scope.alert = {
			msg: ''
		}
		var token = UserService.getToken();
		$scope.userinfo.birthday = birthday;
		var params = $scope.userinfo;
		params.token = token;
		UserService.patchCoach(params).then(function(data) {
			var userinfo = data.user_info;
			userinfo['intro'] = data.coach_info.intro;
			userinfo['exp'] = data.coach_info.exp;
			userinfo['ads'] = data.coach_ad;
			$modalInstance.close($scope.userinfo);
		}, function(err) {
			$scope.alert = {
				msg: err,
				type: 'danger'
			}
		})
	};
});

MisApp.controller('UploadModalInstanceCtrl', function ($scope, $modalInstance, action, editable) {
	$scope.action= action;
	$scope.alert = {
		msg: "",
		type: 'danger'
	}
	function parseMedia (file) {
		var type = file.type.match(/(\w*)\/(\w*)/);
		var postfix = type[2];
		type = type[1] === 'image' ? 0 : 1;
		return {
			file: file,
			type: type,
			postfix: postfix
		}
	};
	$scope.ok = function () {
		if ($scope.action.pic) {
			if (typeof $scope.action.pic === 'string') {
				$scope.action.pic = null;
				$scope.action.picType = null;
			}
			else {
				var file = parseMedia($scope.action.pic);
				$scope.action.pic = file.file;
				$scope.action.picType = file.postfix;
			}
		}
		else {
			$scope.alert.msg = "请上传一张此动作缩略图";
			return;
		}
		if ($scope.action.url) {
			if (typeof $scope.action.url === 'string') {
				$scope.action.media = null;
				$scope.action.mediaType = null;
				delete $scope.action.url;
				$modalInstance.close($scope.action);
			}
			else {
				var file = parseMedia($scope.action.url);
				$scope.action.media = file.file;
				$scope.action.mediaType = file.postfix;
				$scope.action.category = file.type;
				delete $scope.action.url;
				$modalInstance.close($scope.action);
			}
		}
		else {
			$scope.action.media = null;
			$scope.action.mediaType = null;
			delete $scope.action.url;
			$modalInstance.close($scope.action);
		}
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});