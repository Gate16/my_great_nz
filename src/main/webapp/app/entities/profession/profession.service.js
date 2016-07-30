(function() {
    'use strict';
    angular
        .module('myGreatNzApp')
        .factory('Profession', Profession);

    Profession.$inject = ['$resource'];

    function Profession ($resource) {
        var resourceUrl =  'api/public/professions/:id';

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
