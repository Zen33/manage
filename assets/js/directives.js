var MisApp = angular.module('misapp');

MisApp.directive('comfirm', function($state, UserService, customtable) {
	return {
		templateUrl: 'assets/templates/comfirm.html',
		restrict: 'E',
		controller: function($scope, $http) {
			$scope.loginObj = {
				tel: "18012345678",
				pw: "123456"
			};
			$scope.login = function() {
					var data = customtable.getBodyFromResponse(customtable.fixtures.coach_info);
				    UserService.auth($scope.loginObj).then(function (data) {
				    	$state.go('main', {active: 'users', userinfo: data, tableVisible: true});
				    }, function(error) {

	            	})
			};
			$scope.onTextClick = function($event) {
				$event.target.select();
			}
		}
	}
});

MisApp.directive('profile', function($state) {
	return {
		templateUrl: 'assets/templates/profile.html',
		restrict: 'E',
		scope: {
			userinfo: '='
		},
		controller: function($scope) {
			$scope.userinfo = $scope.$parent.userinfo.user_info;
			$scope.userinfo['intro'] = $scope.$parent.userinfo.coach_info.intro;
			$scope.userinfo['exp'] = $scope.$parent.userinfo.coach_info.exp;
			$scope.userinfo['ads'] = $scope.$parent.userinfo.coach_ad;
			$scope.myInterval = 5000;
			$scope.showModal = false;
			$scope.modaltitle = "编辑教练信息";
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
				$scope.showModal = true;
			}
		}
	}
});

MisApp.directive('nav', function($state, PlanService, customtable, UserService, $q, ServiceService, TrainService, ActionService, CalendarService) {
	return {
		templateUrl: 'assets/templates/nav.html',
		restrict: 'E',
		scope: {
			active: '='
		},
		link: function(scope, elem, attrs) {
			scope.active = scope.$parent.active;
		},
		controller: function($scope) {
			$scope.switchTab = function(nextTab) {
				$scope.active = nextTab;
			};
		}
	}
});

MisApp.directive('modal', function($state) {
	return {
		templateUrl: 'assets/templates/modal.html',
		restrict: 'E',
		transclude: true,
		replace:true,
		scope:true,
		link: function(scope, element, attrs) {
			scope.$watch(attrs.modaltitle, function(value) {
				scope.title = value;
			});
			scope.$watch(attrs.visible, function(value) {
				value ? $(element).modal('show') : $(element).modal('hide');
			});
			$(element).on('shown.bs.modal', function(){
				scope.$apply(function(){
					scope.$parent[attrs.visible] = true;
				});
			});

			$(element).on('hidden.bs.modal', function(){
				scope.$apply(function(){
					scope.$parent[attrs.visible] = false;
				});
			});
		}
	}
});

MisApp.directive('profilemodal', function($state) {
	return {
		templateUrl: 'assets/templates/profilemodal.html',
		restrict: 'E',
		scope: {
			userinfo: '='
		},
		link: function(scope, elem, attrs) {
			scope.userinfo = Object.create(scope.$parent.userinfo);
			scope.save = function() {
				$(elem).parent().parent().parent().parent().modal('hide')
			};
		}
	}
});

MisApp.directive('chatbox', function($state, $q, MessageService) {
	return {
		templateUrl: 'assets/templates/chatbox.html',
		restrict: 'E',
		controller: function($scope, $http) {
			$scope.to = "all_users";
			$scope.message = "";
			$scope.messages = [];
			$scope.loading = true;
			
			$scope.loadMessages = function() {
				var params = {
					// params you send to server to get messages list
				} 
				MessageService.getMessages(params).then(function(messages) {
					$scope.messages = messages;
					$scope.loading = false;
				}, function(error) {
					// went wrong, show user info
				})
			};
			$scope.postMessage = function() {
				var params = {
					// params you gonna send to server and create a new message
				} 
				MessageService.postMessage(params).then(function(message) {
					$scope.messages.push(message);
				}, function(error) {
					// went wrong, show user info
				})
			};
			$scope.deleteMessage = function() {
				var params = {
					// params you need to send delete
				}
				MessageService.deleteMessage(params).then(function(resp) {
					$scope.loadMessages();
				}, function(error) {
					// went wrong, show user info 
				})
			}
		}
	}
});
