(() => {
    'use strict';

    /**
     * @ngdoc directive
     * @name directive
     * @module module
     * @restrict EA
     * @description
     *
     * The `directive` directive description.
     *
     */
    angular
        .module('pms')
        .directive('mainSidenav', mainSidenav);

    function mainSidenav() {
        const mainSidenav = {
            restrict: 'E',
            templateUrl: 'app/directives/main-sidenav/main-sidenav.template.html',
            scope: {
            },
            link: link,
            controller: Controller,
            controllerAs: 'vm',
            bindToController: true
        };

        return mainSidenav;

        ///////////

        function link(scope, el, attr, ctrl) {

        }
    }

    Controller.$inject = ['sidenavService'];

    function Controller(sidenavService) {
        'ngInject';
        const vm = this;

        vm.menus = sidenavService.menus;

        initialize();

        //////////////
        function initialize(){

        }

    }
})();
