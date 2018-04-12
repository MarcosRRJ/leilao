angular.module('app.directives')
  .directive("desativarProduto", desativarProduto);

  desativarProduto.$inject = ['$rootScope'];

  function desativarProduto($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_desativarProduto.html'
      }
  }
