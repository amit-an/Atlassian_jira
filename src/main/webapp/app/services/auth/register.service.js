(function () {
    'use strict';

    angular
        .module('hipsterGitlabCiSampleMavenApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
