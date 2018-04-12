package br.com.gft.auction.auction2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario.SituacaoEnum;
import br.com.gft.auction.auction2.repository.ProdutoRepository;

@Service("produtoService")
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void saveProduto(Produto produto) {
		produto.setSituacao(SituacaoEnum.ATIVO.getSituacaoEnum());
		produtoRepository.save(produto);

	}

	@Override
	public Produto findProdutoById(long idProduto) {
		return produtoRepository.findOne(idProduto);
	}

	@Override
	public Produto findProdutoBycodPatrimonio(String codPatrimonio) {
		return produtoRepository.findByCodPatrimonio(codPatrimonio);
	}

	@Override
	public int updateSituacao(long idProduto) {
		return produtoRepository.updateSituacao(idProduto);
	}

	@Override
	public int updateProduto(long idProduto, String descricao, String nomeProduto, String codPatrimonio,
			Unidade unidadeId, double valorInicial, double valorPorLance) {
		return produtoRepository.updateProduto(idProduto, descricao, nomeProduto, codPatrimonio, unidadeId, valorInicial,
				valorPorLance);
	}

	@Override
	public List<Produto> findByActive(Leilao leilao) {

		// Timestamp dataInicio = new Timestamp(System.currentTimeMillis());
		//
		// long start = dataInicio.getTime();
		//
		// if ((start > leilao.getDataFimLeilao().getTimeInMillis()) &&
		// (leilao.getArrematado() == 0)) {
		// produtoRepository.updateSituacaoSeNaoForArrematado();
		// }
		return produtoRepository.findAll();
	}

	@Override
	public int updateProdutoLeilao(double valorInicial, double valorPorLance, long idProduto) {
		return produtoRepository.updateProdutoLeilao(valorInicial, valorPorLance, idProduto);
	}

	@Override
	public void saveProduto(List<Produto> lista) {
		produtoRepository.save(lista);
	}

	@Override
	public int updateProdutoNaoArrematado() {
		return produtoRepository.updateProdutoNaoArrematado();
	}

	@Override
	public int updateProdutoSemUnidade(long idProduto, String descricao, String nomeProduto, String codPatrimonio,
			Unidade unidadeId,double valorInicial, double valorPorLance) {
		return produtoRepository.updateProdutoSemUnidade(idProduto, descricao, nomeProduto, codPatrimonio, unidadeId,valorInicial, valorPorLance);
	}

	@Override
	public int updateSituacaoDesativar(long idProduto) {
		return produtoRepository.updateSituacaoDesativar(idProduto);
	}

	@Override
	public List<Produto> findAllDesativados() {
		return produtoRepository.findAllDesativados();
	}

	@Override
	public int updateSituacaoAtivarProduto(long idProduto) {
		return produtoRepository.updateSituacaoAtivarProduto(idProduto);
	}
	
	@Override
	public int countfindProdutoBycodPatrimonio(String codPatrimonio) {
		return produtoRepository.pegaQuantidadeProduto(codPatrimonio);
	}

}
