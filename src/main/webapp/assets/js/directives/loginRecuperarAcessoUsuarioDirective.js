angular.module('app.directives')
  .directive("loginRecuperarAcesso", loginRecuperarAcesso);

  loginRecuperarAcesso.$inject = ['$rootScope'];

  function loginRecuperarAcesso($rootScope){
      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/login_recuperarAcessoUsuario.html'
      }
  }
