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
import org.springframework.web.multipart.MultipartFile;

import br.com.gft.auction.auction2.infra.Erro;
import br.com.gft.auction.auction2.infra.FileSaver;
import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.service.LeilaoService;
import br.com.gft.auction.auction2.service.ProdutoService;

@Controller
// @RequestMapping("/admin")
public class ProdutoRestController {

	// Injeta uma instancia de ProdutoService
	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private void setService(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	// Injeta uma instancia de LeilaoService
	@Autowired
	private LeilaoService leilaoService;

	@Autowired
	private void setService(LeilaoService leilaoService) {
		this.leilaoService = leilaoService;
	}

	// Injeta uma instancia de FileSaver
	@Autowired
	private FileSaver fileSaver;

	/**
	 * Endpoint responsavel por Registrar um produto
	 * 
	 * @param todos
	 *            atributos de produto
	 * @return
	 */
	@RequestMapping(value = "/registrarProdutoRest", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> createNewProduto(@Valid Produto produto, @RequestParam("file") MultipartFile file,
			BindingResult bindingResult) {

		Produto produtoExist = produtoService.findProdutoBycodPatrimonio(produto.getCodPatrimonio());
		if (produtoExist != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>("Verifique o formulario", HttpStatus.CONFLICT);
		} else {

			String path = fileSaver.write("img", file);
			produto.setImagemPath(path);

			produtoService.saveProduto(produto);
			// headers.add("File Uploaded Successfully - ", path);
			return new ResponseEntity<>(produto, HttpStatus.CREATED);
			// return new ResponseEntity<>(produto,headers, HttpStatus.CREATED);
		}
	}

	/**
	 * Endpoint responsavel por listar todos os Produtos
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listaTodosProdutos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getProdutoJson() {
		List<Produto> produto = produtoService.findByActive(null);
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}

	/**
	 * Endpoint responsavel por selecionar um Produto
	 * 
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/listarUmProduto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getProdutoById(@RequestParam("idProduto") long idProduto) {
		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("Produto nÃ£o existe", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(produto, HttpStatus.OK);
		}
	}

	/**
	 * Endpoint responsavel por Desativar um Produto
	 * 
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/updateDestivarProduto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> desativaProduto(@RequestParam("idProduto") long idProduto) {
		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("Produto NÃ£o existe", HttpStatus.BAD_REQUEST);
		}
		if (produtoService.findProdutoById(idProduto) == null) {
			return new ResponseEntity<>("ID do Produto nÃ£o encontrado", HttpStatus.NOT_FOUND);
		}

		long rowsUpdated = produtoService.updateSituacaoDesativar(idProduto);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>("Produto Desativado", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Mais de um id Produto encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por Alterar as Informações de um Produto
	 * 
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/updateProduto", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> updateProduto(@RequestParam("idProduto") long idProduto,
			@RequestParam("file") MultipartFile file, @RequestParam("descricao") String descricao,
			@RequestParam("nomeProduto") String nomeProduto, @RequestParam("codPatrimonio") String codPatrimonio,
			@RequestParam("unidadeId") Unidade unidadeId, @RequestParam("valorInicial") double valorInicial,
			@RequestParam("valorPorLance") double valorPorLance) {

		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("Produto nÃ£o existe", HttpStatus.BAD_REQUEST);
		}
		if (produtoService.findProdutoById(idProduto) == null) {
			return new ResponseEntity<>("ID do produto nÃ£o encontrado", HttpStatus.NOT_FOUND);

		} else {
			String path = fileSaver.write("img", file);
			produto.setImagemPath(path);
			produtoService.updateProduto(idProduto, descricao, nomeProduto, codPatrimonio, unidadeId, valorInicial,
					valorPorLance);
			return new ResponseEntity<>(produto, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Endpoint responsavel por Fazer o import via Excel
	 * 
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> importExcel(@RequestParam("file") MultipartFile file, Produto produto) {

		String path = fileSaver.excel("excel", file);

		Erro erro = new Erro();
		if (path != null) {
			return new ResponseEntity<>(fileSaver.importarPlanilha(), HttpStatus.OK);
		} else {
			erro.setId_erro("1");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// fileSaver.importarPlanilha();
	// System.out.println(fileSaver.importarPlanilha());

	/**
	 * Endpoint responsavel por Ativa um Produto caso ele não for arrematado
	 * 
	 * @param idProduto
	 * @return
	 */
	@RequestMapping(value = "/updateAtivarProduto", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> ativarUsuario() {
		produtoService.updateProdutoNaoArrematado();
		leilaoService.updateLeilaoNaoArrematado();
		return new ResponseEntity<>("Produto NÃ£o existe", HttpStatus.OK);
	}
	
	/**
	 *  Endpoint responsavel por Alterar as Informações de um Produto mas sem a imagem
	 *  
	 * @param idProduto
	 * @param descricao
	 * @param nomeProduto
	 * @param codPatrimonio
	 * @param unidadeId
	 * @param valorInicial
	 * @param valorPorLance
	 * @return
	 */

	@RequestMapping(value = "/updateProdutoSemFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateProdutoSemFile(@RequestParam("idProduto") long idProduto,
			@RequestParam("descricao") String descricao, @RequestParam("nomeProduto") String nomeProduto,
			@RequestParam("codPatrimonio") String codPatrimonio, @RequestParam("unidadeId") Unidade unidadeId,
			@RequestParam("valorInicial") double valorInicial, @RequestParam("valorPorLance") double valorPorLance) {

		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("Produto nÃ£o existe", HttpStatus.BAD_REQUEST);
		}
		if (produtoService.findProdutoById(idProduto) == null) {
			return new ResponseEntity<>("ID do produto nÃ£o encontrado", HttpStatus.NOT_FOUND);

		} else {
			produtoService.updateProduto(idProduto, descricao, nomeProduto, codPatrimonio, unidadeId, valorInicial,
					valorPorLance);
			return new ResponseEntity<>(produto, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * Endpoint responsavel por Alterar as Informações de um Produto mas sem unidade
	 * @param idProduto
	 * @param descricao
	 * @param nomeProduto
	 * @param codPatrimonio
	 * @param unidadeId
	 * @param valorInicial
	 * @param valorPorLance
	 * @return
	 */

	@RequestMapping(value = "/updateProdutoSemUnidade", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateProdutoSemUnidade(@RequestParam("idProduto") long idProduto,
			@RequestParam("descricao") String descricao, @RequestParam("nomeProduto") String nomeProduto,
			@RequestParam("codPatrimonio") String codPatrimonio, @RequestParam("unidadeId") Unidade unidadeId,
			@RequestParam("valorInicial") double valorInicial, @RequestParam("valorPorLance") double valorPorLance) {

		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("Produto nÃ£o existe", HttpStatus.BAD_REQUEST);
		}
		if (produtoService.findProdutoById(idProduto) == null) {
			return new ResponseEntity<>("ID do produto nÃ£o encontrado", HttpStatus.NOT_FOUND);

		} else {
			produtoService.updateProdutoSemUnidade(idProduto, descricao, nomeProduto, codPatrimonio, unidadeId,
					valorInicial, valorPorLance);
			return new ResponseEntity<>(produto, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Endpoint responsavel por Reativar um Produto
	 * 
	 * @param id_usuario
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateAtivarProdutoDesativado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> AtivarUsuario(@RequestParam("idProduto") long idProduto) {
		Produto produto = produtoService.findProdutoById(idProduto);
		if (produto == null) {
			return new ResponseEntity<>("NÃƒÂ£o existe Produto", HttpStatus.BAD_REQUEST);
		}
		long rowsUpdated = produtoService.updateSituacaoAtivarProduto(idProduto);

		if (rowsUpdated == 1) {
			return new ResponseEntity<>(produto, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>("Usuario mais de um id encotrado", HttpStatus.CONFLICT);
		}
	}

	/**
	 * Endpoint responsavel por listar todos os Produtos Desativados
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listaTodosProdutosDesativados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getProdutoJsonDesativados() {
		List<Produto> produto = produtoService.findAllDesativados();
		return new ResponseEntity<>(produto, HttpStatus.OK);
	}

}