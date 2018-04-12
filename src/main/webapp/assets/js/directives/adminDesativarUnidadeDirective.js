angular.module('app.directives')
  .directive("desativarUnidade", desativarUnidade);

  desativarUnidade.$inject = ['$rootScope'];

  function desativarUnidade($rootScope){

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_desativarUnidade.html'
      }
  }
