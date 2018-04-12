angular.module('app.directives')
  .directive("fileSelect", fileModel);

  fileSelect.$inject = ['$parse'];

  function fileSelect($parse){
  var template = '<input type="file" name="file"/>';
  return function( scope, elem, attrs ) {
    var selector = $( template );
    elem.append(selector);
    selector.bind('change', function( event ) {
      scope.$apply(function() {
        scope[ attrs.fileSelect ] = event.originalEvent.target.files;
      });
    });
    scope.$watch(attrs.fileSelect, function(file) {
      selector.val(file);
    });
  };
}
