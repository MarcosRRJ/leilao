angular.module('app.directives')
  .directive("leilaoFinalizado", leilaoFinalizado);

  leilaoFinalizado.$inject = ['$rootScope'];

  function leilaoFinalizado($rootScope){
      $rootScope.title = 'Leilões Finalizados'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_leilaoFinalizado.html'
      }
  }
