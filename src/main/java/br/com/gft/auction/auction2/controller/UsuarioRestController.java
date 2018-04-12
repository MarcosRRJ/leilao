package br.com.gft.auction.auction2.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario;
import br.com.gft.auction.auction2.service.UsuarioService;

@Controller
public class UsuarioRestController {

	// Injeta uma instancia de UsuarioService
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private void setService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * Endpoint responsavel por Chamar a View
	 * 
	 * @return
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login() {
		return "index";
	}

	/**
	 * Endpoint responsavel por Registrar um Usuario Via Home Admin
	 * 
	 * @param todos
	 *            atributos de Usuario
	 * @return
	 */
	@RequestMapping(value = "/registrarUsuarioViaAdminRest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewUserAdmin(@Valid Usuario user, BindingResult bindingResult) {
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

	/**
	 * Endpoint responsavel por listar Todos Usuarios Ativos
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listaTodosUserAtivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getUserJson() {
		List<Usuario> user = usuarioService.findByActive();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por listar Todos Usuarios em Espera
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listaTodosUserEmEspera", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getUserEsperaJson() {
		List<Usuario> user = usuarioService.findAllUserEspera();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por Listar Todos os Usuarios Desativados
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listaTodosUserDesativato", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getDesativadoJson() {
		List<Usuario> user = usuarioService.findAllDesativado();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por Listar um certo Usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listarUmUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getPersonById(@RequestParam("id_usuario") long idUsuario) {
		Usuario user = usuarioService.findUserById(idUsuario);
		if (user == null) {
			return new ResponseEntity<>("usuario nÃƒÂ£o encontrado", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Ativar um Usuario
	 * 
	 * @param id_usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateAtivarUsuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> AtivarUsuario(@RequestParam("id_usuario") long id_usuario) {
		Usuario user = usuarioService.findUserById(id_usuario);
		if (user == null) {
			return new ResponseEntity<>("NÃƒÂ£o existe usuario", HttpStatus.BAD_REQUEST);
		}
		long rowsUpdated = usuarioService.updateAtivarUser(id_usuario);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Usuario mais de um id encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por Desativar um Usuario
	 * 
	 * @param id_usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateDestivarUsuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> DesativaUsuario(@RequestParam("id_usuario") long id_usuario) {
		Usuario user = usuarioService.findUserById(id_usuario);
		if (user == null) {
			return new ResponseEntity<>("NÃƒÂ£o existe usuario", HttpStatus.BAD_REQUEST);
		}
		long rowsUpdated = usuarioService.updateDesativarUser(id_usuario);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Usuario mais de um id encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por Resetar a Senha de um Usuario
	 * 
	 * @param id_usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateSenha", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateSenhaUser(@RequestParam("id_usuario") long idUsuario) {
		Usuario user = usuarioService.findUserById(idUsuario);
		if (user == null) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
		if (usuarioService.findUserById(idUsuario) == null) {
			return new ResponseEntity<>("ID do usuaraio nÃƒÂ£o encontrado", HttpStatus.NOT_FOUND);
		}

		long rowsUpdated = usuarioService.updateSenhaUser(idUsuario);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Usuario maise de um id encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por Alterar as Informações de um Usuario
	 * 
	 * @param todos
	 *            atributos de Usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUsuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateD(@RequestParam("id_usuario") long id_usuario, @RequestParam("nome") String nome,
			@RequestParam("sobrenome") String sobrenome, @RequestParam("email") String email,
			@RequestParam("netId") String netId, @RequestParam("cpf") long cpf,
			@RequestParam("unidadeId") Unidade unidadeId) {

		Usuario user = usuarioService.findUserById(id_usuario);
		if (user == null) {
			return new ResponseEntity<>("mensagem 2", HttpStatus.BAD_REQUEST);

		} else {
			usuarioService.updateUsuario(id_usuario, nome, sobrenome, email, netId, cpf, unidadeId);
			return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Endpoint responsavel por Alterar a Senha de um Usuario
	 * 
	 * @param todos
	 *            atributos de Usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateSenhaUsuario", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateSenhaUsuario(@RequestParam("id_usuario") long id_usuario,
			@RequestParam("senha") String senha, @RequestParam("novaSenha") String novaSenha) {

		long rowsUpdated = usuarioService.updateSenhaUsuario(id_usuario, novaSenha, senha);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>("OK", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Usuario maise de um id encotrado", HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * Endpoint responsavel por Alterar as Informações de um Usuario sem alterar a unidade
	 * 
	 * @param todos
	 *            atributos de Usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateUsuarioSemUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateUsuarioSemUnidade(@RequestParam("id_usuario") long id_usuario,
			@RequestParam("nome") String nome, @RequestParam("sobrenome") String sobrenome,
			@RequestParam("email") String email, @RequestParam("netId") String netId, @RequestParam("cpf") long cpf) {

		Usuario user = usuarioService.findUserById(id_usuario);
		if (user == null) {
			return new ResponseEntity<>("mensagem 2", HttpStatus.BAD_REQUEST);

		} else {
			usuarioService.updateUsuarioSemUnidade(id_usuario, nome, sobrenome, email, netId, cpf);
			return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Endpoint responsavel por contar quantos Usuários em espera
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pegarQtdUsuariosEmEspera", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getCountUsuario() {
		int contador = usuarioService.countUsuarioEmEspera();
		return new ResponseEntity<>(contador, HttpStatus.OK);
	}


}
