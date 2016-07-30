(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('IsController', IsController);

    IsController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$routeParams'];

    function IsController ($scope, Principal, LoginService, $state, $routeParams) {
        var vm = this;
        vm.region = $routeParams.param1;
        alert(vm.regon);
    }
})();
