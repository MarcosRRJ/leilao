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
import br.com.gft.auction.auction2.service.UnidadeService;

@Controller
// @RequestMapping("/admin")
public class UnidadeRestController {

	// Injeta uma instancia de UsuarioService
	@Autowired
	private UnidadeService unidadeService;

	@Autowired
	private void setService(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}

	/**
	 * Endpoint responsavel por Registrar uma Unidade
	 *
	 * @param todos
	 *            atributos de Unidade
	 * @return
	 */
	@RequestMapping(value = "/registrarUnidadeRest", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> createNewUnidade(@Valid Unidade unidade, BindingResult bindingResult) {
		// Verifica se existe usuario com este email
		Unidade unidadeExists = unidadeService.findUnidade(unidade.getUnidadeNome());
		if (unidadeExists != null) {
			return new ResponseEntity<>("Unidade jÃ¡ existe", HttpStatus.BAD_REQUEST);
		}
		if (bindingResult.hasErrors() && unidade.getUnidadeNome() == null) {
			return new ResponseEntity<>("Verifique o Formulario", HttpStatus.BAD_REQUEST);
		} else {
			unidadeService.saveUnidade(unidade);
			return new ResponseEntity<>(unidade, HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Listar todas as Unidade
	 *
	 * @return
	 */
	@RequestMapping(value = "/listaTodasUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getUnidadeJson() {
		List<Unidade> unidade = unidadeService.findByActive();
		return new ResponseEntity<>(unidade, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por Listar todas as Unidade Desativadas
	 *
	 * @return
	 */
	@RequestMapping(value = "/listaTodasUnidadeDesativadas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getUnidadeDesativadasJson() {
		List<Unidade> unidade = unidadeService.findAllDesativadas();
		return new ResponseEntity<>(unidade, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por Listar uma Unidade
	 *
	 * @return
	 */
	@RequestMapping(value = "/listarUmaUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getPersonById(@RequestParam("unidadeId") long unidadeId) {
		Unidade unidade = unidadeService.findUnidadeById(unidadeId);
		if (unidade == null) {
			return new ResponseEntity<>("Unidade nÃ£o encontrado", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(unidade, HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Desativar uma Unidade
	 *
	 * @param unidadeId
	 * @return
	 */
	@RequestMapping(value = "/updateDestivarUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> desativaUsuario(@RequestParam("unidadeId") long unidadeId) {
		Unidade unidade = unidadeService.findUnidadeById(unidadeId);
		if (unidade == null) {
			return new ResponseEntity<>("NÃ£o existe unidade", HttpStatus.BAD_REQUEST);
		}

		long rowsUpdated = unidadeService.updateSituacao(unidadeId);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>("unidade Desativado", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Mais de uma unidade encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por Editar uma Unidade
	 *
	 * @param unidadeId
	 * @return
	 */
	@RequestMapping(value = "/updateUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateD(@RequestParam("unidadeId") long unidadeId,
			@RequestParam("unidadeNome") String unidadeNome) {

		Unidade unidade = unidadeService.findUnidadeById(unidadeId);
		if (unidade == null) {
			return new ResponseEntity<>("Selecione uma Unidade", HttpStatus.BAD_REQUEST);

		} else {
			unidadeService.updateUnidade(unidadeId, unidadeNome);
			return new ResponseEntity<>(unidade, HttpStatus.NO_CONTENT);
		}
	}
	/**
	 * Endpoint responsavel por Reativar uma Unidade
	 *
	 * @param unidadeId
	 * @return
	 */
	@RequestMapping(value = "/updateReativarUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> reativarUsuario(@RequestParam("unidadeId") long unidadeId) {
		Unidade unidade = unidadeService.findUnidadeById(unidadeId);
		if (unidade == null) {
			return new ResponseEntity<>("NÃ£o existe unidade", HttpStatus.BAD_REQUEST);
		}

		long rowsUpdated = unidadeService.updateReativar(unidadeId);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>("unidade Reativada", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Mais de uma unidade encotrado", HttpStatus.CONFLICT);
		}
	}

}
