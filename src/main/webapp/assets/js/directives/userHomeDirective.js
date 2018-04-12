angular.module('app.directives')
  .directive("userHome", userHome);

  userHome.$inject = ['$rootScope'];

  function userHome($rootScope){
      $rootScope.title = 'Home'; //<title> </>

      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/user_home.html'
      }
  }
