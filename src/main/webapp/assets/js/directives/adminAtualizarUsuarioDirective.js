angular.module('app.directives')
  .directive("atualizarUsuario", atualizarUsuario);

  atualizarUsuario.$inject = ['$rootScope'];

  function atualizarUsuario($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_editarUsuario.html'
      }
  }
