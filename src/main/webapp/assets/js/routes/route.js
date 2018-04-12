angular.module('app.routes', ['ngRoute'])
.config(config);

config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];

function config($routeProvider, $locationProvider, $httpProvider){

  $routeProvider
  .when('/', {
    templateUrl: 'leilao/assets/js/view/login.html',
    controller: 'loginController',
    controllerAs: 'Login'
  })
  .when('/administrador', {
    templateUrl: 'leilao/assets/js/view/admin_main.html',
    controller: 'administradorController',
    controllerAs: 'Administrador'
  })
  .when('/usuario', {
    templateUrl: 'leilao/assets/js/view/user_main.html',
    controller: 'usuarioController',
    controllerAs: 'Usuario'
  })
  .otherwise ({ redirectTo: '/' });

  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

  // $locationProvider.html5Mode(true);
};
