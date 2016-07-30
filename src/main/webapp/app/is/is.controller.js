(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('IsController', IsController);

    IsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$stateParams'];

    var introductions = new Map();
    introductions.set('Auckland','Auckland, based around 2 large harbours, is a major city in the north of New Zealand’s North Island. In central Queen Street, the iconic Sky Tower has views of Viaduct Harbour, which is full of superyachts and lined with bars and cafes. Auckland Domain, the city’s oldest park, is based around an extinct volcano and home to the formal Wintergardens. Mission Bay Beach is minutes from Downtown.');

    function IsController ($scope, Principal, LoginService, $state, $stateParams) {
        var vm = this;
        $scope.region = $stateParams.region;
        $scope.introductionText=introductions.get($stateParams.region);


        $scope.labels = ['2016', '2017', '2018', '2019'];
        $scope.series = ['Construction'];

        $scope.data = [
            [6087,6203,6213,615]
        ];


    }
})();
