angular.module('app.directives')
  .directive("usuariosListaDesativado", usuariosListaDesativado);

  usuariosListaDesativado.$inject = ['$rootScope'];

  function usuariosListaDesativado($rootScope){
      $rootScope.title = 'Usuários Desativados'; //<title> </title>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_listaUsuarioDesativado.html'
      }
  }
