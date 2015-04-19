var MisApp = angular.module('misapp');
var resource = "http://112.124.52.165:8080/fitfame/v1/";
MisApp.factory('UserService', function ($http, $q) {
	return {
		auth: function(params) {
			// params = {
			// 	tel: 
			// 	pw: 
			// }
			return $http.jsonp(resource + 'restapi/user/login' + '?callback=JSON_CALLBACK', {params: params})
				.then(function (resp) {
                    angular.callbacks._0(data)
                     console.log("dddd", resp)
					if (resp.status === 200) {
						return resp;
					}
					else {
	                    // invalid response
	                    console.log("call", resp)
	                    return $q.reject(resp);
	                }
	            }, function(response) {
                    // something went wrong
                    return $q.reject(response);
                })
		}
	}
});

MisApp.factory('PlanService', function ($http, $q) {
    return {
        getPlans: function(params) {
            return $http.jsonp(resource + 'restapi/user/plan?callbacks=JSON_CALLBACK', {params: params}) // your url
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
        postPlan: function(params) {
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
        patchPlan: function(params) {
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
        deletePlan: function(params) {
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