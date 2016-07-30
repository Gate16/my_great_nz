(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('census', {
            parent: 'entity',
            url: '/census',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Censuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/census/censuses.html',
                    controller: 'CensusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('census-detail', {
            parent: 'entity',
            url: '/census/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Census'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/census/census-detail.html',
                    controller: 'CensusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Census', function($stateParams, Census) {
                    return Census.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'census',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('census-detail.edit', {
            parent: 'census-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/census/census-dialog.html',
                    controller: 'CensusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Census', function(Census) {
                            return Census.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('census.new', {
            parent: 'census',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/census/census-dialog.html',
                    controller: 'CensusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                region: null,
                                interestArea: null,
                                type: null,
                                year2006: null,
                                year2013: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('census', null, { reload: true });
                }, function() {
                    $state.go('census');
                });
            }]
        })
        .state('census.edit', {
            parent: 'census',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/census/census-dialog.html',
                    controller: 'CensusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Census', function(Census) {
                            return Census.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('census', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('census.delete', {
            parent: 'census',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/census/census-delete-dialog.html',
                    controller: 'CensusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Census', function(Census) {
                            return Census.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('census', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
