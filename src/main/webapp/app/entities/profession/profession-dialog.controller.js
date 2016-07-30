(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ProfessionDialogController', ProfessionDialogController);

    ProfessionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Profession'];

    function ProfessionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Profession) {
        var vm = this;

        vm.profession = entity;
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
            if (vm.profession.id !== null) {
                Profession.update(vm.profession, onSaveSuccess, onSaveError);
            } else {
                Profession.save(vm.profession, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myGreatNzApp:professionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
