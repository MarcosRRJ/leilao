angular.module('app.directives')
  .directive("loginCadastrarUsuario", loginCadastrarUsuario);

  loginCadastrarUsuario.$inject = ['$rootScope'];

  function loginCadastrarUsuario($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/login_cadastrarUsuario.html'
      }
  }
