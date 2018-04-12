package br.com.gft.auction.auction2.controller;

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

import br.com.gft.auction.auction2.model.Parametros;
import br.com.gft.auction.auction2.service.ParametrosService;

@Controller
//@RequestMapping("/admin")
public class ParametrosRestController {

	// Injeta uma instancia de ParametrosService
	@Autowired
	private ParametrosService parametrosService;

	@Autowired
	private void setService(ParametrosService parametrosService) {
		this.parametrosService = parametrosService;
	}

	/**
	 * Endpoint responsavel por Criar os Parametros
	 * 
	 * @param todos
	 *            atributos de Parametros
	 * @return
	 */
	@RequestMapping(value = "/IniciarParametros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createNewParametro(@Valid Parametros parametros, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("verifique o formulario", HttpStatus.BAD_REQUEST);
		} else {
			parametrosService.saveLance(parametros);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Fazer o Update do Tempo de Acrescimo do Fim do Leilao
	 * 
	 * @param tempoAcrescimo
	 * @return
	 */
	@RequestMapping(value = "/updateParametrosTempoAcrescimo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateParametrosTempoAcrescimo(@Valid Parametros parametro,
			@RequestParam("tempoAcrescimo") int tempoAcrescimo, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("verifique o formulario", HttpStatus.BAD_REQUEST);
		}
		parametrosService.updateParametrosTempoAcrescimo(tempoAcrescimo);
		return new ResponseEntity<>(tempoAcrescimo, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por Fazer o Update do Numero de Leiloes Simultanios
	 * 
	 * @param leiloesSimultaneos
	 * @return
	 */
	@RequestMapping(value = "/updateParametrosLeiloesSimultaneos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateParametrosLeiloesSimultaneos(@Valid Parametros parametro,
			@RequestParam("leiloesSimultaneos") int leiloesSimultaneos, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("verifique o formulario", HttpStatus.BAD_REQUEST);
		}
		parametrosService.updateParametrosLeiloesSimultaneos(leiloesSimultaneos);
		return new ResponseEntity<>(leiloesSimultaneos, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por pega o Tempo de Acrescimo do Fim do Leilao
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pegaTempoAcrescimo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getTempoAcrescimo(@Valid Parametros parametros, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("verifique o formulario", HttpStatus.BAD_REQUEST);
		}
		Integer tempoAcrescimo = parametrosService.peguatempoAcrescimo();
		return new ResponseEntity<>(tempoAcrescimo, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por pegar o numero de Leiloes Simultanios
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pegaVariavelDeLeiloes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getVariavelLeilao(@Valid Parametros parametros, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("verifique o formulario", HttpStatus.BAD_REQUEST);
		}
		Integer numeroDeLeiloes = parametrosService.peguaNumeroDeLeiloeSimultaneos();
		return new ResponseEntity<>(numeroDeLeiloes, HttpStatus.OK);
	}

}
