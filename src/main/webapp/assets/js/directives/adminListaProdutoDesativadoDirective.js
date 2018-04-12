angular.module('app.directives')
  .directive("produtosListaDesativado", produtosListaDesativado);

  produtosListaDesativado.$inject = ['$rootScope'];

  function produtosListaDesativado($rootScope){
      $rootScope.title = 'Produtos Desativados'; //<title> </title>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_listaProdutoDesativado.html'
      }
  }
