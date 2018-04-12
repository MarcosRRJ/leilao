angular.module('app.directives')
  .directive("cadastrarProduto", cadastrarProduto);

  cadastrarProduto.$inject = ['$rootScope'];

  function cadastrarProduto($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_cadastrarProduto.html'
      }
  }
