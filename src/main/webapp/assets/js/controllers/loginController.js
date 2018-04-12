angular.module('app.controllers')
.controller("loginController", loginController);

loginController.$inject = ['$http', '$rootScope','$scope', 'accessFactory', '$cookies', '$location'];

function loginController($http, $rootScope, $scope,accessFactory, $cookies, $location){
  if ($rootScope.authenticated || !$rootScope.authenticated)
   {
    //  sessionStorage.removeItem(2);
     window.sessionStorage.clear();
      // window.sessionStorage["storageName"] = null;
  }

  const vm = this; // vm = view <-> model (conexão entre as partes)

  $rootScope.title = 'Login'; //<title> </>
  vm.showMessageError = false;

  /* SessionStorage */
  var accessData = window.sessionStorage['storageName'];
  vm.storageStorage = angular.fromJson(accessData);

  vm.validar = validar;
  vm.validar.success = true;
  vm.cadastrarUsuario = cadastrarUsuario;
  vm.testaCPF = testaCPF;

  vm.listaTodasUnidades = listaTodasUnidades(); //para deixar autoexecutável é só colocar o nome com parenteses no final
  vm.unidades = []; //Recebe a lista de retornor da função listaTodasUnidades();

  // vm.login = { //Caso queira deixar o login mocado
  //   netId: "adm", //pode ser o netid
  //   senha: "12345" // ou o email
  // }

  vm.authenticate = authenticate;



  function authenticate(credentials, callback) {
    var headers = credentials ?{
        authorization: "Basic " + btoa(credentials.netId + ":" + credentials.senha)
    }:{};
    var netId = credentials.netId;
    var senha = credentials.senha;

    $http.get('leilao/loginRest',{
    headers: headers,
    params: {"netId": netId, "senha": senha}
    })
    .then(
        function(res){
        if (res.data.netId) {
          $rootScope.authenticated = true;
          $rootScope.netId = res.data.netId;
          $rootScope.tipo = res.data.tipo.id;

          $cookies.put('id_usuario', res.data.id_usuario);
          $cookies.put('netId', res.data.netId);

          window.sessionStorage['storageName'] = angular.toJson(res.data);

          localStorage.User = angular.toJson(res.data);
          localStorage['User'] = angular.toJson(res.data);
          localStorage.setItem('User', angular.toJson(res.data));


          accessFactory.checkAccess(res.data);
          if (res.data.situacao ==0) {
              $('#usuarioDesativado').modal('show');
          }
          if (res.data.situacao ==2) {
             $('#usuarioEmEmpera').modal('show');
         }
          // console.log(res.data.token);
          $scope.mensagem = '';
        }else {
          $rootScope.authenticated = false;
        }
        callback && callback();
      },
      function(res){
        $rootScope.authenticated = false;
        callback && callback();
      }
    )
  };

  // vm.login = login;
  function validar(login) {
    authenticate($scope.credentials, function(){
      $scope.netId = $cookies.get('netId');
      if ($rootScope.authenticated) {
        if ($scope.situacao == 1) {
          if ($scope.tipo == 1) {
              $location.path('/administrador');
          }else {
            $location.path('/usuario');
          }
        }
      }else {
        $scope.mensagem = 'NetId ou Senha incorretos';
        $location.path('/');
      }
    });
  }


  function testaCPF(strCPF) {
      var Soma;
      var Resto;
      Soma = 0;
      if (strCPF == "00000000000") return false;
      if (strCPF == "11111111111") return false;
      if (strCPF == "22222222222") return false;
      if (strCPF == "33333333333") return false;
      if (strCPF == "44444444444") return false;
      if (strCPF == "55555555555") return false;
      if (strCPF == "66666666666") return false;
      if (strCPF == "77777777777") return false;
      if (strCPF == "88888888888") return false;
      if (strCPF == "99999999999") return false;

      for (i=1; i<=9; i++) Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (11 - i);
      Resto = (Soma * 10) % 11;

        if ((Resto == 10) || (Resto == 11))  Resto = 0;
        if (Resto != parseInt(strCPF.substring(9, 10)) ) return false;

      Soma = 0;
        for (i = 1; i <= 10; i++) Soma = Soma + parseInt(strCPF.substring(i-1, i)) * (12 - i);
        Resto = (Soma * 10) % 11;

        if ((Resto == 10) || (Resto == 11))  Resto = 0;
        if (Resto != parseInt(strCPF.substring(10, 11) ) ) return false;
        return true;
      }


  function cadastrarUsuario(usuario){

    if (vm.testaCPF(usuario.cpf) == false) {
      $('#cpf-erro').modal();
    }else {

      const req = {
        url: '/leilao/registrarUsuarioRest',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"nome": usuario.nome, "sobrenome": usuario.sobrenome, "email": usuario.email, "netId": usuario.netId, "cpf": usuario.cpf, "unidadeId": usuario.unidade.unidadeId, "senha": usuario.senha}
      }

      $http(req)
      .then(
        function(res){
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
            vm.dialog.error = true;
          }else {
            vm.showMessageError = true;
          }
          $('#cadastrarUsuario').modal('hide'); // Fecha o modal de cadastro
          $('#cadastrarUsuarioLoginSucesso').modal('show'); //Apresenta um modal de sucesso de cadastro
          vm.usuario = {}; // Limpa a variável usuário.
          accessFactory.checkStatus(res.status);
        },
        function(res){

          if (res.status == 409) {
              $('#cadastrarUsuarioLoginErroEmail').modal('show');
          }
          if (res.status == 451) {
            $('#cadastrarUsuarioLoginErroCPF').modal('show');
          }

          if (res.status == 400) {
            $('#cadastrarUsuarioLoginErroNetId').modal('show');
          }
        }
      )
    }
    // vm.usuario = {}; // Limpa a variável usuário.
    // vm.usuario.$setPristine(); // Set back to pristine.
    // vm.usuario.$setUntouched(); // Since Angular 1.3, set back to untouched state.
  }

  function listaTodasUnidades(){
    const req = {
      url: '/leilao/listaTodasUnidade',
      method: 'POST',
      headers: {'Content-Type': 'application/json;charset=utf-8'},
    }

    $http(req)
    .then(
      function(res){
        vm.unidades = res.data;
      },
      function(res){

      }
    )
  }


  // vm.cadastrarUnidade = cadastrarUnidade;
  // function cadastrarUnidade(unidadee){
  //   console.log("Entrou no cadastro de unidade =D");
  //   const req = {
  //     url: '/registrarUnidadeRest',
  //     method: 'POST',
  //     headers: {'Content-Type': 'application/json;charset=utf-8'},
  //     params: {"unidadeNome": unidadee.unidadeNome}
  //   }
  //
  //   console.log(req);
  //   $http(req)
  //   .then(
  //     function(res){ // Retorno de sucesso
  //       if(accessFactory.checkStatus(res.status)){
  //         vm.showMessageError = false;
  //         vm.dialog.error = true;
  //         listaTodasUnidade();
  //
  //       }else {
  //         vm.showMessageError = true;
  //       }
  //       accessFactory.checkStatus(res.status);
  //
  //       listaTodasUnidade();
  //     },
  //
  //     function(res){ // Retorno de erro
  //
  //     }
  //   )
  // }


}
