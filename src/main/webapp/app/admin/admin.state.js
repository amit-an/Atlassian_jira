(function () {
    'use strict';

    angular
        .module('hipsterGitlabCiSampleMavenApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('admin', {
            abstract: true,
            parent: 'app'
        });
    }
})();
