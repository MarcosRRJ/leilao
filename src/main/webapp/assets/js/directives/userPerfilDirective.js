angular.module('app.directives')
  .directive("userPerfil", userPerfil);

  userPerfil.$inject = ['$rootScope'];

  function userPerfil($rootScope){
      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/user_perfil.html'
      }
  }
