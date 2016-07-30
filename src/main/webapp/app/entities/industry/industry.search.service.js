(function() {
    'use strict';

    angular
        .module('myGreatNzApp')
        .factory('IndustrySearch', IndustrySearch);

    IndustrySearch.$inject = ['$resource'];

    function IndustrySearch($resource) {
        var resourceUrl =  'api/public/_search/industries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
