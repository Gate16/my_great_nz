(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('result', {
            parent: 'app',
            url: '/result',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/result/result.html',
                    controller: 'ResultController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
