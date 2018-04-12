angular.module('app.controllers')
.controller("usuarioController", usuarioController);

usuarioController.$inject = ['$http', '$sessionStorage', '$rootScope', '$location', '$scope', '$interval', 'accessFactory'];

function usuarioController($http, $sessionStorage, $rootScope, $location, $scope, $interval, accessFactory){

  const vm = this;

  var accessData = window.sessionStorage['storageName'];
  vm.storageStorage = angular.fromJson(accessData);

  if (!vm.storageStorage || vm.storageStorage.tipo.id != 2)
   {
     $location.path('/');
  }else {


    var deuLance = window.sessionStorage['meuLance'];
    if (!deuLance) {
        vm.storageStorageDeulance = angular.fromJson(deuLance);
    }

    vm.showMessageError = false;

    vm.listaTodosLeiloesAndamentoUsuario = listaTodosLeiloesAndamentoUsuario(); /*Autoexecutável: Carrega quando o controller é chamado*/

    vm.leiloesUsuario = [];
    vm.listaTodosLeiloesAgendadosUsuario = listaTodosLeiloesAgendadosUsuario(); /*Autoexecutável: Carrega quando o controller é chamado*/
    vm.leiloesAgendadosUsuario = [];
    vm.listaProximosLeiloesIdLeilaoUsuario = listaProximosLeiloesIdLeilaoUsuario();
    vm.listaLeiloesArrematadosUsuario = listaLeiloesArrematadosUsuario(); /*Autoexecutável: Carrega quando o controller é chamado*/
    vm.leiloesArrematadosUsuario = [];

    vm.listaDeLeiloesArrematadoPorUmUser = listaDeLeiloesArrematadoPorUmUser(); /*Autoexecutável: Carrega quando o controller é chamado*/
    vm.listaDeLeiloesArrematadoPorUmUser = [];

    vm.pegaInfoRefresh = pegaInfoRefresh();
    vm.pegou = [];

    vm.lanceUsuarioEspecifico = lanceUsuarioEspecifico();
    vm.meuLanceUsuario = [];

    vm.pegaUmLeilaoArrematado = pegaUmLeilaoArrematado();

    vm.insertLeiilaoArrematado = insertLeiilaoArrematado;

    vm.darLance = darLance;
    vm.atualizarPerfil = atualizarPerfil;

    vm.pegatudoUser = [];

    vm.pegaQuantidadeLeilao = pegaQuantidadeLeilao();

    var deu = localStorage['lance'];
    vm.storageStorageDeulance = angular.fromJson(deu);





    $interval(function(){ /* O $interval cria uma rotina de atualização. */
      listaProximosLeiloesIdLeilaoUsuario();
      pegaInfoRefresh();
      pegaQuantidadeLeilao();
      pegaUmLeilaoArrematado();

      var date = new Date();
      var dataMenos = date.getTime() + 1500;
      vm.dataMenosMeio = dataMenos;


    }, 1000);

    var view = window.sessionStorage['menu']; //SessionStorage com retorno do objeto usuário que vem da função validar(login) do loginController
    vm.sessionStorageView = view;

    vm.activeMenu = active;
    if (vm.activeMenu) {
      vm.mActiveMenu = vm.sessionStorageView;
      if (!vm.sessionStorageView) {
        vm.mActiveMenu = 'Home';
      }
    }

    function active(menu) {
      vm.mActiveMenu = menu;

      window.sessionStorage['menu'] = vm.mActiveMenu;
      $rootScope.title =  vm.mActiveMenu;
    }

    function atualizarPerfil(usuario){
      const req = {
        url:     '/leilao/updateSenhaUsuario',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"id_usuario": vm.storageStorage.id_usuario, "senha": usuario.senha, "novaSenha": usuario.novaSenha}
      };


      $http(req)
      .then(
        function(res){
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
            vm.dialog.error = true;
          }else {
            vm.showMessageError = true;
            vm.attForm = {}; // limpa o formulário.
          }
          accessFactory.checkStatus(res.status);
                $('#perfil').modal('hide');
                $('#SenhaAlteradasucesso').modal('show');
                $scope.mensagemPerfilUser = '';
        },
        function(res) {
            $scope.mensagemPerfilUser = 'Senha atual incorreta ';
        }
      )
    }

    function pegaUmLeilaoArrematado(){
      const req = {
        url: '/leilao/umPegaUmLeilao',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso

        insertLeiilaoArrematado(res.data);

        },
        function(res){ // Retorno de erro

        }
      )

    }

    function listaTodosLeiloesAndamentoUsuario(){
      const req = {
        url:     '/leilao/listaLeiloesAndamento',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
      }

      $http(req)
      .then(
        function(res){
          vm.leiloesUsuario = res.data;

          var count = 0;

          angular.forEach(vm.leiloesUsuario, function(value, key){
            // console.log(key + ': ' + value);
            count += key + 1;
            vm.contouListaLeiloesArrematadosUsuario = count;
          });

          if (count == 0) {
            vm.contouListaLeiloesArrematadosUsuario = 0;
          }

        },
        function(res){

        }
      )
    }

    function listaTodosLeiloesAgendadosUsuario(){
      const req = {
        url:     '/leilao/listaProximosLeiloes',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
      }

      $http(req)
      .then(
        function(res){
          vm.leiloesAgendadosUsuario = res.data;

          var count = 0;

          angular.forEach(vm.leiloesAgendadosUsuario, function(value, key){
            // console.log(key + ': ' + value);
            count += key + 1;
            vm.contouListaLeiloesAgendado = count;

          });

          if (count == 0) {
            vm.contouListaLeiloesAgendado = 0;
          }

        },
        function(res){

        }
      )
    }

    function listaProximosLeiloesIdLeilaoUsuario(){
      const req = {
        url:     '/leilao/listaProximosLeiloesIdLeilao',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
      }

      $http(req)
      .then(
        function(res){


          var count = 0;
          var result = res.data

          angular.forEach(result, function(value, key){
            count += key + 1;


          });

          if (count != vm.contouListaLeiloesAgendado) {
            listaTodosLeiloesAgendadosUsuario();

          }

        },
        function(res){

        }
      )
    }

    function listaLeiloesArrematadosUsuario(){
      const req = {
        url: '/leilao/listaLeiloesArrematados',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
        vm.leiloesArrematadosUsuario = res.data;

        var count = 0;

        angular.forEach(vm.leiloesArrematadosUsuario, function(value, key){

          count = key + 1;
          vm.contouListaLeiloesArrematados = count;

        });

        if (count == 0) {
          vm.contouListaLeiloesArrematados = 0;
        }

        },
        function(res){ // Retorno de erro

        }
      )
    }

    function pegaQuantidadeLeilao(){
      const req = {
        url: '/leilao/pegaQuantidadeLeilao',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso

        var count = res.data;

        if (count != vm.contouListaLeiloesArrematados) {
          listaLeiloesArrematadosUsuario();
          listaDeLeiloesArrematadoPorUmUser();
        }

        },
        function(res){ // Retorno de erro

        }
      )
    }


    function pegaInfoRefresh(){
      const req = {
        url: '/leilao/pegaInfoRefresh',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
        // console.log(res.data);
        vm.pegou = res.data;

        var count = 0;

        angular.forEach(vm.pegou, function(value, key){
          count += key + 1;

        });

        if (count != vm.contouListaLeiloesArrematadosUsuario ) {
          listaTodosLeiloesAndamentoUsuario();

        }

        },
        function(res){ // Retorno de erro

        }
      )
    }

    function darLance(idLeilao){
      const req = {
        url:     '/leilao/darLance',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"idUsuario": vm.storageStorage.id_usuario, "idLeilao": idLeilao, "leilao": idLeilao}
      };


      $http(req)
      .then(
        function(res){ //resultado de sucesso

          window.sessionStorage['darLanceSessionStorage'] = angular.toJson(res.data);

          localStorage.lance = angular.toJson(res.data);
          localStorage['lance'] = angular.toJson(res.data);
          localStorage.setItem('lance', angular.toJson(res.data));


          vm.deuLance = res.data.idLeilao.idLeilao;

          listaTodosLeiloesAndamentoUsuario();
          lanceUsuarioEspecifico();
          pegaInfoRefresh();

        },

        function(res) { //erro

        }
      )

    }

    function lanceUsuarioEspecifico(){
      const req = {
        url:     '/leilao/pegaValorUltimoLanceUsuarioDeUmCertoLeilao',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"idUsuario": vm.storageStorage.id_usuario}
      }

      $http(req)
      .then(
        function(res){

            vm.meuLanceIdLeilao = res.data;
            // console.log(vm.meuLanceIdLeilao);

          },

        function(res){

        }
      )
    }


    function insertLeiilaoArrematado(idLeilao){
      const req = {
        url:    '/leilao/insertLeiilaoArrematado',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"leilao": idLeilao, "idLeilao": idLeilao}
      }
      $http(req)
      .then(
        function(res){ // Retorno de sucesso

          listaLeiloesArrematadosUsuario();
          listaDeLeiloesArrematadoPorUmUser();
        },
        function(res){ // Retorno de erro

          // listaDeLeiloesArrematadoPorUmUser();
        }
      )
    }

    function listaDeLeiloesArrematadoPorUmUser(){
      const req = {
        url: '/leilao/listaDeLeiloesArrematadoPorUmUser',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"netId": vm.storageStorage.netId}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
        vm.listaDeLeiloesArrematadoPorUmUser = res.data;

        },
        function(res){ // Retorno de erro

        }
      )
    }

  }
}
