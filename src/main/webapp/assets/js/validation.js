function validate(){
  var username = document.getElementById("username").value;
  var password = document.getElementById("password").value;

  if ( username == "adm" && password == "adm"){
  alert ("Login de Admin feito com sucesso");
  window.location = "home_admin.html"; // Redirecting to other page.
  return false;
  }else if (username == "user" && password == "user") {
    alert ("Login de User feito com sucesso");
    window.location = "home_user.html"; // Redirecting to other page.
    return false;
  }
}
