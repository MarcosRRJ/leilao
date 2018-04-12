angular.module('app.directives')
  .directive("usuariosLista", usuariosLista);

  usuariosLista.$inject = ['$rootScope'];

  function usuariosLista($rootScope){
      $rootScope.title = 'Usuários Ativos'; //<title> </title>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_listaUsuario.html'
      }
  }
