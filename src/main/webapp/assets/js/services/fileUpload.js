angular.module('app.services')
.service("fileUpload", fileUpload);

fileUpload.$inject = ['$http'];

function fileUpload($http){
  this.uploadFileToUrl = function(file, uploadUrl){
	  console.log('Oi');
    var fd = new FormData();
    fd.append('file', file);
    
    $http.post(uploadUrl, fd, {
      transformRequest: angular.identiry,
      headers: {'Content-Type': undefined}
    })
     
  }
}
