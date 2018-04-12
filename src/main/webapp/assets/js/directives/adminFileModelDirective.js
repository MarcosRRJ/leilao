angular.module('app.directives')
.directive("fileModel", fileModel);

fileModel.$inject = ['$parse']; // $parse transforma exppressões AngularJS em funções /* https://docs.angularjs.org/api/ng/service/$parse */

function fileModel($parse){
  return {
    restrict: 'A', // Usar a diretiva como atributo https://stackoverflow.com/questions/23220976/angularjs-directive-restrict-a-vs-e
    link: function(scope, element, attrs){
      var model = $parse(attrs.fileModel); //Olha a expressão do atributo
      var modelSetter = model.assign; //

      element.bind('change', function(){ // toda hora que o elemento mudar
        scope.$apply(function(){ // executa essa função:
          modelSetter(scope, element[0].files[0]);
        })
      })
    }
  }
}
