angular.module('app.providers')
.factory("accessFactory", accessFactory);

accessFactory.$inject = ['$rootScope', '$location', '$http'];

function accessFactory($rootScope, $location, $http) {

	var success = false;

	function checkAccess(user) {
		// console.log("checkAccess", user);
		if(user.tipo.id == 1){
			$rootScope.user = user;
			$location.path('/administrador'); //ROTAS
			success = true;
			return true
		}else if (user.tipo.id == 2 && user.situacao == 1)  {
			$rootScope.user = user;
			$location.path('/usuario'); //ROTAS
			success = true;
			return true
		}else if (user.tipo.id == 2 && user.situacao == 0){
			$rootScope.user = user;
			// alert("usuario desativado");
			$('#editarLeilao').modal('show');
			// console.log("usuario desativado");
			$location.path('/');
			// alert("You don't have access");
			return false;
		}else if (user.tipo.id == 2 && user.situacao == 2){
			$rootScope.user = user;
			// alert("Conta esta aguardando liberação");
			$location.path('/');
			// alert("You don't have access");
			return false;
		}else{
			// console.log("Usuário não possui acesso");
			$location.path('/');
			// alert("You don't have access");
			return false;
		}
	}

	function checkID() {
		return this.success;
	}

	function checkIndentification() {
			return this.success;
		}

	function checkStatus(status) {
		// console.log("Entrou na função 'checkStatus'. O status é: ", status);
		switch (status) {
			case '200':
			// console.log("Usuário Cadastrado com Sucesso");
			break;
			case '300':
			// alert("Erro 300");
			break;
			case '409':
			// alert("Erro 404");
			break;
			case '500':
			// alert("Erro 500");
			break;
			default:
		}
	}

	return {
		checkAccess: checkAccess,
		checkStatus: checkStatus,
		checkID    : checkID,
		checkIndentification: checkIndentification
	}

};
