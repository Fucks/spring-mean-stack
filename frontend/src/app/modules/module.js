(() => {
    'use strict';

    /**
     * @ngdoc module
     * @name pms.teste
     * @requires dependencies
     * @description
     *
     * The `pms.teste` description.
     *
     */
    angular
        .module('pms.teste', [])
        .config(function($stateProvider){
          $stateProvider.state('teste',{
            url: '/teste',
            controller: 'Controller',
            controllerAs: 'vm',
            templateUrl: 'app/modules/teste.html'
          })

          $stateProvider.state('teste2', {
            url: '/teste2',
            controller: 'Controller',
            controllerAs: 'vm',
            template: '<md-content>{{vm.teste}}</md-content>'
          })

          $stateProvider.state('teste3', {
            url: '/teste3',
            controller: 'Controller',
            controllerAs: 'vm',
            template: '<md-content><h3>{{vm.teste}}</h3></md-content>'
          })
        })
})();
