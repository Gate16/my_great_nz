(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .factory('CensusSearch', CensusSearch);

    CensusSearch.$inject = ['$resource'];

    function CensusSearch($resource) {
        var resourceUrl =  'api/public/_search/censuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
