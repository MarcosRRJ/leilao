angular.module('app.directives')
  .directive("adminPerfil", adminPerfil);

  adminPerfil.$inject = ['$rootScope'];

  function adminPerfil($rootScope){
      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_perfil.html'
      }
  }
