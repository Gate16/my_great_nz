(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ProfessionDeleteController',ProfessionDeleteController);

    ProfessionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profession'];

    function ProfessionDeleteController($uibModalInstance, entity, Profession) {
        var vm = this;

        vm.profession = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
