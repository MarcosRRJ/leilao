angular.module('app.providers')
	.factory("reseterFactory", reseterFactory);

reseterFactory.$inject = ['$rootScope', '$location'];

function reseterFactory($rootScope, $location) {

		function redefinirSenha(user) {
			console.log("redefinirSenha", user);

			
		}

		return {
			redefinirSenha: redefinirSenha
		}

};
