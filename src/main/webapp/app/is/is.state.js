(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('is', {
            parent: 'app',
            url: '/is/:region',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/is/is.html',
                    controller: 'IsController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
