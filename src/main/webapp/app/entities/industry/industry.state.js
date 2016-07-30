(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('industry', {
            parent: 'entity',
            url: '/industry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Industries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/industry/industries.html',
                    controller: 'IndustryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('industry-detail', {
            parent: 'entity',
            url: '/industry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Industry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/industry/industry-detail.html',
                    controller: 'IndustryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Industry', function($stateParams, Industry) {
                    return Industry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'industry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('industry-detail.edit', {
            parent: 'industry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/industry/industry-dialog.html',
                    controller: 'IndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Industry', function(Industry) {
                            return Industry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('industry.new', {
            parent: 'industry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/industry/industry-dialog.html',
                    controller: 'IndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                region: null,
                                industryName: null,
                                year2016: null,
                                year2017: null,
                                year2018: null,
                                year2019: null,
                                growth: null,
                                growthPercentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('industry', null, { reload: true });
                }, function() {
                    $state.go('industry');
                });
            }]
        })
        .state('industry.edit', {
            parent: 'industry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/industry/industry-dialog.html',
                    controller: 'IndustryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Industry', function(Industry) {
                            return Industry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('industry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('industry.delete', {
            parent: 'industry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/industry/industry-delete-dialog.html',
                    controller: 'IndustryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Industry', function(Industry) {
                            return Industry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('industry', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
