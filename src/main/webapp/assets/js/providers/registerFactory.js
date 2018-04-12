angular.module('app.providers')
	.factory("registerFactory", registerFactory);

registerFactory.$inject = ['$rootScope', '$location'];

function registerFactory($rootScope, $location) {

		function checkStatus(status) {
			console.log("checkStatus", status);

        switch (status) {
            case '200':
                alert("Usuário Cadastrado com Sucesso");
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
		};

		return {
			checkStatus: checkStatus
		}

};
