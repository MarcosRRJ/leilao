angular.module('app.providers')
	.factory("listFactory", listFactory);

	listFactory.$inject = ['$rootScope', '$location'];

function listFactory($rootScope, $location) {

		function listarTodosUsuarios(status) {
			console.log("listarUsuario", status);
      if (status === 'true') {
        console.log("Há retorno da base de dados");
      }else{
        console.log("Não há retorno da base de dados");
      }
		}

		return {
			listarTodosUsuarios: listarTodosUsuarios
		}

};
