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

MisApp.controller('ProfileCtrl', function ($scope, $modal) {
	$scope.userinfo = $scope.$parent.userinfo.user_info;
	$scope.userinfo['intro'] = $scope.$parent.userinfo.coach_info.intro;
	$scope.userinfo['exp'] = $scope.$parent.userinfo.coach_info.exp;
	$scope.userinfo['ads'] = $scope.$parent.userinfo.coach_ad;
	$scope.myInterval = 5000;
	var slides = $scope.slides = [];
	$scope.addSlide = function() {
		slides.push({
			image: $scope.userinfo.ads[slides.length].url,
			text: ['More','Extra','Lots of','Surplus'][slides.length % 4] + ' ' +
			['Cats', 'Kittys', 'Felines', 'Cutes'][slides.length % 4]
			});
	};
	for (var i=0; i<$scope.$parent.userinfo.coach_ad.length; i++) {
		$scope.addSlide();
	}
	$scope.editProfile = function() {
		var modalInstance = $modal.open({
			animation: true,
			templateUrl: 'assets/templates/profilemodal.html',
			controller: 'ProfileModalInstanceCtrl',
			resolve: {
				userinfo: function() {
					return $scope.userinfo;
				}
			}
		});

		modalInstance.result.then(function (updatedUser) {
			
		});
	}
});

MisApp.controller('ProfileModalInstanceCtrl', function ($scope, $modalInstance, userinfo) {
	$scope.userinfo = Object.create(userinfo);
	$scope.alert = {
		msg: ''
	}
	$scope.icon = {
		url: userinfo.icon,
		file: null,
		postfix: null
	};
	$scope.ads = _.map(userinfo.ads, function(ad) {
		return {
			url: ad.url,
			file: null,
			postfix: null
		};
	});
	var birthday = $scope.userinfo.birthday;
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
		var params = {
			birthday: birthday,

		}
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

MisApp.controller('UploadModalInstanceCtrl', function ($scope, $modalInstance, action, editable, title) {
	$scope.action= action;
	$scope.editable = editable;
	$scope.title = title;
	$scope.pic = {
		url: $scope.action.pic,
		file: null,
		type: null,
		postfix: null
	}
	$scope.media = {
		url: $scope.action.url,
		file: null,
		type: $scope.action.category,
		postfix: null
	}
	$scope.alert = {
		msg: "",
		type: 'danger'
	};
	$scope.ok = function () {
		if (!editable) {
			var params = {
				name: $scope.action.name,
				intro: $scope.action.intro,
				quantiry: $scope.action.quantiry,
				units: $scope.action.units,
				duration: $scope.action.duration
			}
			if ($scope.pic.url && $scope.media.url) {
				for (var i in params) {
					if (!params[i]) {
						$scope.alert.msg = "不能为空";
						return;
					}
				}
			}
			else {
				$scope.alert.msg = "不能为空";
				return;
			}
			params.pic = $scope.pic.file;
			params.picType = $scope.pic.postfix;
			params.media = $scope.media.file;
			params.mediaType = $scope.media.postfix;
			params.category = $scope.media.type;
			if ($scope.action.id !== undefined) {
				params.id = $scope.action.id;
			}
			console.log("hint",params);
			$modalInstance.close(params);
		}
		else {
			$modalInstance.dismiss('cancel');
		}
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});