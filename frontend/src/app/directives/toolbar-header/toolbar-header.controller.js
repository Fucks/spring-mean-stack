(() => {
    'use strict';

    /**
     * @ngdoc directive
     * @name directive
     * @module pms
     * @restrict EA
     * @description
     *
     * The `directive` directive description.
     *
     */
    angular
        .module('pms')
        .directive('toolbarHeader', directive);

    function directive() {
        const directive = {
            restrict: 'EA',
            templateUrl: 'app/directives/toolbar-header/toolbar-header.template.html',
            scope: {
            },
            controller: toolbarHeader,
            controllerAs: 'self',
            bindToController: true
        };

        return directive;

    }

    toolbarHeader.$inject = [];

    function toolbarHeader() {
        'ngInject';
        const self = this;

        activate();

        ///////////

        function activate() {

        }
    }
})();
