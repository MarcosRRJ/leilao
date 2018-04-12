angular.module('app.directives')
  .directive("desativarUsuario", desativarUsuario);

  desativarUsuario.$inject = ['$rootScope'];

  function desativarUsuario($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_desativarUsuario.html'
      }
  }
