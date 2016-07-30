(function() {
    'use strict';
    angular
        .module('myGreatNzApp')
        .factory('Industry', Industry);

    Industry.$inject = ['$resource'];

    function Industry ($resource) {
        var resourceUrl =  'api/public/industries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
