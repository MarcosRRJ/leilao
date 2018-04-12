angular.module('app.directives')
  .directive("adminHome", adminHome);

  adminHome.$inject = ['$rootScope'];

  function adminHome($rootScope){
      $rootScope.title = 'Home'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_home.html'
      }
  }
