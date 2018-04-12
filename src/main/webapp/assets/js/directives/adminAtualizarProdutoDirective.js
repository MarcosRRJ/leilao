angular.module('app.directives')
  .directive("atualizarProduto", atualizarProduto);

  atualizarProduto.$inject = ['$rootScope'];

  function atualizarProduto($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_editarProduto.html'
      }
  }
