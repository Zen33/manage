var MisApp = angular.module('misapp');

MisApp.factory('MessageService', function ($http, $q) {
    return {
        getMessages: function(params) {
            return $http.get('url') // your url
                .then(function(response) {
                	// if success
                    if (true) {
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
        }
    };
});