(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .controller('ResultController', ResultController);

    ResultController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function ResultController ($scope, Principal, LoginService, $state) {
        var vm = this;

 
    }
})();
