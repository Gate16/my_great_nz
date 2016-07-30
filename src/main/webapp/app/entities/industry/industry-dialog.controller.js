(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('IndustryDialogController', IndustryDialogController);

    IndustryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Industry'];

    function IndustryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Industry) {
        var vm = this;

        vm.industry = entity;
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
            if (vm.industry.id !== null) {
                Industry.update(vm.industry, onSaveSuccess, onSaveError);
            } else {
                Industry.save(vm.industry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('myGreatNzApp:industryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
