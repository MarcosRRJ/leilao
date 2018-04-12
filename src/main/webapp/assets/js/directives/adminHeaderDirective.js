angular.module('app.directives')
  .directive("headerSidebarAdmin", headerSidebarAdmin);

  function headerSidebarAdmin(){
      return {
        restrict: 'E',
  			templateUrl: 'leilao/assets/js/view/admin_header.html'
      }
  }
