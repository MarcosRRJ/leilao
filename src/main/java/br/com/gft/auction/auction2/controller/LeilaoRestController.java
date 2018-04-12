package br.com.gft.auction.auction2.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.service.LeilaoService;
import br.com.gft.auction.auction2.service.ParametrosService;
import br.com.gft.auction.auction2.service.ProdutoService;

@Controller
// @RequestMapping("/admin")
public class LeilaoRestController {

	// Injeta uma instancia de LeilaoService
	@Autowired
	private LeilaoService leilaoService;

	@Autowired
	private void setService(LeilaoService leilaoService) {
		this.leilaoService = leilaoService;
	}

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private void setService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@Autowired
	private ParametrosService parametrosService;

	@Autowired
	private void setService(ParametrosService parametrosService) {
		this.parametrosService = parametrosService;
	}

	/**
	 * Endpoint responsavel por criar um Leilao
	 *
	 * @param todos
	 *            atributos de Leialão
	 *
	 * @return
	 */
	@RequestMapping(value = "/IniciarLeilaoRest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> createNewUser(@Valid Leilao leilao, BindingResult bindingResult,
			@RequestParam long idProduto, Produto produto) throws Exception {

		// // Valida o formulario
		// if (bindingResult.hasErrors()) {
		// return new ResponseEntity<String>("verifique o formulario",
		// HttpStatus.BAD_REQUEST);
		// }

		// pega a data atual do sistema e convert em milissegundo
		Timestamp dataAtual = new Timestamp(System.currentTimeMillis());
		long start = dataAtual.getTime();

		if (leilao.getDataInicio().getTime() < (start - 1000)) {

			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		} else if (leilao.getDataInicio().getTime() > (start + 2000)) {
			try {
				// Converte dataInicio leilao em string
				String newstring = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(leilao.getDataInicio());
				int rowsLeilaoAgendado = leilaoService.verificaNumeroDeLeilaoComADataAgendade(newstring);

				if (rowsLeilaoAgendado >= parametrosService.peguaNumeroDeLeiloeSimultaneos()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);

				} else if (leilao.getDataInicio().getTime() > leilaoService.verificaSeHaLeilaoAcontecendo()
						.getTimeInMillis()) {

					leilaoService.saveLeilao(leilao, idProduto);
					produtoService.updateSituacao(produto.getIdProduto());
					return new ResponseEntity<>(leilao, HttpStatus.OK);
				}
			} catch (Exception e) {
				leilaoService.saveLeilao(leilao, idProduto);
				produtoService.updateSituacao(produto.getIdProduto());
				return new ResponseEntity<>(leilao, HttpStatus.OK);
			}

		} else {
			try {
				long rowsSelect = leilaoService.verificaNumeroDeLeilaoAcontecendo();
				if (rowsSelect >= parametrosService.peguaNumeroDeLeiloeSimultaneos()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);

				} else {

					leilaoService.saveLeilao(leilao, idProduto);
					produtoService.updateSituacao(produto.getIdProduto());
					return new ResponseEntity<>(leilao, HttpStatus.OK);
				}

				// ?return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>("Entrou no catch", HttpStatus.OK);
			}
		}
		return null;

	}

	/**
	 * Endpoint responsavel por listar todos Leiloes Andamento
	 *
	 * @return
	 */
	@RequestMapping(value = "/listaLeiloesAndamento", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> LeilaoJson() {
		List<Leilao> leilao = leilaoService.findAll();
		return new ResponseEntity<>(leilao, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por listar todos Leiloes Agendado
	 *
	 * @return
	 */
	@RequestMapping(value = "/listaProximosLeiloes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> ProximosLeilao() {
		List<Leilao> leilao = leilaoService.findAllProximos();
		return new ResponseEntity<>(leilao, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por listar todos Leiloes Agendado
	 *
	 * @return
	 */
	@RequestMapping(value = "/listaProximosLeiloesIdLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> ProximosLeilaoIdLeilao() {
		List<Leilao> leilao = leilaoService.findAllIdLeilaoProximosleiloes();
		return new ResponseEntity<>(leilao, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por selecionar um Leilao
	 *
	 * @param idLeilao
	 * @return
	 */
	@RequestMapping(value = "/listarUmLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getProdutoById(@RequestParam("idLeilao") long idLeilao) {
		Leilao leilao = leilaoService.findByLeilaoId(idLeilao);
		if (leilao == null) {
			return new ResponseEntity<>("Produto nÃƒÂ£o existe", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(leilao, HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Fazer o update de Leiloes agendados
	 *
	 * @param Todos
	 *            os atributos de leiloes
	 * @return
	 */
	@RequestMapping(value = "/updateLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateSenhaUser(@RequestParam("dataInicio") Timestamp dataInicio,
			@RequestParam("duracao") long duracao, @RequestParam("idLeilao") long idLeilao) {

		Timestamp dataAtual = new Timestamp(System.currentTimeMillis());
		long start = dataAtual.getTime();

		// pega a data do inicio do leilÃ£o e converte para milesegundos
		long millisecond = dataInicio.getTime();
		// soma a data do inicio do leilÃƒÂ£o e a duraÃ§Ã£o
		long data = (millisecond + duracao);

		// pega o tempo em milisegundos e converte para calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(data);
		// set data do fim do leilão.

		Leilao leilao = leilaoService.findByLeilaoId(idLeilao);
		if (leilao == null || leilaoService.findByLeilaoId(idLeilao) == null) {
			return new ResponseEntity<>("NÃƒÂ£o existe Leilao", HttpStatus.BAD_REQUEST);
		}
		if (dataInicio.getTime() < (start - 1000)) {

			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

		} else if (dataInicio.getTime() > (start + 2000)) {
			try {
				// Converte dataInicio leilao em string
				String newstring = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dataInicio.getTime());
				int rowsLeilaoAgendado = leilaoService.verificaNumeroDeLeilaoComADataAgendade(newstring);

				if (rowsLeilaoAgendado >= parametrosService.peguaNumeroDeLeiloeSimultaneos()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);

				} else if (dataInicio.getTime() > leilaoService.verificaSeHaLeilaoAcontecendo().getTimeInMillis()) {
					leilao.setDataFimLeilao(calendar);
					long rowsUpdated = leilaoService.updateLeilao(dataInicio, duracao, idLeilao);
					if (rowsUpdated == 1) {

						return new ResponseEntity<>(leilao, HttpStatus.NO_CONTENT);
					}
				}
			} catch (Exception e) {
				leilao.setDataFimLeilao(calendar);
				long rowsUpdated = leilaoService.updateLeilao(dataInicio, duracao, idLeilao);
				if (rowsUpdated == 1) {

					return new ResponseEntity<>(leilao, HttpStatus.NO_CONTENT);
				}
			}

		} else {
			try {
				long rowsSelect = leilaoService.verificaNumeroDeLeilaoAcontecendo();
				if (rowsSelect >= parametrosService.peguaNumeroDeLeiloeSimultaneos()) {
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);

				} else {

					leilao.setDataFimLeilao(calendar);
					long rowsUpdated = leilaoService.updateLeilao(dataInicio, duracao, idLeilao);
					if (rowsUpdated == 1) {

						return new ResponseEntity<>(leilao, HttpStatus.NO_CONTENT);
					}
				}

				// ?return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>("Entrou no catch", HttpStatus.OK);
			}
		}
		return null;
	}

	/**
	 * Endpoint responsavel por listar todos Leiloes Andamento para ficar
	 * fazendo o refresh das informações
	 *
	 * @return
	 */
	@RequestMapping(value = "/pegaInfoRefresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> pegaInfoRefresh() {

		List<Leilao> leilao = leilaoService.pegaInfoRefresh();
		return new ResponseEntity<>(leilao, HttpStatus.OK);

	}

	/**
	 * Endpoint responsavel por desativar um leilão
	 * @param idLeilao
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/updateDesativaLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> desativaLeilao(@RequestParam("idLeilao") long idLeilao, @RequestParam("idProduto") long idProduto) {
		Leilao leilao = leilaoService.findByLeilaoId(idLeilao);
		if (leilao == null) {
			return new ResponseEntity<>("Leilao NÃ£o existe", HttpStatus.BAD_REQUEST);
		}
		if (leilaoService.findByLeilaoId(idLeilao) == null) {
			return new ResponseEntity<>("ID do Leilao nÃ£o encontrado", HttpStatus.NOT_FOUND);
		}

		long rowsUpdated = leilaoService.updateDesativaLeilaoAgendado(idLeilao);

		if (rowsUpdated == 1) {
			produtoService.updateSituacaoAtivarProduto(idProduto);
			return new ResponseEntity<>("Leilao Desativado", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Mais de um id Leilao encotrado", HttpStatus.CONFLICT);
		}
	}
}
