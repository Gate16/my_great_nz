(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .factory('ProfessionSearch', ProfessionSearch);

    ProfessionSearch.$inject = ['$resource'];

    function ProfessionSearch($resource) {
        var resourceUrl =  'api/public/_search/professions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
