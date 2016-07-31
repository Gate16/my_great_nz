(function() {
	'use strict';

	angular.module('myGreatNzApp').controller('IsController', IsController);

	IsController.$inject = [ '$scope', 'Principal', 'LoginService', '$state',
			'$stateParams', '$sce', '$http', 'Industries'];


    var introductions = [{'id':'Auckland','description':'Imagine an urban environment where everyone lives within half an hour of beautiful beaches, hiking trails and a dozen enchanting holiday islands. Add a sunny climate, a background rhythm of Polynesian culture and a passion for outstanding food, wine and shopping, and you’re beginning to get the picture of Auckland, New Zealand, our largest and most diverse city. More than just a city, Auckland is a whole region full of things to see and do. Best of all, with so many experiences close by it’s easy to hop from one adventure to the next!'},
    {'id':'Canterbury','description':'Christchurch is the gateway to New Zealands South Island. Bordered by hills and the Pacific Ocean, it is situated on the edge of the Canterbury Plains that stretch to the Southern Alps. Within two hours of the international airport, you can ski at a world-class alpine resort, play golf, bungy jump, raft, mountain bike, hot-air balloon, wind surf, whale watch and visit internationally-acclaimed wineries and gardens.'},
    {'id':'Wellington', 'description':"Called the world’s ‘coolest little capital’ Wellington is known for its vibrant arts scene, world class café and restaurant culture, and active outdoor lifestyle. Set around an attractive waterfront, you'll be hard pressed to find a city in the world that's easier to get around. Wellington enjoys more cafes, bars and restaurants than New York City, and its coffee and craft beer producers are internationally recognised. A creative, cosmopolitan city, Wellington combines the sophistication of a capital with the warmth and personality of a village."}
    ]


	function IsController($scope, Principal, LoginService, $state,
			$stateParams, $sce, $http, Industries) {


		var vm = this;
		$scope.region = $stateParams.region;
        $scope.industry = $stateParams.industry;
        $scope.profession = $stateParams.profession;

        $scope.map_url = $sce.trustAsResourceUrl("https://www.google.com/maps/embed/v1/place?key=AIzaSyDqPw5bjILL-g1fbU56teCbY3eoM0-535M&q="
            + $stateParams.region + "+New+Zealand");

        console.log($sce);

		var result = $.grep(introductions, function(e){ return e.id == $stateParams.region; });
		$scope.introductionText = result[0].description;

        //Industry
        $scope.labels_years = [ '2016', '2017', '2018', '2019' ];
		$scope.industrySeries = [ $scope.industry ];
		$scope.industryData = [ [ 100, 150, 170, 200 ] ];

        //Profession
        $scope.professionSeries = [ $scope.profession ];
        $scope.professionData = [ [ 200, 250, 270, 260 ] ];


        $scope.labels_income = [ '$1-$5,000', '$5,001-$10,000', '$10,001-$15,000', '$15,001-$20,000',
            '$25,001-$30,000',
            '$30,001-$35,000',
            '$40,001-$50,000',
            '$50,001-$70,000',
            '$70,001-$100,000',
            '$100,001 or More'];


        $scope.incomeSeries = [ "2006", "2013" ];
        $scope.incomeData = [
                    [ 4698, 6411, 15258, 18633,  21648, 1245, 15654, 15996, 27468, 53217],
                    [ 4698, 6411, 15258, 18633,  21648, 1245, 15654, 15996, 27468, 53217],
                    ];

        $scope.labels_languages = [
            'Maori',
            'Samoan',
            'Hindi',
            'Northern Chinese',
            'French',
            'Yue',
            'Sinitic not further defined',
            'German',
            'Tongan',
            'Tagalog',
            'Afrikaans',
            'Spanish',
            'Korean',
            'New Zealand sign language'];

        $scope.languagesData = [30924, 58197, 49521, 38781, 17433, 30681, 30282, 11886, 26031, 14925, 13992, 10605, 19362, 5259];

        $scope.labels_country = [
            'Australia',
            'Pacific Islands',
            'United Kingdom and Ireland',
            'Europe (excluding United Kingdom and Ireland)',
            'North America',
            'Asia',
            'Middle East and Africa',
            'Other'];

        $scope.countryData = [1959, 109674, 90432, 27504, 10746, 203277, 50301, 5658];


        $scope.labels_transport = [
            'Worked at home',
            'Did not go to work today',
            'Drove a private car, truck or van',
            'Drove a company car, truck or van',
            'Passenger in a car, truck, van or company bus',
            'Public bus',
            'Train',
            'Motor cycle or power cycle',
            'Bicycle',
            'Walked or jogged',
            'Other',];

        $scope.transportData = [44253, 61827, 340299, 65484, 2451, 33933, 9459, 5496, 6342, 26529, 7986];

        $scope.labels_dwelling = [
            'Separate house',
            'Apartments/Units',
            'Other private',
            'Not defined'];

        $scope.dwellingData = [161226, 3423, 1791, 9669];


        $scope.education = [
                {"no":"2", "Education":"No Qualification", "year_2006":"183975", "year_2013":"166782"},
                {"no":"3", "Education":"Level 1 Certificate", "year_2006":"108102", "year_2013":"104454"},
                {"no":"4", "Education":"Level 2 Certificate", "year_2006":"92259", "year_2013":"95241"},
                {"no":"5", "Education":"Level 3 Certificate", "year_2006":"85101", "year_2013":"104508"},
                {"no":"6", "Education":"Level 4 Certificate", "year_2006":"78816", "year_2013":"78876"},
                {"no":"7", "Education":"Level 5 or Level 6 Diploma", "year_2006":"86436", "year_2013":"91923"},
                {"no":"8", "Education":"Bachelor Degree and Level 7 Qualification", "year_2006":"129165", "year_2013":"168924"},
                {"no":"9", "Education":"Post-graduate and Honours Degrees", "year_2006":"1923", "year_2013":"31854"},
                {"no":"10", "Education":"Masters Degree", "year_2006":"2622", "year_2013":"36792"},
                {"no":"11", "Education":"Doctorate Degree", "year_2006":"5391", "year_2013":"7428"},
                {"no":"12", "Education":"Overseas Secondary School Qualification", "year_2006":"90618", "year_2013":"105201"}];



        $scope.labels_weather = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
        $scope.weatherSeries = ['Rainy days %', 'Av temp'];
        $scope.weatherData = [
            [30, 30, 31, 34, 37, 41, 42, 38, 32, 33, 33, 33],
            [12.4, 10.3,9.4,6.6,12.5,11.3,10.2,11.1,11.8,12.8,13,10.4]
        ];

        $scope.colors = ['#45b7cd', '#ff6384'];

        $scope.datasetOverride = [{ yAxisID: 'y-axis-1' }, { yAxisID: 'y-axis-2' }];
        $scope.options = {
            scales: {
                yAxes: [
                    {
                        id: 'y-axis-1',
                        type: 'linear',
                        display: true,
                        position: 'left',
                        ticks: {
                            beginAtZero:true
                        }
                    },
                    {
                        id: 'y-axis-2',
                        type: 'linear',
                        display: true,
                        position: 'right'
                    }
                ]
            },
            legend:{
                display: true
            }
        };



        $scope.options_dafault = {
            scales: {
                yAxes: [
                    {
                        ticks: {
                            beginAtZero:true
                        }
                    }
                 ]
            }
        };

        $scope.options_legent = {
            scales: {
                yAxes: [
                    {
                        ticks: {
                            beginAtZero:true
                        }
                    }
                ]
            },
            legend:{
                display: true
            }
        };

        $scope.options_pie = {
            scales: {
                display:false,
                yAxes: [
                    {
                        display:false,
                        ticks: {
                            beginAtZero:true
                        }
                    }
                ]
            },
            legend:{
                display: true,
                position: "bottom"
            }
        };

        /*$http.get('/api/public/industries/' + $scope.industry)
        success(function (data) {
                console.log(data);
            }
        );*/

        /*//console.log(Industries);*/
		Industries.query({
			region : $scope.region,
			industry : $scope.industry
		}, function(result) {
			var items = [ [ result.year2016, result.year2017, result.year2018,
					result.year2019 ] ];
			$scope.industryData = items;
		});


	}
})();
