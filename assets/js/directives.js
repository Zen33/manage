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
	                    $state.go('main', {active: 'home', userinfo: data});
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

MisApp.directive('calendar', function($state) {
	return {
		templateUrl: 'assets/templates/calendar.html',
		restrict: 'E',
		controller: function($scope, $http) {
			var date = new Date();
    var d = date.getDate();
    var m = date.getMonth();
    var y = date.getFullYear();
    
    $scope.changeTo = 'Hungarian';
    /* event source that pulls from google.com */
    $scope.eventSource = {
            url: "http://www.google.com/calendar/feeds/usa__en%40holiday.calendar.google.com/public/basic",
            className: 'gcal-event',           // an option!
            currentTimezone: 'America/Chicago' // an option!
    };
    /* event source that contains custom events on the scope */
    $scope.events = [
      {title: 'All Day Event',start: new Date(y, m, 1)},
      {title: 'Long Event',start: new Date(y, m, d - 5),end: new Date(y, m, d - 2)},
      {id: 999,title: 'Repeating Event',start: new Date(y, m, d - 3, 16, 0),allDay: false},
      {id: 999,title: 'Repeating Event',start: new Date(y, m, d + 4, 16, 0),allDay: false},
      {title: 'Birthday Party',start: new Date(y, m, d + 1, 19, 0),end: new Date(y, m, d + 1, 22, 30),allDay: false},
      {title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
    ];
    /* event source that calls a function on every view switch */
    $scope.eventsF = function (start, end, timezone, callback) {
      var s = new Date(start).getTime() / 1000;
      var e = new Date(end).getTime() / 1000;
      var m = new Date(start).getMonth();
      var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
      callback(events);
    };

    $scope.calEventsExt = {
       color: '#f00',
       textColor: 'yellow',
       events: [ 
          {type:'party',title: 'Lunch',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
          {type:'party',title: 'Lunch 2',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
          {type:'party',title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
        ]
    };
    /* alert on eventClick */
    $scope.alertOnEventClick = function( date, jsEvent, view){
        $scope.alertMessage = (date.title + ' was clicked ');
    };
    /* alert on Drop */
     $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
       $scope.alertMessage = ('Event Droped to make dayDelta ' + delta);
    };
    /* alert on Resize */
    $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
       $scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
    };
    /* add and removes an event source of choice */
    $scope.addRemoveEventSource = function(sources,source) {
      var canAdd = 0;
      angular.forEach(sources,function(value, key){
        if(sources[key] === source){
          sources.splice(key,1);
          canAdd = 1;
        }
      });
      if(canAdd === 0){
        sources.push(source);
      }
    };
    /* add custom event*/
    $scope.addEvent = function() {
      $scope.events.push({
        title: 'Open Sesame',
        start: new Date(y, m, 28),
        end: new Date(y, m, 29),
        className: ['openSesame']
      });
    };
    /* remove event */
    $scope.remove = function(index) {
      $scope.events.splice(index,1);
    };
    /* Change View */
    $scope.changeView = function(view,calendar) {
      uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
    };
    /* Change View */
    $scope.renderCalender = function(calendar) {
      if(uiCalendarConfig.calendars[calendar]){
        uiCalendarConfig.calendars[calendar].fullCalendar('render');
      }
    };
     /* Render Tooltip */
    $scope.eventRender = function( event, element, view ) { 
        element.attr({'tooltip': event.title,
                     'tooltip-append-to-body': true});
        $compile(element)($scope);
    };
    /* config object */
    $scope.uiConfig = {
      calendar:{
        height: 450,
        editable: true,
        header:{
          left: 'title',
          center: '',
          right: 'today prev,next'
        },
        eventClick: $scope.alertOnEventClick,
        eventDrop: $scope.alertOnDrop,
        eventResize: $scope.alertOnResize,
        eventRender: $scope.eventRender
      }
    };

    $scope.changeLang = function() {
      if($scope.changeTo === 'Hungarian'){
        $scope.uiConfig.calendar.dayNames = ["Vasárnap", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"];
        $scope.uiConfig.calendar.dayNamesShort = ["Vas", "Hét", "Kedd", "Sze", "Csüt", "Pén", "Szo"];
        $scope.changeTo= 'English';
      } else {
        $scope.uiConfig.calendar.dayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        $scope.uiConfig.calendar.dayNamesShort = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
        $scope.changeTo = 'Hungarian';
      }
    };
    /* event sources array*/
    $scope.eventSources = [$scope.events, $scope.eventSource, $scope.eventsF];
    $scope.eventSources2 = [$scope.calEventsExt, $scope.eventsF, $scope.events];
		}
	}
})

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
