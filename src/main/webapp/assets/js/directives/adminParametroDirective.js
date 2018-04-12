angular.module('app.directives')
  .directive("parametros", parametros);

parametros.$inject = ['$rootScope'];

  function parametros($rootScope){
      $rootScope.title = 'Parâmetros'; //<title> </title>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_parametros.html'
      }
  }
