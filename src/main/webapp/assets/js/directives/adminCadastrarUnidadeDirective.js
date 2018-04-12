angular.module('app.directives')
  .directive("cadastrarUnidade", cadastrarUnidade);

  cadastrarUnidade.$inject = ['$rootScope'];

  function cadastrarUnidade($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_cadastrarUnidade.html'
      }
  }
