angular.module('misapp')
.value('customtable', {
	defaultColumnDefs: function(dataType) {
		switch(dataType) {
		case "projects": 
			return [
				{ field: 'icon', displayName: '计划标志', enableCellEdit: false, cellTemplate: '<img src="{{row.entity.icon}}" width="30px" height="30px"/>'},
				{ field: 'pid', displayName: 'id', enableCellEdit: false},
				{ field: 'name', displayName: '计划名称'},
				{ field: 'intro', displayName: '计划介绍'},
				{ field: 'duration', displayName: '时长'},
				{ name: 'plan', displayName: '查看计划', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.viewPlan(row.entity)" >查看计划</button> '},
				{name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '}
			];
		case "users": 
			return [
				{ field: 'icon', displayName: '', enableCellEdit: false, cellTemplate: '<img src="{{row.entity.icon}}" width="30px" height="30px"/>'},
				{ field: 'uid', displayName: 'id', enableCellEdit: false},
				{ field: 'username', displayName: '名字', type: 'string', enableCellEdit: false},
				{ field: 'sex', displayName: '性别', editDropdownValueLabel: 'sex', editableCellTemplate: 'ui-grid/dropdownEditor', editDropdownOptionsArray: [
			      { id: 1, sex: '男' },
			      { id: 2, sex: '女' }
			    ], enableCellEdit: false},
			     // cellTemplate: '<input id="editBtn" type="file" class="btn-small" ng-change="grid.addScope.storeMedia"></input> '
				{ field: 'brithday', displayName: '生日', type: 'date', enableCellEdit: false},
				{ field: 'city', displayName: '城市', type: 'string', enableCellEdit: false},
				{ field: 'height', displayName: '身高', enableCellEdit: false},
				{ field: 'weight', displayName: '体重', enableCellEdit: false},
				{ name: 'plan', displayName: '查看计划', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.viewPlan(row.entity)" >查看计划</button> '},
				{ name: 'service', displayName: '查看服务', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.viewService(row.entity)" >查看服务</button> '}
			];
		case "trainings":
			return [
				{ field: 'id', displayName: 'id', enableCellEdit: false},
				{ field: 'name', displayName: '训练名称'},
				{ field: 'intro', displayName: '训练介绍'},
				{ field: 'duration', displayName: '时长'},
				{name: 'training', displayName: '训练', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.viewTraining(row.entity)" >查看动作</button> '}
			];
		case "actions":
			return [
				{ field: 'id', displayName: 'id', enableCellEdit: false},
				{ field: 'name', displayName: '动作名称'},
				{ field: 'intro', displayName: '动作介绍'},
				{ field: 'duration', displayName: '时长'},
				{ field: 'quantity', displayName: '数量', type: 'number'},
				{ field: 'units', displayName: '单位', type: 'string'}
			];
		case "home":
			return [
				{ field: 'id', displayName: 'id', enableCellEdit: false},
				{ field: 'name', displayName: '训练名称'},
				{ field: 'intro', displayName: '训练介绍'},
				{ field: 'duration', displayName: '时长'},
				{ field: 'url', displayName: '实例', type: 'file'},
				{ field: 'quantity', displayName: '数量', type: 'number'},
				{ field: 'units', displayName: '单位', type: 'string'}
			];
		case "services":
			return [
				{ field: 'sid', displayName: 'id', enableCellEdit: false},
				{ field: 'name', displayName: '服务名称'},
				{ field: 'intro', displayName: '服务介绍'},
				{ field: 'cost', displayName: '费用'},
				{ field: 'month', displayName: '有效时间/月', type: 'number'},
				{ field: 'online', displayName: '视频服务次数', type: 'number'},
				{ field: 'offline', displayName: '线下指导次数', type: 'number'},
				{ field: 'online_times', displayName: '视频时长', type: 'number'},
				{ field: 'offline_times', displayName: '线下时长', type: 'number'},
			];
		case "calendar":
			return [
				{ field: 'calendar.id', displayName: 'id', enableCellEdit: false},
				{ field: 'calendar.cdate', displayName: '教学实践'},
				{ field: 'calendar.minutes', displayName: '授课时长', type: 'number'},
				{ field: 'calendar.maxlimit', displayName: '人数上限', type: 'number'},
				{ field: 'calendar.sign', displayName: '报名人数', type: 'number'},
				{ field: 'calendar.intro', displayName: '课程简介', type: 'number'},
				{ field: 'service.name', displayName: '服务'},
				{ field: 'calendar.members.member', displayName: '参与者', cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.handleMembers(row.entity)" >查看参与者</button>'},
				{name: 'delete', displayName: '删除', enableCellEdit: false, cellTemplate: '<button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.rmData(row.entity)" >删除</button> '}
			];
		}
	},
	getBodyFromResponse: function(resp) {
		delete resp.status;
		return resp;
	},
	getId: function(row) {
		return _.find(row, function(value, key) { return key.indexOf('id') !== -1; });
	},
	formatCalendar: function(calendar) {
		var rows = [];
		var row = [];
		for (var index in calendar) {
			if (parseInt(index) % 7 != 0 || parseInt(index) == 0) {
				row.push(calendar[index])
			}
			else {
				rows.push(row);
				row = [];
				row.push(calendar[index]);
			}
		}
		rows.push(row)
		return rows;
	},
	getTableData: function() {
		var token = UserService.getToken();
		var deferred = $q.defer();
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
	},
	fixtures: {
		coach_info: {
	        "user_info": {
	            "sex": "1",
	            "username": "看",
	            "icon": "http://112.124.52.165:8080/resources/HeaderPicture/13723922.jpg",
	            "birthday": 20100606,
	            "height": 0,
	            "weight": 0,
	            "uid": "17088099",
	            "firstLetter": "K"
	        },
	        "state": {
	            "comment": 0,
	            "reply": 0,
	            "uid": "17088099"
	        },
	        "token": "5DCC37937437E7078EAA7C5627D5A620",
	        "coach_info": {
	            "cid": "17088099",
	            "circle": 59098,
	            "exp": 0,
	            "fav": 0,
	            "intro": "牛逼的教练",
	            "reply": 1
	        },
	        "coach_ad": [{
	            "cid": "17088099",
	            "url": "http://112.124.52.165:8080/resources/AD/ad.jpg"
	        }, {
	            "cid": "17088099",
	            "url": "http://112.124.52.165:8080/resources/AD/js.jpg"
	        }, {
	            "cid": "17088099",
	            "url": "http://112.124.52.165:8080/resources/AD/test.png"
	        }]
		},
		public_projects: {
		    "plan": [{
		        "cid": "17088099",
		        "duration": 30,
		        "icon": "",
		        "intro": "good",
		        "name": "模版1",
		        "pid": 1
		    }, {
		        "cid": "17088099",
		        "duration": 20,
		        "icon": "",
		        "intro": "力量",
		        "name": "力量",
		        "pid": 2
		    }],
		    "status": 200
		},
		project_models: {
		    "plan": [{
		        "cid": "17088099",
		        "duration": 30,
		        "icon": "http://112.124.52.165:8080/resources/AD/ad.jpg",
		        "intro": "good",
		        "inuse": 1,
		        "name": "模版1",
		        "pid": 1
		    }, {
		        "cid": "17088099",
		        "duration": 30,
		        "icon": "",
		        "intro": "nice",
		        "inuse": 1,
		        "name": "模版2",
		        "pid": 2
		    }],
		    "status": 200
		},
		user_trainings: {
			subplan: [{
		        "id": 5,
		        "duration": 34,
		        "name": "sub1",
		        "intro": "sub1",
		        "rank": 1,
		        "rid": 5,
		        "done": 1
		    }, {
		        "id": 6,
		        "duration": 35,
		        "name": "sub2",
		        "intro": "sub2",
		        "rank": 2,
		        "rid": 6,
		        "done": 1
		    }, {
		        "id": 7,
		        "duration": 40,
		        "name": "sub3",
		        "intro": "sub3",
		        "rank": 3,
		        "rid": 7,
		        "done": 0
		    }, {
		        "id": 8,
		        "duration": 45,
		        "name": "sub4",
		        "intro": "sub4",
		        "rank": 4,
		        "rid": 8,
		        "done": 0
		    }, {
		        "id": 9,
		        "duration": 50,
		        "name": "sub5",
		        "intro": "sub5",
		        "rank": 5,
		        "rid": 9,
		        "done": 0
		    }, {
		        "id": 10,
		        "duration": 30,
		        "name": "sub6",
		        "intro": "bus6",
		        "rank": 6,
		        "rid": 10,
		        "done": 0
		    }, {
		        "id": 11,
		        "duration": 30,
		        "name": "sub7",
		        "intro": "bus7",
		        "rank": 7,
		        "rid": 11,
		        "done": 0
		    }, {
		        "id": 12,
		        "duration": 30,
		        "name": "sub8",
		        "intro": "sub8",
		        "rank": 8,
		        "rid": 12,
		        "done": 0
		    }],
			status: 200
		},
		training_models: {
		    "subplan": [{
		        "cid": "17088099",
		        "duration": 45,
		        "icon": "",
		        "id": 1,
		        "intro": "sub hehe",
		        "inuse": 0,
		        "name": "sub1",
		        "rank": 1,
		        "share": 1
		    }, {
		        "cid": "17088099",
		        "duration": 40,
		        "icon": "",
		        "id": 2,
		        "intro": "sub2 heh",
		        "inuse": 0,
		        "name": "sub2",
		        "rank": 2,
		        "share": 1
		    }, {
		        "cid": "17088099",
		        "duration": 30,
		        "icon": "",
		        "id": 3,
		        "intro": "sub3",
		        "inuse": 0,
		        "name": "sub3",
		        "rank": 3,
		        "share": 1
		    }, {
		        "cid": "17088099",
		        "duration": 20,
		        "icon": "",
		        "id": 4,
		        "intro": "sub4",
		        "inuse": 0,
		        "name": "sub4",
		        "rank": 4,
		        "share": 1
		    }],
		    "status": 200
		},
		users: {
		    "users": [{
		        "brithday": 20100606,
		        "category": 0,
		        "city": "",
		        "dist": "",
		        "height": 155,
		        "icon": "http://112.124.52.165:8080/resources/HeaderPicture/13301064_833.png",
		        "sex": "1",
		        "uid": "13301064",
		        "username": "No",
		        "weight": 58
		    }],
		    "status": 200
		},
		user_services: {
		    "services": [{
		        "cid": "17088099",
		        "cost": 1000,
		        "intro": "service1",
		        "inuse": 0,
		        "month": 6,
		        "name": "online service",
		        "num": 2,
		        "offline": 2,
		        "offline_times": 3,
		        "online": 4,
		        "online_times": 3,
		        "sid": 1
		    }],
		    "status": 200
		},
		coach_services: {
		    "services": [{
		        "cid": "17088099",
		        "cost": 1000,
		        "intro": "service1",
		        "inuse": 0,
		        "month": 6,
		        "name": "online service",
		        "num": 2,
		        "offline": 3,
		        "offline_times": 3,
		        "online": 4,
		        "online_times": 3,
		        "sid": 1
		    }, {
		        "cid": "17088099",
		        "cost": 1000,
		        "intro": "service2",
		        "inuse": 0,
		        "month": 6,
		        "name": "offline service",
		        "num": 0,
		        "offline": 2,
		        "offline_times": 3,
		        "online": 3,
		        "online_times": 3,
		        "sid": 2
		    }],
		    "status": 200
		},
		current_course_schedules: {
		    "calendar": [{
		        "cdate": 20150421,
		        "cid": 2,
		        "holiday": "",
		        "num": 0,
		        "week": 2
		    }, {
		        "cdate": 20150422,
		        "cid": 3,
		        "holiday": "",
		        "num": 0,
		        "week": 3
		    }, {
		        "cdate": 20150423,
		        "cid": 4,
		        "holiday": "",
		        "num": 0,
		        "week": 4
		    }, {
		        "cdate": 20150424,
		        "cid": 5,
		        "holiday": "",
		        "num": 2,
		        "week": 5
		    }, {
		        "cdate": 20150425,
		        "cid": 6,
		        "holiday": "",
		        "num": 0,
		        "week": 6
		    }, {
		        "cdate": 20150426,
		        "cid": 7,
		        "holiday": "",
		        "num": 0,
		        "week": 7
		    }, {
		        "cdate": 20150427,
		        "cid": 8,
		        "holiday": "",
		        "num": 0,
		        "week": 1
		    }, {
		        "cdate": 20150428,
		        "cid": 9,
		        "holiday": "",
		        "num": 0,
		        "week": 2
		    }, {
		        "cdate": 20150429,
		        "cid": 10,
		        "holiday": "",
		        "num": 0,
		        "week": 3
		    }, {
		        "cdate": 20150430,
		        "cid": 11,
		        "holiday": "",
		        "num": 0,
		        "week": 4
		    }, {
		        "cdate": 20150501,
		        "cid": 12,
		        "holiday": "劳动节",
		        "num": 0,
		        "week": 5
		    }, {
		        "cdate": 20150502,
		        "cid": 13,
		        "holiday": "",
		        "num": 0,
		        "week": 6
		    }, {
		        "cdate": 20150503,
		        "cid": 14,
		        "holiday": "",
		        "num": 0,
		        "week": 7
		    }, {
		        "cdate": 20150504,
		        "cid": 15,
		        "holiday": "青年节",
		        "num": 0,
		        "week": 1
		    }, {
		        "cdate": 20150505,
		        "cid": 16,
		        "holiday": "",
		        "num": 0,
		        "week": 2
		    }, {
		        "cdate": 20150506,
		        "cid": 17,
		        "holiday": "",
		        "num": 0,
		        "week": 3
		    }, {
		        "cdate": 20150507,
		        "cid": 18,
		        "holiday": "",
		        "num": 0,
		        "week": 4
		    }, {
		        "cdate": 20150508,
		        "cid": 19,
		        "holiday": "",
		        "num": 0,
		        "week": 5
		    }, {
		        "cdate": 20150509,
		        "cid": 20,
		        "holiday": "",
		        "num": 0,
		        "week": 6
		    }, {
		        "cdate": 20150510,
		        "cid": 21,
		        "holiday": "",
		        "num": 0,
		        "week": 7
		    }, {
		        "cdate": 20150511,
		        "cid": 22,
		        "holiday": "",
		        "num": 0,
		        "week": 1
		    }, {
		        "cdate": 20150512,
		        "cid": 23,
		        "holiday": "",
		        "num": 0,
		        "week": 2
		    }, {
		        "cdate": 20150513,
		        "cid": 24,
		        "holiday": "",
		        "num": 0,
		        "week": 3
		    }, {
		        "cdate": 20150514,
		        "cid": 25,
		        "holiday": "",
		        "num": 0,
		        "week": 4
		    }, {
		        "cdate": 20150515,
		        "cid": 26,
		        "holiday": "",
		        "num": 0,
		        "week": 5
		    }, {
		        "cdate": 20150516,
		        "cid": 27,
		        "holiday": "",
		        "num": 0,
		        "week": 6
		    }, {
		        "cdate": 20150517,
		        "cid": 28,
		        "holiday": "",
		        "num": 0,
		        "week": 7
		    }, {
		        "cdate": 20150518,
		        "cid": 29,
		        "holiday": "",
		        "num": 0,
		        "week": 1
		    }],
		    "status": 200
		},
		next_course_schedules: {
		    "calendar": [{
		        "cdate": 20150518,
		        "cid": 29,
		        "holiday": "",
		        "num": 0,
		        "week": 1
		    }, {
		        "cdate": 20150519,
		        "cid": 30,
		        "holiday": "",
		        "num": 0,
		        "week": 2
		    }, {
		        "cdate": 20150520,
		        "cid": 31,
		        "holiday": "",
		        "num": 0,
		        "week": 3
		    }],
		    "status": 200
		},
		last_course_schedules: {
		    "calendar": [{
		        "cdate": 20150420,
		        "cid": 1,
		        "holiday": "",
		        "num": 0,
		        "week": 1
		    }],
		    "status": 200
		},
		participators: {
		    "member": [{
		        "brithday": 20100606,
		        "category": 0,
		        "city": "",
		        "dist": "",
		        "height": 155,
		        "icon": "http://112.124.52.165:8080/resources/HeaderPicture/13301064_833.png",
		        "sex": "1",
		        "uid": "13301064",
		        "username": "No",
		        "weight": 58
		    }],
		    "candidate": [{
		        "brithday": 20100606,
		        "category": 0,
		        "city": "",
		        "dist": "",
		        "height": 155,
		        "icon": "http://112.124.52.165:8080/resources/HeaderPicture/13301064_833.png",
		        "sex": "1",
		        "uid": "13301064",
		        "username": "No",
		        "weight": 58
		    }],
		    "status": 200
		}, 
		day_course_schedule: {
		    "course": [{
				"service": {
					"sid": 1,
					"name": "online service",
					"intro": "service1",
				},
				calendar: {
					id: 1,
					stype: 1,
					cdate: 201505180100,
					minutes: 100,
					maxlimit: 100,
					sign: 10,
					intro: "20150518"
				}
			}, {
				"service": {},
				calendar: {
					id: 1,
					stype: 1,
					cdate: 201505180100,
					minutes: 100,
					maxlimit: 100,
					sign: 10,
					intro: "20150518"
				}	
			}],
		    "service": [{
		        "cid": "17088099",
		        "cost": 1000,
		        "intro": "service1",
		        "inuse": 0,
		        "month": 6,
		        "name": "online service",
		        "num": 2,
		        "offline": 3,
		        "offline_times": 3,
		        "online": 4,
		        "online_times": 3,
		        "sid": 1
		    }, {
		        "cid": "17088099",
		        "cost": 1000,
		        "intro": "service2",
		        "inuse": 0,
		        "month": 6,
		        "name": "offline service",
		        "num": 0,
		        "offline": 2,
		        "offline_times": 3,
		        "online": 3,
		        "online_times": 3,
		        "sid": 2
		    }],
		    "status": 200
		},
		subplan_actions: {"desc":[{"category":0,"cid":"13610140","duration":30,"id":22,"intro":"好好练21","inuse":0,"name":"好好练","pic":"http://112.124.52.165:8080/resources/AD/test.png","quantity":30,"rank":1,"rid":22,"share":1,"units":"分钟","url":"http://112.124.52.165:8080/resources/AD/test.png"},{"category":1,"cid":"13610140","duration":30,"id":23,"intro":"好好练22","inuse":0,"name":"好好练","pic":"http://112.124.52.165:8080/resources/AD/test.png","quantity":30,"rank":2,"rid":23,"share":1,"units":"分钟","url":""},{"category":1,"cid":"13610140","duration":30,"id":24,"intro":"好好练23","inuse":0,"name":"好好练","pic":"http://112.124.52.165:8080/resources/AD/test.png","quantity":30,"rank":3,"rid":24,"share":1,"units":"分钟","url":""},{"category":1,"cid":"13610140","duration":30,"id":25,"intro":"好好练24","inuse":0,"name":"好好练","pic":"http://112.124.52.165:8080/resources/AD/test.png","quantity":30,"rank":4,"rid":25,"share":1,"units":"分钟","url":""}],"status":200},
		action_models: {
		    "desc": [{
		        "category": 1,
		        "cid": "17088099",
		        "duration": 10,
		        "id": 1,
		        "intro": "desc1",
		        "inuse": 0,
		        "name": "desc1",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 110,
		        "rank": 1,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 2,
		        "intro": "好好练1",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 2,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 3,
		        "intro": "好好练2",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 3,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 4,
		        "intro": "好好练3",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 4,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 5,
		        "intro": "好好练4",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 5,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 6,
		        "intro": "好好练5",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 6,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 7,
		        "intro": "好好练6",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 7,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 8,
		        "intro": "好好练7",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 8,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 9,
		        "intro": "好好练8",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 9,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 10,
		        "intro": "好好练9",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 10,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 11,
		        "intro": "好好练10",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 11,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 12,
		        "intro": "好好练11",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 12,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 13,
		        "intro": "好好练12",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 13,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 14,
		        "intro": "好好练13",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 14,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 15,
		        "intro": "好好练14",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 15,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 16,
		        "intro": "好好练15",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 16,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 17,
		        "intro": "好好练16",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 17,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 18,
		        "intro": "好好练17",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 18,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 19,
		        "intro": "好好练18",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 19,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 20,
		        "intro": "好好练19",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 20,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }, {
		        "category": 1,
		        "cid": "17088099",
		        "duration": 30,
		        "id": 21,
		        "intro": "好好练20",
		        "inuse": 0,
		        "name": "好好练",
		        "pic": "http://112.124.52.165:8080/resources/AD/test.png",
		        "quantity": 30,
		        "rank": 21,
		        "share": 1,
		        "units": "分钟",
		        "url": ""
		    }],
		    "status": 200
		}
	}
});