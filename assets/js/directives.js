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
			$scope.alert = {msg: ''};
			$scope.isLogining = false;
			$scope.login = function() {
				if (!$scope.isLogining) {
					$scope.isLogining = true;
					var data = customtable.getBodyFromResponse(customtable.fixtures.coach_info);
				    UserService.auth($scope.loginObj).then(function (data) {
				    	$scope.isLogining = false;
				    	$state.go('main', {active: 'home', userinfo: data, tableVisible: true});
				    }, function(error) {
				    	$scope.alert = {
				    		msg: "参数输入异常！" ? "用户名或密码有误" : error,
				    		type: 'danger'
				    	}
				    	$scope.isLogining = false;
	            	})
				}
				else {
					$scope.alert = {
						msg: "正在登陆，请耐心等待",
						type: 'success'
					}
				}
			};
			$scope.onTextClick = function($event) {
				$event.target.select();
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

MisApp.directive('fileModel', ['$parse', '$base64', function ($parse, $base64) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
     		
			function parseMedia (file) {
				var type = file.type.match(/(\w*)\/(\w*)/);
				var postfix = type[2];
				type = type[1] === 'image' ? 0 : 1;
				return {
					file: $base64.encode(file),
					type: type,
					postfix: postfix
				}
			};
            element.bind('change', function(){
                scope.$apply(function(){
                	var reader  = new FileReader();
				  	var file    = element[0].files[0];
				  	var reader  = new FileReader();
				  	var info = {
				  		file: null,
				  		url: null,
				  		postfix: null,
				  		type: null
				  	}

				  	reader.onloadend = function () {
				    	var t = parseMedia(file);
				    	info.file = t.file;
				    	info.url = reader.result;
				    	info.postfix = t.postfix;
				    	info.type = t.type;
				    	modelSetter(scope, info);
				  	}

				  	if (file) {
				    	reader.readAsDataURL(file);
				  	} else {
				    	preview.src = "";
				  	}
                    
                });
            });
        }
    };
}]);


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
