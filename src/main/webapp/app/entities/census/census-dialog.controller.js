(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('CensusDialogController', CensusDialogController);

    CensusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Census'];

    function CensusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Census) {
        var vm = this;

        vm.census = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.census.id !== null) {
                Census.update(vm.census, onSaveSuccess, onSaveError);
            } else {
                Census.save(vm.census, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myGreatNzApp:censusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
