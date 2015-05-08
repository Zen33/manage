var MisApp = angular.module('misapp');
var resource = "http://112.124.52.165:8080/fitfame/v1/restapi/";
MisApp.factory('UserService', function ($http, $q, customtable) {
    var token = "";
	return {
		auth: function(params) {
            // token = customtable.fixtures.coach_info.token;
            // return customtable.fixtures.coach_info;
			// params = {
			// 	tel: 
			// 	pw: 
			// }
            var deferred = $q.defer();
			$http.jsonp(resource + 'user/login' + '?callback=JSON_CALLBACK', {params: params})
			.then(function (resp) {
				if (resp.data.status === 200) {
                    token = resp.data.token;
					deferred.resolve(customtable.getBodyFromResponse(resp.data));
				}
				else {
                    // invalid response
                    deferred.reject(resp.data.message);
                }
            }, function(response) {
                // something went wrong
                deferred.reject(response);
            });
            return deferred.promise;
		},
        getUsers: function(params) {
            // params = {
            //     token: 
            // }
            var deferred = $q.defer();
            $http.jsonp(resource + 'plan/coach/user' + '?callback=JSON_CALLBACK', {params: params})
            .then(function (resp) {
                if (resp.data.status === 200) {
                    deferred.resolve(customtable.getBodyFromResponse(resp.data).users);
                }
                else {
                    // invalid response
                    deferred.reject(resp.data.message);
                }
            }, function(response) {
                // something went wrong
                deferred.reject(response);
            });
            return deferred.promise;
        },
        getToken: function() {
            return token;
        },
        patchCoach: function(params) {
            // params = {
            //     token:
            //     ...
            // }
            var deferred = $q.defer();
            $http.jsonp(resource + 'user/coach/profile' + '?callback=JSON_CALLBACK', {params: params})
            .then(function (resp) {
                if (resp.status === 200) {
                    deferred.resolve(resp);
                }
                else {
                    // invalid response
                    deferred.reject(resp);
                }
            }, function(response) {
                // something went wrong
                deferred.reject(response);
            });
            return deferred.promise;
        }
	}
});

MisApp.factory('PlanService', function ($http, $q, customtable) {
    return {
        getPublicPlans: function(params) {
            // params = {
            //    token: 
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).plan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);

                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        getPlanModels: function(params) {
            // params = {
            //     token:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plantemplate?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).plan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }
            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        getUserDoingPlan: function(params) {
            // params = {
            //     token:
            //     uid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/user/undo?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.error);
            });
            return defer.promise;
        },
        postPublicPlan: function(params) {
            // params = {
            //     token:
            //     pid: 
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plan/publish?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).plan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.error);
            });
            return defer.promise;
        },
        postPlanModel: function(params) {
            // params = {
            //     token:
            //     name:
            //     intro:
            //     duration:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plantemplate/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.status === 200) {
                    // return messages
                    defer.resolve(response.data);
                } else {
                    // invalid response
                    defer.reject(response.data);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        postUserPlan: function(params) {
            // params = {
            //     token:
            //     uid:
            //     pid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/user/replace?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        patchPlanModel: function(params) {
            // params = {
            //     token:
            //     name:
            //     intro:
            //     duration:
            //     id:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plantemplate/update?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(params);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.error);
            });
            return defer.promise;
        },
        deletePlanModel: function(params) {
            // params = {
            //     token:
            //     id:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plantemplate/delete?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).plan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        },
        deletePublicPlan: function(params) {
            // params = {
            //     token:
            //     pid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plan/remove?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).plan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        }
    };
});

MisApp.factory('TrainService', function ($http, $q, customtable) {
    return {
        getTrainModels: function(params) {
            // params = {
            //     token:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        getPlanTrains: function(params) {
            // params = {
            //     id: 计划id
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/subplan?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        postPublicTrain: function(params) {
            // params = {
            //     token:
            //     pid:
            //     spid:
            //     rank:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plan/subplan/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        postTrainModel: function(params) {
            // params = {
            //     token:
            //     name: 
            //     intro:
            //     duration:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve();
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        },
        patchTrainModel: function(params) {
            // params = {
            //     token:
            //     name:
            //     intro:
            //     duration:
            //     id:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan/update?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.status === 200) {
                    // return messages
                    defer.resolve(response.data);
                } else {
                    // invalid response
                    defer.reject(response.data);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        deletePlanTrain: function(params) {
            // params = {
            //     token:
            //     rid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plan/subplan/remove?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        },
        deleteTrainModel: function(params) {
            // params = {
            //     token:
            //     id:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan/delete?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).subplan);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        }
    }
});

MisApp.factory('ActionService', function ($http, $q, customtable) {
    return {
        getActionModels: function(params) {
            // params = {
            //     token:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/subplan/desc?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).desc);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response);
            });
            return defer.promise;
        },
        getSubplanActions: function(params) {
            // params = {
            //     spid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/subplan/desc?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).desc);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        postSubplanAction: function(params) {
            // params = {
            //     spid:
            //     did:
            //     token:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan/desc/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).desc);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.error);
            });
            return defer.promise;
        },
        postActionModel: function(params) {
            // params = {
            //     token:
            //     name:    
            //     intro:
            //     pic:
            //     picType:
            //     media:
            //     mediaType:
            //     category:
            //     quantiry:
            //     units:
            //     duration:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/plandesc/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve("success!");
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        },
        deleteSubplanAction: function(params) {
            // params = {
            //     token:
            //     rid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'plan/coach/subplan/desc/remove?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.status === 200) {
                    // return messages
                    defer.resolve(response.data);
                } else {
                    // invalid response
                    defer.reject(response.data);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        }
    }
});

MisApp.factory('ServiceService', function ($http, $q, customtable) {
    return {
        getUserServices: function(params) {
            // params = {
            //     token:
            //     uid:
            // }  
            var defer = $q.defer();
            $http.jsonp(resource + 'coach/info/personal?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).services);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        getServices: function(params) {
            // params = {
            //     token:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'coach/service?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).services);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.data);
            });
            return defer.promise;
        },
        postService: function(params) {
            // var params = {
            //     token:
            //     name:
            //     intro:
            //     cost:
            //     online_times:
            //     offline_times:
            //     online:
            //     offline:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'coach/service/add?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve(customtable.getBodyFromResponse(response.data).sid);
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        },
        patchService: function(params) {
            // var params = {
            //     token:
            //     name:
            //     intro:
            //     cost:
            //     online_times:
            //     offline_times:
            //     online:
            //     offline:
            //     sid:
            // }
            var defer = $q.defer();
            $http.jsonp(resource + 'coach/service/update?callback=JSON_CALLBACK', {params: params}) // your url
            .then(function(response) {
                // if success
                if (response.data.status === 200) {
                    // return messages
                    defer.resolve("success");
                } else {
                    // invalid response
                    defer.reject(response.data.message);
                }

            }, function(response) {
                // something went wrong
                defer.reject(response.statusText);
            });
            return defer.promise;
        }
    }
});

MisApp.factory('MessageService', function ($http, $q) {
    return {
        getMessages: function(params) {
            return $http.get('url') // your url
                .then(function(response) {
                	// if success
                    if (response.status === 200) {
                    	// return messages
                        return response.data;
                    } else {
                        // invalid response
                        return $q.reject(response.data);
                    }

                }, function(response) {
                    // something went wrong
                    return $q.reject(response.data);
            	});
        },
        postMessage: function(params) {
        	return $http.post('url', params) // your url
                .then(function(response) {
                	// if success
                    if (response.status === 201) {
                    	// return new message
                        return response.data;
                    } else {
                        // invalid response
                        return $q.reject(response.data);
                    }

                }, function(response) {
                    // something went wrong
                    return $q.reject(response.data);
            	});
        },
        patchMessage: function(params) {
        	return $http.patch('url', params) // your url
                .then(function(response) {
                	// if success
                    if (response.status === 200) {
                    	// return updated message
                        return response.data;
                    } else {
                        // invalid response
                        return $q.reject(response.data);
                    }

                }, function(response) {
                    // something went wrong
                    return $q.reject(response.data);
            	});
        },
        deleteMessage: function(params) {
        	return $http.delete('url', params) // your url
                .then(function(response) {
                	// if response status is 200 or 204
                    if (response.status === 204) {
                    	// return response status
                    } else {
                        // invalid response
                        return $q.reject(response.data);
                    }

                }, function(response) {
                    // something went wrong
                    return $q.reject(response.data);
            	});
        }
    };
});