angular.module('app.directives')
  .directive("userCompras", userCompras);

  userCompras.$inject = ['$rootScope'];

  function userCompras($rootScope){
      $rootScope.title = 'Minhas Compras'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/user_compras.html'
      }
  }
