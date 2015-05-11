var MisApp = angular.module('misapp');

MisApp.directive('calendar', function(customtable, CalendarService, UserService, $q) {
    return {
        templateUrl: 'assets/templates/calendar.html',
        restrict: 'E',
        controller: function($scope, $modal) {
        	var token = UserService.getToken();
            $scope.isLoading = true;
            $scope.tableData = [];
            var today = new Date();
            $scope.today = today.getFullYear() * 10000 + (today.getMonth() + 1) * 100 + today.getDate();
            $scope.nextP = function() {
                // raw = customtable.getBodyFromResponse(customtable.fixtures.next_course_schedules).calendar;
                // $scope.rows = customtable.formatCalendar(raw);
                CalendarService.getCalendar({token: token, page: 1}).then(function(data) {
            		var raw = data;
            		$scope.rows = customtable.formatCalendar(raw);
                    $scope.isLoading = false;
            	});
            };
            $scope.prevP = function() {
                // raw = customtable.getBodyFromResponse(customtable.fixtures.last_course_schedules).calendar;
                // $scope.rows = customtable.formatCalendar(raw);
                CalendarService.getCalendar({token: token, page: -1}).then(function(data) {
            		var raw = data;
            		$scope.rows = customtable.formatCalendar(raw);
                    $scope.isLoading = false;
            	});
            };
            $scope.currentP = function() {
            	CalendarService.getCalendar({token: token, page: 0}).then(function(data) {
            		var raw = data;
            		$scope.rows = customtable.formatCalendar(raw);
                    $scope.isLoading = false;
            	});
                // customtable.getBodyFromResponse(customtable.fixtures.current_course_schedules).calendar;
            };
            $scope.currentP();
            $scope.viewDetail = function(day) {
                if (day.num === 0) {
                    $scope.alert = {
			    		msg: ""
			    	};
					var modalInstance = $modal.open({
						animation: true,
						templateUrl: 'assets/templates/coursemodal.html',
						controller: 'CourseModalInstanceCtrl',
						resolve: {
							calendar: function() {
								return day;
							},
							services: function() {
								return CalendarService.getCourses({token: token, cid: day.cid}).then(function(data) {
									return data.service;
								})
							}
						}
					});

					modalInstance.result.then(function (newCourse) {
						newCourse.token = token;
						CalendarService.postCourse(newCourse).then(function(data) {
							$scope.currentP();
						}, function (err) {
							$scope.alert = {
								msg: err,
								type: 'danger'
							}
						});
					});
                }
                else {
                	return CalendarService.getCourses({token: token, cid: day.cid}).then(function(data) {
	                    	$scope.active = "calendar";
	                    	$scope.visible = true;
                            data.calendarId = day.cid;
                            data.cdate = day.cdate;
                            $scope.tableData = data;
                	})
                }
            }
        }
    }
});