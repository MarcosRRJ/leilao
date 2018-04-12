angular.module('app.controllers')
.controller("administradorController", administradorController);

administradorController.$inject = ['$http','$timeout', '$interval', '$location', '$rootScope', '$route', '$scope', 'accessFactory','FileSaver', 'Blob', 'Upload', '$filter'];

function administradorController ($http,$timeout, $interval, $location, $rootScope, $route, $scope, accessFactory, FileSaver, Blob, Upload, $filter){



  const vm = this;

  // $scope.$on('$locationChangeStart', function(event) {
      // console.log("Não pode");
  //     // $location.path( "/login" );
  //     event.preventDefault();
  // });


  vm.showMessageError = false;

  /* SessionStorage */
  var accessData = window.sessionStorage['storageName']; //SessionStorage com retorno do objeto usuário que vem da função validar(login) do loginController
  vm.storageStorage = angular.fromJson(accessData);

  if (!vm.storageStorage || vm.storageStorage.tipo.id != 1)
   {
     $location.path('/');
  }else {



    /* Serviços */
    vm.downloadExcel = downloadExcel; //Gera excel de uma lista
    vm.ordenarConsultas = ordenarConsultas; //Ordena as tabelas

    /* CRUD de Usuário */
    vm.atualizarPerfil = atualizarPerfil; // Atualiza a senha do perfil
    vm.cadastrarUsuarioAdmin = cadastrarUsuarioAdmin; //Cadastra usuário
    vm.ativarUsuarioEmEspera = ativarUsuarioEmEspera; //Função ativa usuário em espera
    vm.ativarUsuarioDesativado = ativarUsuarioDesativado; //Função ativa usuário desativado
    vm.editarUsuario = editarUsuario; //Esta função cria uma cópia dos elementos para o formulário de editar usuario
    vm.atualizarUsuario = atualizarUsuario; //Atualiza usuário
    vm.desativarUsuario = desativarUsuario; //Desativa usuário
    vm.redefinirSenha = redefinirSenha; //Reset Senha usuário
    vm.pegaQuantidadeUsuarioEmEspera = pegaQuantidadeUsuarioEmEspera();// pega quantidade de Usuários em espera

    vm.listarTodosUsuarios = listarTodosUsuarios(); //Esta função é autexecutável (para deixar autoexecutável é só colocar o nome com parenteses no final) e serve para trazer a lista com todos os usuários
    vm.usuarios = []; //Variável irá receber lista de todos usuários

    vm.listarTodosUsuariosEspera = listarTodosUsuariosEspera(); //Esta função é autexecutável (para deixar autoexecutável é só colocar o nome com parenteses no final) e serve para trazer a lista com todos os usuários em espera
    vm.usuariosEspera = []; //Variável irá receber lista de todos usuários em espera

    vm.listarTodosUsuariosDesativados = listarTodosUsuariosDesativados(); //Esta função é autexecutável (para deixar autoexecutável é só colocar o nome com parenteses no final) e serve para trazer a lista com todos os usuários desativados
    vm.usuariosDesativados = []; //Variável irá receber lista de todos usuários desativados

    /* CRUD de Produto */
    vm.cadastrarProduto = cadastrarProduto; //Cadastra produto
    vm.editarProduto = editarProduto; //Esta função cria uma cópia dos elementos para o formulário de editar produto
    vm.atualizarProduto = atualizarProduto; //Atualiza produto
    vm.desativarProduto = desativarProduto; //Desativa produto
    vm.listarTodosProdutosDesativados = listarTodosProdutosDesativados(); //Esta função é autexecutável (para deixar autoexecutável é só colocar o nome com parenteses no final) e serve para trazer a lista com todos os usuários desativados
    vm.produtoDesativados = []; //Variável irá receber lista de todos usuários desativados
    vm.ativarProdutoDesativado = ativarProdutoDesativado;

    vm.ativarProduto = ativarProduto(); //Envia o produto de volta para a fila, caso não tenha recebido lances
    vm.listarTodosProdutos = listarTodosProdutos(); //para deixar autoexecutável é só colocar o nome com parenteses no final
    vm.produtos = [];

    /* CRUD de Unidade */
    vm.cadastrarUnidade = cadastrarUnidade; //Cadastra unidade
    vm.editarUnidade = editarUnidade; //Esta função cria uma cópia dos elementos para o formulário de editar unidade
    vm.atualizarUnidade = atualizarUnidade; //Atualiza unidade
    vm.desativarUnidade = desativarUnidade; //Desativa Unidade
    vm.reativarUnidade = reativarUnidade; //reativar Unidade

    vm.listaTodasUnidade = listaTodasUnidade(); //para deixar autoexecutável é só colocar o nome com parenteses no final
    vm.unidades = [];
    vm.listaTodasUnidadeDesativadas = listaTodasUnidadeDesativadas();
    vm.unidadesDesativadas = [];

    /* CRUD de Leilão */
    vm.iniciarLeilao = iniciarLeilao;
    // vm.listaLeiloesArrematados = listaLeiloesArrematados;
    vm.editarLeilao = editarLeilao;
    vm.atualizarLeilao = atualizarLeilao;
    vm.desativarLeilao = desativarLeilao;

    vm.updateDesativaLeilao = updateDesativaLeilao;

    vm.selecionarLeiloes = selecionarLeiloes;

    vm.listaTodosLeiloesAndamento = listaTodosLeiloesAndamento();
    vm.leiloes = []; //Recebe a lista de retornor da função listaTodosLeiloesAndamento();
    vm.pegaInfoRefresh = pegaInfoRefresh();
    vm.pegou = [];
    vm.listaTodosLeiloesAgendados = listaTodosLeiloesAgendados();
    vm.leiloesAgendados = []; //Recebe a lista de retornor da função listaTodosLeiloesAgendados();
    vm.listaProximosLeiloesIdLeilao = listaProximosLeiloesIdLeilao();
    vm.listaLeiloesArrematados = listaLeiloesArrematados();
    vm.leiloesArrematados = []; //Recebe a lista de retornor da função listaLeiloesArrematados();

    vm.pegatudo = [];

    /* Praâmetros */
    vm.cadastrarAcrescimo = cadastrarAcrescimo;
    vm.cadastrarQuantidadeLeilao = cadastrarQuantidadeLeilao;

    vm.listarTempoAcrescimo = listarTempoAcrescimo();
    vm.tempoDeAcrescimoAtual = []; // Recebe o valor de retorno da função: listarTempoAcrescimo();
    vm.listarQtdLeiloesAtual = listarQtdLeiloesAtual();
    vm.quantidadeDeLeiloesSimultaneosAtual = []; // Recebe o valor de retorno da função: listarQtdLeiloesAtual();

    vm.pegaQuantidadeLeilao = pegaQuantidadeLeilao();
    vm.pegaUmLeilaoArrematado = pegaUmLeilaoArrematado();

    $interval(function(){ /* O $interval cria uma rotina de atualização. */
       listaProximosLeiloesIdLeilao();
  	   pegaInfoRefresh();
       pegaQuantidadeLeilao();
       pegaUmLeilaoArrematado();

  	  }, 2000);

      $interval(function(){ /* O $interval cria uma rotina de atualização. */
         pegaQuantidadeUsuarioEmEspera();// pega quantidade de Usuários em espera
       }, 10000);



    var view = window.sessionStorage['menu']; // grava a view atual em uma sessão chamad menu
    vm.sessionStorageView = view;

    vm.activeMenu = active;

    if (vm.activeMenu) { // seta a sessão menu para inicar como home
      vm.mActiveMenu = vm.sessionStorageView;
      if (!vm.sessionStorageView) {
        vm.mActiveMenu = 'Home';
      }
    }


    function active(menu) { // altera a sessão menu para page Selecionada
      vm.mActiveMenu = menu;

      window.sessionStorage['menu'] = vm.mActiveMenu;
      $rootScope.title =  vm.mActiveMenu;

    }


    function ordenarConsultas(order){ //Esta função funciona - ou deveria - nos for eachs (ng-repeat, dir-pagination) para ordernar a pesquisa.
      vm.sortKey = order;
      vm.reverse = !vm.reverse;
    }

    function selecionarLeiloes(leilao, index){
      vm.product = angular.copy(leilao);
    }

    function editarUsuario(usuario, index){ //É chamada na view: admin_listaUsuario.html, admin_listaUsuarioDesativado.html e admin_listaUsuarioEspera.html
      vm.form = angular.copy(usuario); //Cria uma cópia do conteúdo para ser enviada para a admin_editarUsuario.html
      vm.form.index = usuario.id_usuario;
    }

    function editarProduto(produto, index){ //É chamada na view: admin_listaProduto.html
      vm.formulario = angular.copy(produto); //Cria uma cópia do conteúdo para ser enviada para a admin_editarProduto.html
      vm.formulario.index = produto.idProduto;
    }

    function editarUnidade(unidade, index){ //É chamada na view: admin_listaProduto.html
      vm.editUnit = angular.copy(unidade); // Cria uma cópia do conteúdo para ser enviada para a admin_editarProduto.html
      vm.editUnit.index = unidade.unidadeId;
    }

    function editarLeilao(leilao, index){ //É chamada na view: admin_iniciarLeilao.html
      vm.formularioLeilao = angular.copy(leilao); //Cria uma cópia do conteúdo para ser enviada para a admin_editarLeilao.html
      vm.formularioLeilao.index = leilao.idLeilao;
      $('#editarLeilao').modal('show');
    }

    function desativarLeilao(leilao, index){ //É chamada na view: home.html
      vm.formularioDesativaLeilao = angular.copy(leilao); //Cria uma cópia do conteúdo para ser enviada para a admin_desativarLeilao.html
      vm.formularioDesativaLeilao.index = leilao.idLeilao;
    }

    vm.countPlaylistUsuario = 0;
    vm.selectedSongs = function () { //funcão responsavel por pegar os usuarios em Espera pelo chekbox ativos
      var count = 0;

      vm.playList = $filter('filter')(vm.usuariosEspera, {checked: true});
      // console.log(vm.playList);

      angular.forEach(vm.playList, function(value, key) {
        count = key + 1;
      });
      vm.countPlaylistUsuario = count;
    }

    vm.checkAll = function() { //funcão responsavel por pegar TOdos os usuarios em Espera
      var count = 0;
      vm.playListTudo = angular.copy(vm.usuariosEspera);
      angular.forEach(vm.playListTudo, function(value, key) {
        count = key + 1;
      });
      vm.countPlaylist = count;
    };

    vm.ativarUsuCheck = ativarUsuCheck;//funcão responsavel por ativar Todos os usuarios em Espera com checkbox ativos
    function ativarUsuCheck() {
      angular.forEach(vm.playList, function(value, key) {
        ativarUsuarioEmEspera(value);
        vm.countPlaylistUsuario = "";
      });
    }
    vm.ativarTodosUsuCheck = ativarTodosUsuCheck;//funcão responsavel por ativar Todos os usuarios em Espera
    function ativarTodosUsuCheck() {
      angular.forEach(vm.playListTudo, function(value, key) {
        ativarUsuarioEmEspera(value);
      });
    }

    vm.playListLeilao = []; //Recebe a lista de retornor da função lista de todos leiloes do checkox();
    vm.countPlaylistLeilao = 0;
    vm.selectedLeilao = function () { //funcão responsavel por pegar os Leilões em Espera pelo chekbox
      var count = 0;

      vm.playListLeilao = $filter('filter')(vm.produtos, {checked: true});
      // console.log(vm.playList);

      vm.contaLeilaoSemData = 0;//conta o numero de Leilões sem data de Inicio

      angular.forEach(vm.playListLeilao, function(value, key) {
        count = key + 1;
        if (!value.dataInicio) {
            vm.contaLeilaoSemData = vm.contaLeilaoSemData + 1
        }
      });
      vm.countPlaylistLeilao = count;
    }

    vm.ativarTodosLeiloesCheck = ativarTodosLeiloesCheck; //funcão responsavel por ativar Todos os usuarios em Espera
    function ativarTodosLeiloesCheck() {
      if (vm.contaLeilaoSemData > vm.quantidadeDeLeiloesSimultaneosAtual) {

        $('#iniciarLeilaoError4').modal('show');
      }
      else {
        angular.forEach(vm.playListLeilao, function(value, key) {
          iniciarLeilao(value);

        });
      }

    }

    function pegaUmLeilaoArrematado(){ //pega sempre o ultimo leilão arrematado para fazer o insert na tabela
      const req = {
        url: '/leilao/umPegaUmLeilao',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso

        insertLeiilaoArrematado(res.data);
        // listaLeiloesArrematados();
        },
        function(res){ // Retorno de erro

        }
      )

    }

    function insertLeiilaoArrematado(idLeilao){ // aqui fazer o insert do leilão Arrematado
      const req = {
        url:    '/leilao/insertLeiilaoArrematado',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params:  {"leilao": idLeilao, "idLeilao": idLeilao}
      }
      $http(req)
      .then(
        function(res){ // Retorno de sucesso


          // listaLeiloesArrematados();
        },
        function(res){ // Retorno de erro

          // listaDeLeiloesArrematadoPorUmUser();
        }
      )
    }


    function downloadExcel(){ // download do arquivo Excel gerado no java dos leiloes_Arrematados
      var downloadPath = '/leilao/leiloes_Arrematado.xls';
      var newWindowRef = window.open(downloadPath, '_blank', '');
      if (newWindowRef) {
          if (newWindowRef.document.body) { // not working on IE
              newWindowRef.document.title = "Downloading ...";
              newWindowRef.document.body.innerHTML = '<h4>Your file is generating ... please wait</h4>';
            //   console.log("entrou aqui");
          }

          var interval = setInterval(function() {
              if (!!newWindowRef.closed) {
                  // Downloading completed
                  clearInterval(interval);
                  // console.log("esta aqui");
              }
          }, 1000);
      } else {
          $log.error("Opening new window is probably blocked");
      }
    };


    function atualizarPerfil(usuario){ // atualizar senha do usuario
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
          }
          accessFactory.checkStatus(res.status);
                $('#perfil').modal('hide');
                $('#SenhaAlteradasucesso').modal('show');
                vm.attForm = {}; // limpa o formulário.
                $scope.mensagemPerfil = '';
        },
        function(res) {
            $scope.mensagemPerfil = 'Senha atual incorreta ';
        }
      )
    }

    function cadastrarAcrescimo(acrescimo){ // Altera o tempo de Acrescimo dos leiloes
      if (!acrescimo) {
          $('#desativarErro').modal('show');
      }else {
        const req = {
          url:    '/leilao/updateParametrosTempoAcrescimo',
          method: 'POST',
          headers: {'Content-Type': 'application/json;charset=utf-8'},
          params:  {"tempoAcrescimo": acrescimo.tempoAcrescimo}
        }
        $http(req)
        .then(
          function(res){ // Retorno de sucesso
            listarTempoAcrescimo();
            listarQtdLeiloesAtual();
          },
          function(res){ // Retorno de erro
            $('#desativarErro').modal('show');
          }
        )
        vm.tempo = {}; // Reset the form model.
        // vm.tempo.$setPristine(); // Set back to pristine.
        // vm.tempo.$setUntouched(); // Since Angular 1.3, set back to untouched state.
      }

    }

    function cadastrarQuantidadeLeilao(qtd){ // altera a quantidade De Leiloes Simultaneos
      if (!qtd) {
        $('#desativarErro').modal('show');
      }else {
        const req = {
          url:    '/leilao/updateParametrosLeiloesSimultaneos',
          method: 'POST',
          headers: {'Content-Type': 'application/json;charset=utf-8'},
          params:  {"leiloesSimultaneos": qtd.qtdLeilao}
        }
        $http(req)
        .then(
          function(res){ // Retorno de sucesso
            listarQtdLeiloesAtual();
            listarTempoAcrescimo();
          },
          function(res){ // Retorno de erro
            $('#desativarErro').modal('show');
          }
        )
        vm.qtdL = {}; // Reset the form model.
        // vm.qtdL.$setPristine(); // Set back to pristine.
        // vm.qtdL.$setUntouched(); // Since Angular 1.3, set back to untouched state.

      }

    }


    function listarTempoAcrescimo(){ // pega o Tempo Acrescimo
      const req = {
        url: '/leilao/pegaTempoAcrescimo',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.tempoDeAcrescimoAtual = res.data;
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarQtdLeiloesAtual(){ // pega a quantidade De Leiloes Simultaneos
      const req = {
        url: '/leilao/pegaVariavelDeLeiloes',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.quantidadeDeLeiloesSimultaneosAtual = res.data;
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarTodosUsuarios(){ // lista todos Usuários Ativos
      const req = {
        url: '/leilao/listaTodosUserAtivos',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8',
  //    	  		'Authorization': 'Bearer ' + vm.storageStorage.token,
        }
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.usuarios = res.data;
          // console.log("TODOS USUÁRIOS: " + vm.usuarios);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarTodosUsuariosEspera(){ // lista todos Usuários em ESPERA
      const req = {
        url: '/leilao/listaTodosUserEmEspera',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.usuariosEspera = res.data;
          // console.log("TODOS USUÁRIOS EM ESPERA: " + vm.usuariosEspera);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarTodosUsuariosDesativados(){ // lista todos Usuários DESATIVADOS
      const req = {
        url: '/leilao/listaTodosUserDesativato',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.usuariosDesativados = res.data;
          // console.log("TODOS USUÁRIOS DESATIVADOS: " + vm.usuariosDesativados);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarTodosProdutos(){ // lista todos Produtos Ativos
      const req = {
        url: '/leilao/listaTodosProdutos',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.produtos = res.data;
          // console.log("TODOS PRODUTOS: " + vm.produtos);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listarTodosProdutosDesativados(){ // lista todos Produtos DESATIVADOS
      const req = {
        url: '/leilao/listaTodosProdutosDesativados',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.produtosDesativados = res.data;
          // console.log("TODOS USUÁRIOS DESATIVADOS: " + vm.usuariosDesativados);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function ativarProdutoDesativado(produto){ // responsivel por reativar Produto
      // console.log("Entrou na validação de usuário desativado =D");
      const req = {
        url: '/leilao/updateAtivarProdutoDesativado',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"idProduto": produto.idProduto}
      }

      // console.log(req);
      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
            vm.dialog.error = true;
          }else {
            vm.showMessageError = true;
          }
          $('#reativarProduto').modal('show');
          accessFactory.checkStatus(res.status);
          listarTodosProdutosDesativados();
          listarTodosProdutos();
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listaTodasUnidade(){ // lista todos Unidades ATIVADAS
      const req = {
        url: '/leilao/listaTodasUnidade',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.unidades = res.data;
          // console.log("TODAS UNIDADES: " + vm.unidades);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listaTodasUnidadeDesativadas(){ // lista todos Unidades DESATIVADAS
      const req = {
        url: '/leilao/listaTodasUnidadeDesativadas',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.unidadesDesativadas = res.data;
          // console.log("TODAS UNIDADES: " + res.data);
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listaTodosLeiloesAgendados(){ // lista todos Leilões AGENDADOS
      const req = {
        url: '/leilao/listaProximosLeiloes',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          vm.leiloesAgendados = res.data;
          // console.log("TODOS LEILÕES AGENDADOS: " + vm.leiloesAgendados);

          var count = 0;

          angular.forEach(vm.leiloesAgendados, function(value, key){
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

    function listaProximosLeiloesIdLeilao(){ // lista todos Leilões em Andamento
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
            listaTodosLeiloesAgendados();

          }

        },
        function(res){

        }
      )
    }

    function listaTodosLeiloesAndamento(){ // lista todos Leilões em Andamento
      const req = {
        url:     '/leilao/listaLeiloesAndamento',
        method:  'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
      }

      $http(req)
      .then(
        function(res){
          vm.leiloes = res.data;
          pegaUmLeilaoArrematado();

          var count = 0;

          angular.forEach(vm.leiloes, function(value, key){
            // console.log(key + ': ' + value);
            count += key + 1;
            vm.contouListaLeiloesAndamento = count;
          });

          if (count == 0) {
            vm.contouListaLeiloesAndamento = 0;
          }

        },
        function(res){

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

        if (count != vm.contouListaLeiloesAndamento ) {
          listaTodosLeiloesAndamento();
        }

        },
        function(res){ // Retorno de erro

        }
      )
    }

    function listaLeiloesArrematados(){
      const req = {
        url: '/leilao/listaLeiloesArrematados',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
        vm.leiloesArrematados = res.data;

        var count = 0;

        angular.forEach(vm.leiloesArrematados, function(value, key){

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
          listaLeiloesArrematados();
        }

        },
        function(res){ // Retorno de erro

        }
      )
    }

    function pegaQuantidadeUsuarioEmEspera(){
      const req = {
        url: '/leilao/pegarQtdUsuariosEmEspera',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso

          var count = res.data;

          if (count != vm.usuariosEspera.length) {
            listarTodosUsuariosEspera();
          }

        },

        function(res){ // Retorno de erro

        }
      )
    }



    function redefinirSenha(usuario){
      // console.log("Entrou na Função de resetar senha! PEGA O ID: " + usuario.id_usuario);

      const req = {
        url: '/leilao/updateSenha',
        method: 'PUT',
        data: 'id_usuario='+usuario.id_usuario,
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
          }else {
            vm.showMessageError = true;
          }
          accessFactory.checkStatus(res.status);
          $('#editarUsuario').modal('hide'); //Este jQuery fecha a modal de edição
          $('#redefinirSenhaUsuarioSucesso').modal('show'); //Este jQuery exibe a modal de sucesso (senha redefinida)
        },
        function(res){ // Retorno de erro
          $('#redefinirSenhaUsuarioErro').modal('show'); //Este jQuery exibe a modal de sucesso (senha redefinida)
            $scope.mensagem = 'netId / Senha incorretos ou seu cadrastro ainda esta em analise';
        }
      )
    }

    vm.testaCPF = testaCPF;
    function testaCPF(strCPF) {
      strCPF = strCPF.toString();
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

    function cadastrarUsuarioAdmin(usuario){
      if (vm.testaCPF(usuario.cpf) == false) {
        $('#adm-cpf-erro').modal();
      }else {
        if(vm.usuario.unidade == null){
          $scope.mensagemCadAdmin = 'Selecione uma unidade';
        }
        // console.log("Entrou no cadastro de usuário via admin =D");
        const req = {
          url: '/leilao/registrarUsuarioViaAdminRest',
          method: 'POST',
          headers: {'Content-Type': 'application/json;charset=utf-8'},
          params: {"nome": usuario.nome, "sobrenome": usuario.sobrenome, "email": usuario.email, "netId": usuario.netId, "cpf": usuario.cpf, "unidadeId": usuario.unidade.unidadeId, "senha": usuario.senha}
        }

        // console.log(req);
        $http(req)
        .then(
          function(res){ // Retorno de sucesso
            if(accessFactory.checkStatus(res.status)){
              vm.showMessageError = false;
              vm.dialog.error = true;
            }else {
              vm.showMessageError = true;
            }
            accessFactory.checkStatus(res.status);
            $('#cadastrarUsuario').modal('hide');
            $('#cadastrarUsuarioSucesso').modal('show');
            listarTodosUsuarios();
            $scope.mensagemCadAdmin = '';
            vm.usuario = {}; // limpa o formulário.
          },
          function(res){

            if (res.status == 409) {
                $('#cadastrarUsuarioErroEmail').modal('show');
            }
            if (res.status == 451) {
              $('#cadastrarUsuarioErroCPF').modal('show');
            }

            if (res.status == 400) {
              $('#cadastrarUsuarioErroNetId').modal('show');
            }
          }
        )
      }
    }

    function ativarUsuarioDesativado(usuario){
      // console.log("Entrou na validação de usuário desativado =D");
      const req = {
        url: '/leilao/updateAtivarUsuario',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"id_usuario": usuario.id_usuario}
      }

      // console.log(req);
      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
            vm.dialog.error = true;
          }else {
            vm.showMessageError = true;
          }
          $('#reativarUsuario').modal('show');
          accessFactory.checkStatus(res.status);
          listarTodosUsuariosDesativados();
          listarTodosUsuariosEspera();
          listarTodosUsuarios();
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function ativarUsuarioEmEspera(usuario){
      // console.log("Entrou na ativação de usuário =D");
      const req = {
        url: '/leilao/updateAtivarUsuario',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"id_usuario": usuario.id_usuario}
      }

      // console.log(req);
      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
            vm.dialog.error = true;
          }else {
            vm.showMessageError = true;
          }
          $('#validarUsuario').modal('show');
          accessFactory.checkStatus(res.status);
          listarTodosUsuariosEspera();
          listarTodosUsuariosDesativados();
          listarTodosUsuarios();
          vm.playList = "";
          vm.countPlaylistUsuario = "";
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function atualizarUsuario(usuario){
      if (vm.testaCPF(usuario.cpf) == false) {
        $('#adm-cpf-erro').modal();
      }else {

        var form = vm.form.unidadeId;
        if (usuario.unidadeId.unidadeId == null) {

          const req = {
            url: '/leilao/updateUsuario',
            method: 'POST',
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            params: {"id_usuario": usuario.id_usuario, "nome": usuario.nome, "sobrenome": usuario.sobrenome, "email": usuario.email, "netId": usuario.netId, "cpf": usuario.cpf, "unidadeId": usuario.unidadeId, "senha": usuario.senha}
          }

          $http(req)
          .then(
            function(res){ // Retorno de sucesso
              if(accessFactory.checkStatus(res.status)){
                vm.showMessageError = false;
              }else {
                vm.showMessageError = true;
              }
              $('#editarUsuario').modal('hide');
              accessFactory.checkStatus(res.status);
              listarTodosUsuarios();
            },
            function(res){ // Retorno de erro

            }

          )
        }else{
          const req = {
            url: '/leilao/updateUsuarioSemUnidade',
            method: 'POST',
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            params: {"id_usuario": usuario.id_usuario, "nome": usuario.nome, "sobrenome": usuario.sobrenome, "email": usuario.email, "netId": usuario.netId, "cpf": usuario.cpf, "senha": usuario.senha}
          }

          $http(req)
          .then(
            function(res){ // Retorno de sucesso
              if(accessFactory.checkStatus(res.status)){
                vm.showMessageError = false;
              }else {
                vm.showMessageError = true;
              }
              $('#editarUsuario').modal('hide');
              accessFactory.checkStatus(res.status);
              listarTodosUsuarios();
            },
            function(res){ // Retorno de erro

            }

          )

        }
      }
    }

    function desativarUsuario(usuario){
      // console.log("DESATIVA! PEGA O ID: " + usuario.id_usuario);
      const req = {
        url: '/leilao/updateDestivarUsuario',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"id_usuario": usuario.id_usuario}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
          }else {
            vm.showMessageError = true;
          }
          $('#editarUsuario').modal('hide'); //Este jQuery fecha a modal de edição
          $('#desativarUsuarioSucesso').modal('show'); //Este jQuery abre a modal com mensagem de sucesso
          accessFactory.checkStatus(res.status);
          listarTodosUsuarios();
          listarTodosUsuariosDesativados();
          listarTodosUsuariosEspera();
        },
        function(res){ // Retorno de erro
          $('#desativarUsuarioErro').modal('show'); //Este jQuery abre a modal com mensagem de erro
        }
      )
    }

    function cadastrarUnidade(unidadee){
      // console.log("Entrou no cadastro de unidade =D");
      if (!unidadee || !unidadee.unidadeNome ) {
        $('#desativarErro').modal('show');
      }else {
        const req = {
          url: '/leilao/registrarUnidadeRest',
          method: 'POST',
          headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
          params: {"unidadeNome": unidadee.unidadeNome}
        }

        // console.log(req);
        $http(req)
        .then(
          function(res){ // Retorno de sucesso
            if(accessFactory.checkStatus(res.status)){
              vm.showMessageError = false;
              vm.dialog.error = true;
              listaTodasUnidade();

            }else {
              vm.showMessageError = true;
            }
            accessFactory.checkStatus(res.status);

            listaTodasUnidade();
          },

          function(res){ // Retorno de erro

          }
        )
        vm.unidade = {}; // Reset the form model.
        // vm.unidade.$setPristine(); // Set back to pristine.
        // vm.unidade.$setUntouched(); // Since Angular 1.3, set back to untouched state.

      }

    }

    function atualizarUnidade(unidade){
      const req = {
        url: '/leilao/updateUnidade',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"unidadeId": unidade.unidadeId, "unidadeNome": unidade.unidadeNome}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
          }else {
            vm.showMessageError = true;
          }
          listaTodasUnidade();
          accessFactory.checkStatus(res.status);
          $('#editarUnidade').modal('hide');
        },
        function(res){ // Retorno de erro

        }

      )
    }

    function desativarUnidade(unidade){
      // console.log("DESATIVA! PEGA O ID: " + unidade.unidadeId);
      const req = {
        url: '/leilao/updateDestivarUnidade',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"unidadeId": unidade.unidadeId}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
          }else {
            vm.showMessageError = true;
          }
          $('#editarUnidade').modal('hide');
          $('#desativarUnidadeSucesso').modal('show');
          accessFactory.checkStatus(res.status);
          listaTodasUnidade();
          listaTodasUnidadeDesativadas();
        },
        function(res){ // Retorno de erro
          $('#desativarErro').modal('show');
        }
      )
    }

    function reativarUnidade(unidade){
  // console.log("DESATIVA! PEGA O ID: " + unidade.unidadeId);
  const req = {
    url: '/leilao/updateReativarUnidade',
    method: 'POST',
    headers: {'Content-Type': 'application/json;charset=utf-8'},
    params: {"unidadeId": unidade.unidadeId}
  }

  $http(req)
  .then(
    function(res){ // Retorno de sucesso
      if(accessFactory.checkStatus(res.status)){
        vm.showMessageError = false;
      }else {
        vm.showMessageError = true;
      }
      $('#uniDesativadas').modal('hide');
      $('#reativadoUnidadeSucesso').modal('show');
      accessFactory.checkStatus(res.status);
      listaTodasUnidade();
    },
    function(res){ // Retorno de erro
      $('#desativarErro').modal('show');
    }
  )
}

    function ativarProduto(){
      const req = {
          url:     '/leilao/updateAtivarProduto',
          method:  'POST',
          headers: {'Content-Type': 'application/json;charset=utf-8'}
      }

      $http(req)
        .then(
          function(res){
            // console.log("Deu certo updateAtivarProduto: ");
            // console.log(res.data);
          },
          function(res) {
            // console.log("Deu errado updateAtivarProduto: ");
            // console.log(res.data);
          }
        )

    }


    vm.uploadFile = uploadFile;
    function uploadFile(produto){ // import de um arquivo Excel
  	  var fd = new FormData();

  	  var nome = vm.produto.nomeProduto;
      var descricao = vm.produto.descricao;
      var cod = vm.produto.codPatrimonio;
      if(vm.produto.unidade == null){
       $scope.mensagemProdutoUnidade = 'Selecione uma unidade';
     }
      var uni = vm.produto.unidade.unidadeId;
      var valorIn = vm.produto.valorInicial;
      var valorPor = vm.produto.valorPorLance;
      var file = vm.produto.file;

      fd.append('file', file);

  	  $http.post('/leilao/registrarProdutoRest', fd, {
  			transformRequest: angular.identity,
  			headers: {'Content-Type': undefined},
        params: {"nomeProduto": nome, "descricao": descricao, "codPatrimonio": cod, "unidadeId": uni , "valorInicial": valorIn, "valorPorLance": valorPor}
  		}).then(
           function(res){ // Retorno de sucesso
             if(accessFactory.checkStatus(res.status)){
               vm.showMessageError = false;
               vm.dialog.error = true;
                //  alert("Hello! I am an alert box!!");
             }else {
               vm.showMessageError = true;
               vm.produto = {}; // Reset the form model.
             }
             angular.element("input[type='file']").val(null);
             $('#cadastrarProduto').modal('hide');
             $('#cadProdutoSucess').modal('show');
            //  $('#file').trigger("reset");
            accessFactory.checkStatus(res.status);
             listarTodosProdutos();
           },
           function(res){ // Retorno de erro
              if(res.status == 400) {
                $scope.mensagemProduto = 'Produto já existe';
              }
           }
         )

        //  vm.produto.$setPristine(); // Set back to pristine.
        //  vm.produto.$setUntouched(); // Since Angular 1.3, set back to untouched state.
       };



  vm.uploadFileExcel = uploadFileExcel;
  function uploadFileExcel(){ // import de um arquivo Excel
  	  var fd = new FormData();
  	  var file = vm.file;
      if (!file) {
        $('#importarExcelError').modal('show');
      }else {
        fd.append('file', file);
        // console.log(file);
        $http.post('/leilao/importExcel', fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
        }).then(
             function(res){ // Retorno de sucesso
                angular.element("input[type='file']").val(null);
                   $('#importarExcelSucesso').modal('show');
                  //  console.log(res.data);
                   vm.excel = res.data;

                     listarTodosProdutos();

             },
             function(res){ // Retorno de erro
                if(res.status == 404) {
                   $('#importarExcelError').modal('show');
                  // $scope.mensagemErrImport = 'Selecione um arquivo';
                }
             }
           )
      }

    };

       function atualizarProduto(produto){
         var fd = new FormData();

         var id = produto.idProduto;
         var nome = produto.nomeProduto;
         var descricao = produto.descricao;
         var cod = produto.codPatrimonio;
         var uni = produto.unidadeId.unidadeId;
         var valorIn = produto.valorInicial;
         var valorPor = produto.valorPorLance;
         var file = produto.file;

         var form = vm.formulario.unidadeId;

         var file = produto.file;

         if (uni != null) {

           if(file != null)  { // aqui atualiza o produto com TOdos seus atributos
             fd.append('file', file);
             $http.post('/leilao/updateProduto', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined},
                params: {"idProduto": id, "nomeProduto": nome, "descricao": descricao, "codPatrimonio": cod, "unidadeId": uni, "valorInicial": valorIn, "valorPorLance": valorPor}
              })
              .then(
                function(res){ // Retorno de sucesso
                  if(accessFactory.checkStatus(res.status)){
                    vm.showMessageError = false;
                  }else {
                    vm.showMessageError = true;
                    vm.produto = {}; // Reset the form model.
                  }
                  angular.element("input[type='file']").val(null);
                  $('#editarProduto').modal('hide');
                   $('#updateProdutoSucess').modal('show');
                  accessFactory.checkStatus(res.status);
                  listarTodosProdutos();
                  vm.formulario = {}; // Reset the form model.

                },
                function(res){ // Retorno de erro
                  if(res.status == 400) {
                  $scope.mensagemProdutoEdit = 'Verifique o formulario';
                }

                }
              )
           }
           else { // aqui atualiza o produto com TOdos seus atributos
             $http.post('/leilao/updateProdutoSemFile', fd,{
               headers: {'Content-Type': 'application/json;charset=utf-8'},
               params: {"idProduto": id, "nomeProduto": nome, "descricao": descricao, "codPatrimonio": cod, "unidadeId": uni, "valorInicial": valorIn, "valorPorLance": valorPor}
              })
              .then(
                function(res){ // Retorno de sucesso
                  if(accessFactory.checkStatus(res.status)){
                    vm.showMessageError = false;
                  }else {
                    vm.showMessageError = true;
                    vm.formulario = {}; // Reset the form model.
                  }
                  $('#editarProduto').modal('hide');
                   $('#updateProdutoSucess').modal('show');
                  accessFactory.checkStatus(res.status);
                  listarTodosProdutos();
                },
                function(res){ // Retorno de erro
                  if(res.status == 400) {
                  $scope.mensagemProdutoEdit = 'Verifique o formulario';
                }
                }
              )
           }
         }else { // aqui atualiza o produto com TOdos seus atributos mas sem unidade
           $http.post('/leilao/updateProdutoSemUnidade', fd,{
             headers: {'Content-Type': 'application/json;charset=utf-8'},
             params: {"idProduto": id, "nomeProduto": nome, "descricao": descricao, "codPatrimonio": cod, "valorInicial": valorIn, "unidadeId": form, "valorPorLance": valorPor}
            })
            .then(
              function(res){ // Retorno de sucesso
                if(accessFactory.checkStatus(res.status)){
                  vm.showMessageError = false;
                }else {
                  vm.showMessageError = true;
                  vm.formulario = {}; // Reset the form model.
                }
                $('#editarProduto').modal('hide');
                $('#updateProdutoSucess').modal('show');
                accessFactory.checkStatus(res.status);
                listarTodosProdutos();
              },
              function(res){ // Retorno de erro
                if(res.status == 400) {
                $scope.mensagemProdutoEdit = 'Verifique o formulario';
              }
              }
            )
         }
       }

    function desativarProduto(produto){
      // console.log("DESATIVA! PEGA O ID: " + produto.idProduto);
      const req = {
        url: '/leilao/updateDestivarProduto',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"idProduto": produto.idProduto}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;

          }else {
            vm.showMessageError = true;
          }
          accessFactory.checkStatus(res.status);
          $('#editarProduto').modal('hide');
          $('#desativaProdutoSucess').modal('show');
          listarTodosProdutos();
          listarTodosProdutosDesativados();
        },
        function(res){ // Retorno de erro

        }
      )
    }

    function iniciarLeilao(leilao){

      if (vm.duracaoLeiloes && !leilao.duracao) {

        leilao.duracao = vm.duracaoLeiloes;
      }

      if (!leilao) {
      $('#iniciarLeilaoError').modal('show');

      }if (!leilao.duracao || leilao.duracao == '0000' ) {
        $('#iniciarLeilaoError').modal('show');
      }else if ((leilao.dataInicio && !leilao.hora) || (!leilao.dataInicio && leilao.hora) ) {
        $('#iniciarLeilaoError5').modal('show');
      }

      else {
        if (!leilao.dataInicio) {

        }else if(leilao.dataInicio && leilao.hora ){
          var str = leilao.dataInicio;
          var yyyy = str.substring(0,4);
          var mm = str.substring(7,5);
          var dd = str.substring(10,8);
          var hour = leilao.hora.substring(0,2);
          var min = leilao.hora.substring(2,4);
          var sec = "00"
          leilao.dataInicio = yyyy + '-' + mm + '-' + dd + ' ' + hour + ':' + min + ':' + sec;

        }

        vm.dataInicial = leilao.dataInicio;

        if (yyyy > "3000") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (mm > "12") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (mm < "01") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (dd > "31") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (dd < "01") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (hour > "24") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (min > "60") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (sec > "60") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else {

          var dividi = leilao.duracao / 100;

          if (dividi < 1) {
            var duracao = dividi * 6000000;
          }else {
            var duracao = dividi * 3600000;
          }
          const req = {
            url: '/leilao/IniciarLeilaoRest',
            method: 'POST',
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            params: {"idProduto": leilao.idProduto, "duracao": duracao, "dataInicio": leilao.dataInicio}
          }

          // console.log(req);
          $http(req)
          .then(
            function(res){ // Retorno de sucesso
              if(accessFactory.checkStatus(res.status)){
                vm.showMessageError = false;
                // vm.dialog.error = true;
              }else {
                vm.showMessageError = true;
              }
              $('#iniciarLeilaoSucesso').modal('show');
              accessFactory.checkStatus(res.status);
              listarTodosProdutos();
              listaTodosLeiloesAndamento();
              listaTodosLeiloesAgendados();
              /*
              Reseta as variaveis do checkbox de iniciar leilao quando for bem sucedido
              */
              vm.countPlaylistLeilao = 0; //
              vm.playListLeilao = "";
              vm.duracaoLeiloes = "";
            },
            function(res){ // Retorno de erro
              leilao.dataInicio = "";
              if (res.status == 400) {
                  $('#iniciarLeilaoError3').modal('show');
                  leilao.dataInicio = "";
              }else if (res.status == 404) {
                  $('#iniciarLeilaoError4').modal('show');
                  res.showMessageError;
                  leilao.dataInicio = "";
              }
            }
          )
        }
      }
    } /* Fim da função iniciarLeilao(leilao) */
    function updateDesativaLeilao(leilao){
      // console.log("DESATIVA! PEGA O ID: " + usuario.id_usuario);
      const req = {
        url: '/leilao/updateDesativaLeilao',
        method: 'POST',
        headers: {'Content-Type': 'application/json;charset=utf-8'},
        params: {"idLeilao": leilao.idLeilao, "idProduto": leilao.idProduto.idProduto}
      }

      $http(req)
      .then(
        function(res){ // Retorno de sucesso
          if(accessFactory.checkStatus(res.status)){
            vm.showMessageError = false;
          }else {
            vm.showMessageError = true;
          }
          $('#editarUsuario').modal('hide'); //Este jQuery fecha a modal de edição
          $('#desativaLeilaoAgendado').modal('show'); //Este jQuery abre a modal com mensagem de sucesso
          $('#desativarLeilao').modal('hide'); //Este jQuery fecha a modal de edição
          $('#desativaLeilaoAndamento').modal('show'); //Este jQuery abre a modal com mensagem de sucesso
          accessFactory.checkStatus(res.status);
          listaTodosLeiloesAgendados();
          listaTodosLeiloesAndamento();
          listarTodosProdutos();

        },
        function(res){ // Retorno de erro
          $('#desativarUsuarioErro').modal('show'); //Este jQuery abre a modal com mensagem de erro
        }
      )
    }

    function atualizarLeilao(formularioLeilao,leilao){

      var dataAtual = new Date();
      var date = dataAtual.getFullYear() + '-' + ('0' + (dataAtual.getMonth() + 1)).slice(-2) + '-' + ('0' + dataAtual.getDate()).slice(-2) + ' ' +
      ('0' + dataAtual.getHours()).slice(-2) + ':' + ('0' + dataAtual.getMinutes()).slice(-2) + ':' + ('0' + dataAtual.getSeconds()).slice(-2);


      if (!leilao) {
      $('#iniciarLeilaoError').modal('show');

      }if (!leilao.duracao || leilao.duracao == '0000' ) {
        $('#iniciarLeilaoError').modal('show');
      }else if ((leilao.dataInicio && !leilao.hora) || (!leilao.dataInicio && leilao.hora) ) {
        $('#iniciarLeilaoError5').modal('show');
      }
      else {
        if (!leilao.dataInicio) {

          leilao.dataInicio = date;
        }else if(leilao.dataInicio && leilao.hora ){
          var str = leilao.dataInicio;
          var yyyy = str.substring(0,4);
          var mm = str.substring(7,5);
          var dd = str.substring(10,8);
          var hour = leilao.hora.substring(0,2);
          var min = leilao.hora.substring(2,4);
          var sec = "00"
          leilao.dataInicio = yyyy + '-' + mm + '-' + dd + ' ' + hour + ':' + min + ':' + sec;

        }

        vm.dataInicial = leilao.dataInicio;

        if (yyyy > "3000") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (mm > "12") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (mm < "01") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (dd > "31") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (dd < "01") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (hour > "24") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (min > "60") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else if (sec > "60") {
          $('#iniciarLeilaoError2').modal('show');
          leilao.dataInicio = "";
        }else {

          var dividi = leilao.duracao / 100;

          if (dividi < 1) {
            var duracao = dividi * 6000000;
          }else {
            var duracao = dividi * 3600000;
          }
          const req = {
            url:     '/leilao/updateLeilao',
            method:  'POST',
            headers: {'Content-Type': 'application/json;charset=utf-8'},
            params:  {"idLeilao": formularioLeilao.idLeilao, "dataInicio": leilao.dataInicio, "duracao": duracao}
          };

          $http(req)
          .then(
            function(res){
              if(accessFactory.checkStatus(res.status)){
                vm.showMessageError = false;
                vm.dialog.error = true;
              }else {
                vm.showMessageError = true;
              }
              accessFactory.checkStatus(res.status);
              $('#alterarLeilaoAgendado').modal('show');
              $('#editarLeilao').modal('hide');
              vm.leilao = {}; // limpa o formulário.

                    listaTodosLeiloesAgendados();
            },
            function(res) {
              leilao.dataInicio = "";
              if (res.status == 400) {
                $('#alterarLeilaoError3').modal('show');
              }else if (res.status == 404) {
                  $('#alterarLeilaoError4').modal('show');
              }

            }
          )
        }
      }
    }
 }
} /* Fim da função administradorController() */

/* --------- O que não deu certo ------------------------ */



/* ------------------------------------------------------ */
