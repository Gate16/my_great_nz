(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('IndustryDeleteController',IndustryDeleteController);

    IndustryDeleteController.$inject = ['$uibModalInstance', 'entity', 'Industry'];

    function IndustryDeleteController($uibModalInstance, entity, Industry) {
        var vm = this;

        vm.industry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Industry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
