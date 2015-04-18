var MisApp = angular.module('misapp');

MisApp.directive('comfirm', function($state) {
	return {
		templateUrl: 'assets/templates/comfirm.html',
		restrict: 'E',
		controller: function($scope, $http) {
			$scope.loginObj = {
				username: "",
				password: "",
				rememberMe: true
			};
			$scope.login = function() {
				// $http.post('/someUrl', $scope.loginObj).
			  	// success(function(data, status, headers, config) {
				    // this callback will be called asynchronously
				    // when the response is available
				    var data = {
				    	user_name: "黄舒宁",
				    	user_avator: "assets/imgs/muscle.jpeg",
				    	user_specialty: "特短",
				    	user_desc: "详细介绍什么的，谁会写啊"
				    }
				    $state.go('main', {active: 'home', userinfo: data, tableVisible: true});
			  	// }).
			  	// error(function(data, status, headers, config) {
				    // called asynchronously if an error occurs
				    // or server returns response with an error status.
			  	// });
			};
			$scope.onTextClick = function($event) {
				$event.target.select();
			}
		}
	}
});

MisApp.directive('customtable', function($compile) {
	return {
		templateUrl: 'assets/templates/customtable.html',
		restrict: 'E',
		link: function(scope, elm, attrs) {
			scope.gridOptions = {
				enableRowSelection: true,
    			enableSelectAll: false,
    			enableCellEditOnFocus: true,
    			useExternalPagination: true,
    			paginationPageSizes: [50, 100, 200],
    			paginationPageSize: 20
			};
			scope.$watch(attrs.customData, function(value) {
				scope.gridOptions.columnDefs = value.columnDefs;
		    });


			// $http.get('/data/500_complex.json')
			// .success(function(data) {
			//   $scope.gridOptions.data = data;
			// });

			scope.currentFocused = "";

			scope.getCurrentFocus = function(){
				var rowCol = scope.gridApi.cellNav.getFocusedCell();
				if(rowCol !== null) {
				  	scope.currentFocused = 'Row Id:' + rowCol.row.entity.id + ' col:' + rowCol.col.colDef.name;
				}
			}

			scope.gridOptions.onRegisterApi = function(gridApi) {
				scope.gridApi = gridApi;
				scope.gridApi.pagination.on.paginationChanged(scope, function( currentPage, pageSize){
					scope.getPage(currentPage, pageSize);
				});
    		}

    		scope.clearAll = function() {
				scope.gridApi.selection.clearSelectedRows();
			}

			scope.rmData = function() {
				var selections = scope.gridApi.selection.getSelectedRows();
				selections.filter(function(s) { return s; });
				// $http.put('', selections);
				var currentPage = scope.gridApi.pagination.getPage();
				scope.getPage(currentPage, 20);
			}

			scope.getPage = function(pageNumber, pageSize){
				var startingRow = pageSize * ( pageNumber - 1);   // page number starts at 1, not zero
				var newData = [];
				scope.$watch(attrs.customData, function(value) {
					var data = value.data;
					for( var i = startingRow; i < startingRow + scope.gridOptions.paginationPageSize; i++ ) {
						newData.push( data[i] );
					}
					scope.gridOptions.data = newData;
					scope.gridOptions.totalItems = data.length;
			    });
				
			}

			scope.getPage(1, 20);

			scope.addData = function() {
				var columnDefs = scope.gridOptions.columnDefs;
				var newRow = {};
				for (var i in columnDefs) {
					newRow[columnDefs[i].name] = '';
				}
				// $http.post('', newRow);
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
		link: function(scope, elem, attrs) {
			scope.userinfo = scope.$parent.userinfo;
		},
		controller: function($scope) {
			$scope.myInterval = 5000;
			$scope.showModal = false;
			var slides = $scope.slides = [];
			$scope.addSlide = function() {
				var newWidth = 600 + slides.length + 1;
				slides.push({
					image: 'http://placekitten.com/' + newWidth + '/300',
					text: ['More','Extra','Lots of','Surplus'][slides.length % 4] + ' ' +
					['Cats', 'Kittys', 'Felines', 'Cutes'][slides.length % 4]
					});
			};
			for (var i=0; i<4; i++) {
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
				$state.go('main', {active: nextTab, tableVisible: nextTab === "messages" ? false : true}); //second parameter is for $stateParams
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
			scope.title = attrs.title;
			scope.$watch(attrs.visible, function(value){
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
			
			$scope.loadMessages = function() {
				var params = {
					// params you send to server to get messages list
				} 
				MessageService.getMessages(params).then(function(messages) {
					$scope.messages = messages;
				}, function(error) {
					// went wrong, show user info
				})
			}
			$scope.send = function() {
				var params = {
					content: $scope.message,
					timestamp: "",
					to: "",
					from: ""
				} // params you gonna send to server and create a new message
				$http.post('/someUrl', params).
					success(function(data, status, headers, config) {
						// return;
						// append new message into messages list
					}).
					error(function(data, status, headers, config) {
						// show error info to users
  					});
			}
		}
	}
});
