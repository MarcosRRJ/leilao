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

import br.com.gft.auction.auction2.model.Lance;
import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Usuario;
import br.com.gft.auction.auction2.service.LanceService;

@Controller
public class LanceRestController {

	//Injeta uma instancia de LanceService
	@Autowired
	private LanceService lanceService;

	@Autowired
	private void setService(LanceService lanceService) {
		this.lanceService = lanceService;
	}
	
	/**
	 * Endpoint para Usuario dar Lance
	 * 
	 * @param idUsuario
	 * @param leilao
	 * @param idLeilao
	 * @return
	 */
	@RequestMapping(value = "/darLance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createNewUser(@Valid Lance lance, BindingResult bindingResult,
			@RequestParam Usuario idUsuario, @RequestParam Leilao leilao, @RequestParam long idLeilao) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("verifique o formulario", HttpStatus.BAD_REQUEST);
		} else {
			leilao.setArrematado(1);
			lanceService.saveLance(lance, leilao, idLeilao);
			return new ResponseEntity<>(lance, HttpStatus.OK);

		}
	}

	/**
	 * Endpoint responsavel por pegar o lance do usuario de um certo leilao
	 * 
	 * @param idLeilao
	 * @return
	 */
	@RequestMapping(value = "/pegaValorUltimoLanceUsuarioDeUmCertoLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> pegaValorUltimoLanceUsuarioDeUmCertoLeilao(@RequestParam("idUsuario") Usuario idUsuario) {

		List<?> lance = lanceService.pegaValorUltimoLanceUsuarioDeUmCertoLeilao(idUsuario);
		return new ResponseEntity<>(lance, HttpStatus.OK);

	}
}
