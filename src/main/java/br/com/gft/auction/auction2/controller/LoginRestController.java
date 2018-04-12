package br.com.gft.auction.auction2.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.auction.auction2.model.Usuario;
import br.com.gft.auction.auction2.service.UsuarioService;

@RestController
public class LoginRestController { // LoginController

	// Injeta uma instancia de UsuarioService
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private void setService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * Endpoint responsavel por Realizar o Login
	 * 
	 * @param netId
	 * @param senha
	 * @return
	 */

	@RequestMapping(value = "/loginRest", method =	RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Usuario loginJson(@RequestParam("netId") String netId, @RequestParam("senha") String senha) {
		return usuarioService.findUserBynetIdAndSenha(netId, senha);
	}

	// @RequestMapping(value = "/loginRest", method = { RequestMethod.GET,
	// RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<?> TipoJson(@RequestParam("netId") String netId,
	// @RequestParam("senha") String senha) {
	// Usuario userExist = usuarioService.findUserBynetIdAndSenha(netId, senha);
	// if (userExist != null) {
	// if (userExist.getSituacao() == 0 || userExist.getSituacao() == 2) {
	// return new ResponseEntity<>("UsuÃ¡rio Desativado",
	// HttpStatus.BAD_REQUEST);
	// } else {
	// userExist.setToken(
	// Jwts.builder().setSubject(userExist.getNetId()).signWith(SignatureAlgorithm.HS512,
	// "GFT@Leilao")
	// .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 *
	// 1000)).compact());
	//
	// return new ResponseEntity<>(userExist, HttpStatus.OK);
	//
	// }
	// } else {
	// return new ResponseEntity<>("netId ou senha Incorreto",
	// HttpStatus.BAD_REQUEST);
	// }
	// }

	/**
	 * Endpoint responsavel por Registrar Usuario
	 * 
	 * @param todos
	 *            atributos de Usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/registrarUsuarioRest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewUser(@Valid Usuario user, BindingResult bindingResult) {
		// Verifica se existe usuario com este email
		Usuario userExists = usuarioService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		Usuario userExists1 = usuarioService.findUserByCpf(user.getCpf());
		if (userExists1 != null) {
			return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
		}
		Usuario userExists2 = usuarioService.findUserByNetId(user.getNetId());
		if (userExists2 != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
		} else {
			
			usuarioService.saveUser(user);
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		}
	}

}
