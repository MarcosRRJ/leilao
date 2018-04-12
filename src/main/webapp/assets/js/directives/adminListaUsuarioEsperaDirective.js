angular.module('app.directives')
  .directive("usuariosListaEspera", usuariosListaEspera);

  usuariosListaEspera.$inject = ['$rootScope'];

  function usuariosListaEspera($rootScope){
      $rootScope.title = 'Usuários em Espera'; //<title> </title>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_listaUsuarioEspera.html'
      }
  }
