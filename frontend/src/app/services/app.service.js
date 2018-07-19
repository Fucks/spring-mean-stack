(() => {
    'use strict';

    /**
     * @ngdoc service
     * @name service
     * @module pms
     * @requires dependencies
     * @description
     *
     * The `service` service description.
     *
     */
    angular
        .module('pms')
        .service('sidenavService', sidenavService);

    sidenavService.$inject = [];

    function sidenavService() {
        'ngInject';
        let menus = []


        menus.push({
          'category' : 'dashboard',
          'state' : 'teste',
          'icon': 'dashboard'
        });
        menus.push({
          'category' : 'Projetos',
          'state': 'teste2',
          'icon' : 'assignment'
        });
        menus.push({
          'category' : 'Equipe',
          'state': 'teste3',
          'icon' : 'assignment_ind'
        });




        return{
          menus : menus
        }
    }
})();
