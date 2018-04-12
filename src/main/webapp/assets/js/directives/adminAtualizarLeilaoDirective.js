angular.module('app.directives')
  .directive("atualizarLeilao", atualizarLeilao);

  atualizarLeilao.$inject = ['$rootScope'];

  function atualizarLeilao($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_editarLeilao.html'
      }
  }
