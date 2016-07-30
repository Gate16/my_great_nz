(function() {
    'use strict';
    angular
        .module('myGreatNzApp')
        .factory('Census', Census);

    Census.$inject = ['$resource'];

    function Census ($resource) {
        var resourceUrl =  'api/public/censuses/:id';

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
