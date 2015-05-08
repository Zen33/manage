var MisApp = angular.module('misapp');
MisApp.controller('OptionsModalInstanceCtrl', function ($scope, $modalInstance, options, title) {

	$scope.options = options;
	$scope.title = title;
	$scope.optionsChecked = {};

	for (var index in options) {
		$scope.optionsChecked[options[index].id] = false;
	}

	$scope.onChange = function(id) {
		for (var index in $scope.options) {
			if ($scope.options[index].id === id) {
				// if ($scope.optionsChecked[id]) {
				// 	$scope.optionsChecked[id] = false;
				// }
				// else {
					$scope.optionsChecked[id] = true;
				// }
			}
			else {
				$scope.optionsChecked[$scope.options[index].id] = false;
			}
		}
	};

	$scope.ok = function () {
		var id = -1;
		_.forEach($scope.optionsChecked, function(value, key) { 
			if (value) {
				id = key;
			} 
		});
		if (id === -1) {
			$modalInstance.dismiss('cancel');
		}
		else {
			$modalInstance.close(id);
		}
	};

	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
});