angular.module('app.directives')
  .directive("produtosLista", produtosLista);

  produtosLista.$inject = ['$rootScope'];

  function produtosLista($rootScope){
      $rootScope.title = 'Produtos'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_listaProduto.html'
      }
  }
