angular.module('app.directives')
  .directive("unidadesDesativadas", unidadesDesativadas);

  unidadesDesativadas.$inject = ['$rootScope'];

  function unidadesDesativadas($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_unidadesDesativadas.html'
      }
  }
