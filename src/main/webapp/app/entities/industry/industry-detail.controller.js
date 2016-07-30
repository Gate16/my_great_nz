(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('IndustryDetailController', IndustryDetailController);

    IndustryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Industry'];

    function IndustryDetailController($scope, $rootScope, $stateParams, previousState, entity, Industry) {
        var vm = this;

        vm.industry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myGreatNzApp:industryUpdate', function(event, result) {
            vm.industry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
