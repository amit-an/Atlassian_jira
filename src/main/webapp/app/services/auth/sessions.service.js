(function() {
    'use strict';

    angular
        .module('hipsterGitlabCiSampleMavenApp')
        .factory('Sessions', Sessions);

    Sessions.$inject = ['$resource'];

    function Sessions ($resource) {
        return $resource('api/account/sessions/:series', {}, {
            'getAll': { method: 'GET', isArray: true}
        });
    }
})();
