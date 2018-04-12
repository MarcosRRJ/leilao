angular.module('app.directives')
  .directive("ultimosLeiloes", ultimosLeiloes);

  ultimosLeiloes.$inject = ['$rootScope'];

  function ultimosLeiloes($rootScope){
      $rootScope.title = 'Últimos Leilões'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/user_ultimosLeiloes.html'
      }
  }
