(function () {
    'use strict';

    angular
        .module('myGreatNzApp')
        .factory('Industries', Industries);

    Industries.$inject = ['$resource'];

    function Industries($resource) {
        var service = $resource('/api/public/industries/:region/:industry', {}, {
            'query': {method: 'GET', isArray: false},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });

        return service;
    };
    
})();
