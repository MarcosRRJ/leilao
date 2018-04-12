angular.module('app.providers')
	.factory("updaterFactory", updaterFactory);

updaterFactory.$inject = ['$rootScope', '$location'];

function updaterFactory($rootScope, $location) {

		function atualizarUsuario(status) {
			console.log("atualizarUsuario", status);

        switch (status) {
            case '200':
                alert("Usuário Atualizado com Sucesso");
                break;
            case '300':
                alert("Selected Case Number is 2");
                break;
						case '404':
		            alert("Erro 404");
		            break;
						case '500':
				        alert("Erro 500");
				        break;
            default:
        }
		}

		return {
			atualizarUsuario: atualizarUsuario
		}

};
