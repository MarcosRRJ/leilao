angular.module('app.directives')
  .directive("atualizarUnidade", atualizarUnidade);

  atualizarUnidade.$inject = ['$rootScope'];

  function atualizarUnidade($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_editarUnidade.html'
      }
  }
