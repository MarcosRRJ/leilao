angular.module('app.directives')
  .directive("proximosLeiloes", proximosLeiloes);

  proximosLeiloes.$inject = ['$rootScope'];

  function proximosLeiloes($rootScope){
      $rootScope.title = 'Próximos Leilões'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/user_proximosLeiloes.html'
      }
  }
