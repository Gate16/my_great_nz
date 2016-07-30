(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('CensusDeleteController',CensusDeleteController);

    CensusDeleteController.$inject = ['$uibModalInstance', 'entity', 'Census'];

    function CensusDeleteController($uibModalInstance, entity, Census) {
        var vm = this;

        vm.census = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Census.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
