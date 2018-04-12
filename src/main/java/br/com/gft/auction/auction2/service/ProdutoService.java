package br.com.gft.auction.auction2.service;

import java.util.List;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.model.Unidade;

public interface ProdutoService {

	public void saveProduto(Produto produto);

	public List<Produto> findByActive(Leilao leilao);

	public Produto findProdutoById(long idProduto);

	public Produto findProdutoBycodPatrimonio(String codPatrimonio);

	public int updateSituacao(long idProduto);

	public int updateProduto(long idProduto, String descricao, String nomeProduto, String codPatrimonio,
			Unidade unidadeId, double valorInicial, double valorPorLance);

	public int updateProdutoSemUnidade(long idProduto, String descricao, String nomeProduto, String codPatrimonio,
			Unidade unidadeId,double valorInicial, double valorPorLance);

	public int updateProdutoLeilao(double valorInicial, double valorPorLance, long idProduto);

	public void saveProduto(List<Produto> lista);

	public int updateProdutoNaoArrematado();

	public int updateSituacaoDesativar(long idProduto);

	public List<Produto> findAllDesativados();

	public int updateSituacaoAtivarProduto(long idProduto);

	public int countfindProdutoBycodPatrimonio(String codPatrimonio);

}
