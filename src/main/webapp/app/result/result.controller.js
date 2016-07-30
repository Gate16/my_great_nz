(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ResultController', ResultController);

    ResultController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function getRandom(min, max) {
        return Math.round(Math.random() * (max - min) + min);
    }

    function ResultController ($scope, Principal, LoginService, $state) {

        $scope.regions = [{"id":"auckland", "name":"Auckland"},
                        {"id":"wellington", "name":"Wellington"},
                         {"id":"canterbury", "name":"Canterbury"},
                        {"id":"Northland", "name":"Northland"},
                        {"id":"Waikato", "name":"Waikato"},
                        {"id":"Bay of Plenty", "name":"Bay of Plenty"},
                        {"id":"Gisborne", "name":"	Gisborne"},
                        {"id":"Hawke's Bay", "name":"Hawke's Bay"},
                        {"id":"Taranaki", "name":"Taranaki"},
                        {"id":"Manawatu-Whanganui", "name":"Manawatu-Whanganui"},
                        {"id":"Tasman", "name":"Tasman"},
                        {"id":"Nelson", "name":"Nelson"},
                        {"id":"Marlborough", "name":"Marlborough"},
                        {"id":"West Coast", "name":"West Coast"},
                        {"id":"Otago", "name":"Otago"},
                        {"id":"Southland", "name":"Southland"}
        ];

        var arrayLength = $scope.regions.length;
        for (var i = 0; i < arrayLength; i++) {
            var region = $scope.regions[i];

            region.industry = getRandom(0,50);
            region.profession = getRandom(0,50);
            region.age = getRandom(0,50);
            region.nationality = getRandom(0,50);
            region.rainydays = getRandom(0,50);

            console.log($scope.regions[i]);
        }

    }
})();
