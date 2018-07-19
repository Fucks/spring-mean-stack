(function() {
    'use strict';

    /**
     * @ngdoc function
     * @name controller
     * @module pms
     * @description
     *
     * The `controller` controller description.
     *
     */
    angular
        .module('pms')
        .controller('Controller', Controller);

    Controller.$inject = [];

    function Controller() {
        'ngInject';
        const self = this;
        self.teste = "OIOIOIOIOIOI";

        console.log("Controller")

        activate();

        ///////////

        function activate() {

        }
    }
})();
