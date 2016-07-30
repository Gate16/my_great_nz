(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ProfessionDetailController', ProfessionDetailController);

    ProfessionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profession'];

    function ProfessionDetailController($scope, $rootScope, $stateParams, previousState, entity, Profession) {
        var vm = this;

        vm.profession = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('myGreatNzApp:professionUpdate', function(event, result) {
            vm.profession = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
