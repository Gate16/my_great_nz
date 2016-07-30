(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ResultController', ResultController);

    ResultController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function ResultController ($scope, Principal, LoginService, $state) {

        $scope.regions = [{"id":"auckland", "name":"Auckland", "spiciness":"mild"},
                        {"id":"wellington", "name":"Wellington", "spiciness":"hot hot hot!"},
                         {"id":"canterbury", "name":"Canterbury", "spiciness":"LAVA HOT!!"}];

    }
})();
