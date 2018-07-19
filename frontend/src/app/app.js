(() => {
    'use strict';

    /**
     * @ngdoc module
     * @name module
     * @requires dependencies
     * @description
     *
     * The `module` description.
     *
     */
    angular
        .module('pms', [
          'ui.router',
          'ngMaterial',
          'pms.teste'])
        .config(function($urlRouterProvider, $locationProvider){
          $urlRouterProvider.otherwise('/');
          $locationProvider.html5Mode(true);
        });
})();
