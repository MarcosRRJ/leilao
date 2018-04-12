angular.module('app.providers')
	.factory("deleterFactory", deleterFactory);

deleterFactory.$inject = ['$rootScope', '$location'];

function deleterFactory($rootScope, $location) {

		function desativarUsuario(status) {
			console.log("desativarUsuario", status);
			if (status === true){
				console.log("é true");
			}else {
				console.log("é null");
			}
		}

		return {
			desativarUsuario: desativarUsuario
		}

};
