var MisApp = angular.module('misapp');

MisApp.directive('comfirm', function($state, UserService, customtable) {
	return {
		templateUrl: 'assets/templates/comfirm.html',
		restrict: 'E',
		controller: function($scope, $http) {
			$scope.loginObj = {
				tel: "",
				pw: ""
			};
			$scope.login = function() {
				    // UserService.auth($scope.loginObj).then(function (data) {
				    // 	$state.go('main', {active: 'users', userinfo: data, tableVisible: true});
				    // }, function(error) {
	                    var data = customtable.fixtures.coach_info;
	                    $state.go('main', {active: 'users', userinfo: data});
	            	// })
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

MisApp.directive('nav', function($state) {
	return {
		templateUrl: 'assets/templates/nav.html',
		restrict: 'E',
		scope: {
			active: '='
		},
		link: function(scope, elem, attrs) {
			scope.active = scope.$parent.active;
			scope.switchTab = function(nextTab) {
				$state.go('main', {active: nextTab}); //second parameter is for $stateParams
				scope.active = nextTab
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

MisApp.directive('optionsmodal', function($state, PlanService, UserService) {
	return {
		templateUrl: 'assets/templates/optionsmodal.html',
		scope: {
			modaldata: '=',
			userid: '='
		},
		link: function(scope, elem, attrs) {
			var userid = "";
			var token = UserService.getToken();
			scope.optionsChecked = {};
			scope.$watch('modaldata', function(value) {
				scope.gridOptions2 = value;
				for (var index in value) {
					scope.optionsChecked[value[index].pid] = false;
				}
			});
			scope.$watch('userid', function(value) {
				userid = value;
			})
			scope.onChange = function(id) {
				for (var index in scope.gridOptions2) {
					if (scope.gridOptions2[index].pid === id) {
						scope.optionsChecked[id] = true;
					}
					else {
						scope.optionsChecked[scope.gridOptions2[index].pid] = false;
					}
				}
			},
			scope.save = function() {
				var pid = _.find(scope.optionsChecked, function(value) { return value; });
				if (pid) {
					var params = {
						token: token,
						pid: pid,
						uid: userid
					};
					// PlanService.postUserPlan(params).then(function(data) {
						$(elem).parent().parent().parent().parent().modal('hide');
					// }) 
				}
				else {
					$(elem).parent().parent().parent().parent().modal('hide');
				}
			}
		}
	}
});

// MisApp.directive('calendar', function($state) {
// 	return {
// 		templateUrl: 'assets/templates/calendar.html',
// 		restrict: 'E',
// 		controller: function($scope, $http) {
// 			$scope.eventSources = [];
// 			$scope.uiConfig = {
//       calendar:{
//         height: 450,
//         editable: true,
//         header:{
//           left: 'month basicWeek basicDay agendaWeek agendaDay',
//           center: 'title',
//           right: 'today prev,next'
//         },
//         dayClick: $scope.alertEventOnClick,
//         eventDrop: $scope.alertOnDrop,
//         eventResize: $scope.alertOnResize
//       }
//     };
// 		}
// 	}
// })

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
