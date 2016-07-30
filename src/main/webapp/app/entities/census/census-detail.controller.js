(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('CensusDetailController', CensusDetailController);

    CensusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Census'];

    function CensusDetailController($scope, $rootScope, $stateParams, previousState, entity, Census) {
        var vm = this;

        vm.census = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myGreatNzApp:censusUpdate', function(event, result) {
            vm.census = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
