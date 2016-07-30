(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profession', {
            parent: 'entity',
            url: '/profession',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Professions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profession/professions.html',
                    controller: 'ProfessionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('profession-detail', {
            parent: 'entity',
            url: '/profession/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Profession'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profession/profession-detail.html',
                    controller: 'ProfessionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Profession', function($stateParams, Profession) {
                    return Profession.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profession',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profession-detail.edit', {
            parent: 'profession-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profession/profession-dialog.html',
                    controller: 'ProfessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profession', function(Profession) {
                            return Profession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profession.new', {
            parent: 'profession',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profession/profession-dialog.html',
                    controller: 'ProfessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                region: null,
                                professionId: null,
                                professionName: null,
                                year_2016: null,
                                year_2017: null,
                                year_2018: null,
                                year_2019: null,
                                growth: null,
                                growthPercentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profession', null, { reload: true });
                }, function() {
                    $state.go('profession');
                });
            }]
        })
        .state('profession.edit', {
            parent: 'profession',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profession/profession-dialog.html',
                    controller: 'ProfessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profession', function(Profession) {
                            return Profession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profession', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profession.delete', {
            parent: 'profession',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profession/profession-delete-dialog.html',
                    controller: 'ProfessionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profession', function(Profession) {
                            return Profession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profession', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
