(function() {
	'use strict';

	angular.module('myGreatNzApp').controller('IsController', IsController);

	IsController.$inject = [ '$scope', 'Principal', 'LoginService', '$state',
			'$stateParams'];


    var introductions = new Map();
    introductions.set('Auckland','Imagine an urban environment where everyone lives within half an hour of beautiful beaches, hiking trails and a dozen enchanting holiday islands. Add a sunny climate, a background rhythm of Polynesian culture and a passion for outstanding food, wine and shopping, and you’re beginning to get the picture of Auckland, New Zealand, our largest and most diverse city. More than just a city, Auckland is a whole region full of things to see and do. Best of all, with so many experiences close by it’s easy to hop from one adventure to the next!');
    introductions.set('Christchurch','Christchurch is the gateway to New Zealands South Island. Bordered by hills and the Pacific Ocean, it is situated on the edge of the Canterbury Plains that stretch to the Southern Alps. Within two hours of the international airport, you can ski at a world-class alpine resort, play golf, bungy jump, raft, mountain bike, hot-air balloon, wind surf, whale watch and visit internationally-acclaimed wineries and gardens.');



	function IsController($scope, Principal, LoginService, $state,
			$stateParams, Industries) {

        $scope.colors = Chart.defaults.global.colors;

		var vm = this;
		$scope.region = $stateParams.region;
		$scope.industry = 'Construction';
		$scope.introductionText = introductions.get($stateParams.region);

		$scope.labels = [ '2016', '2017', '2018', '2019' ];
		$scope.series = [ $scope.industry ];

		$scope.data = [ [ 10, 0, 0, 600 ] ];

		$scope.industryData = [ [ 10, 0, 0, 600 ] ];

		/*Industries.query({
			region : $scope.region,
			industry : $scope.industry
		}, function(result) {
			var items = [ [ result.year2016, result.year2017, result.year2018,
					result.year2019 ] ];
			$scope.industryData = items;
		});*/


	}
})();
