(function() {
    'use strict';

    angular
        .module('hipsterGitlabCiSampleMavenApp')
        .controller('BookController', BookController);

    BookController.$inject = ['$scope', '$state', 'Book'];

    function BookController ($scope, $state, Book) {
        var vm = this;
        
        vm.books = [];

        loadAll();

        function loadAll() {
            Book.query(function(result) {
                vm.books = result;
            });
        }
    }
})();
