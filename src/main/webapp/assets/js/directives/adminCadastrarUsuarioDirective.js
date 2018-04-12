angular.module('app.directives')
  .directive("cadastrarUsuario", cadastrarUsuario);

  cadastrarUsuario.$inject = ['$rootScope'];

  function cadastrarUsuario($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_cadastrarUsuario.html'
      }
  }
