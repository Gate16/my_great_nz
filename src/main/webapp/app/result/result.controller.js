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

            region.industry = {'name':getParameterByName('industry'), 'value':getRandom(10,30)};
            region.profession = {'name':getParameterByName('profession'), 'value':getRandom(100,1000)};
            region.age = getRandom(10000,40000);
            region.nationality = {'name':getParameterByName('nationality'), 'value':getRandom(10,30)};
            region.rainydays = getRandom(50,365);

            console.log($scope.regions[i]);
        }
        
        function getParameterByName(name, url) {
            if (!url) url = window.location.href;
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
            if (!results) return null;
            if (!results[2]) return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }

    }
})();
