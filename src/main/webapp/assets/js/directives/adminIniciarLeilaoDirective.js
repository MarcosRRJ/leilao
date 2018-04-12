angular.module('app.directives')
  .directive("iniciarLeilao", iniciarLeilao);

  iniciarLeilao.$inject = ['$rootScope'];

  function iniciarLeilao($rootScope){
      $rootScope.title = 'Iniciar Leilão'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_iniciarLeilao.html'
      }
  }
