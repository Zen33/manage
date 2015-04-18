var MisApp = angular.module('misapp');

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