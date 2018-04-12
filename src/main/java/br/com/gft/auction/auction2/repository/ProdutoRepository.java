package br.com.gft.auction.auction2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.model.Unidade;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	/**
	 * Metodo responsavel por pegar o codigo do produtos para verificar se o
	 * mesmo já existe
	 *
	 * @param codPatrimonio
	 * @return
	 */
	public Produto findByCodPatrimonio(String codPatrimonio);

	/**
	 * Metodo responsavel por listar todos os produtos que ainda não estão em
	 * leilões
	 *
	 * @return
	 */
	@Query("select p from produto p where p.situacao = 1 ORDER BY idProduto DESC")
	public List<Produto> findAll();

	/**
	 * Metodo responsavel por listar todos os produtos Desativados pelo admin
	 *
	 * @return
	 */
	@Query("select p from produto p where p.situacao =2 ORDER BY idProduto DESC")
	public List<Produto> findAllDesativados();


	/**
	 * Metodo responsavel por tirar o produto da lista
	 *
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.situacao =0 WHERE p.idProduto = :idProduto")
	public int updateSituacao(@Param("idProduto") long idProduto);

	/**
	 * Metodo responsavel por tirar o produto da lista quando o admin desativar
	 *
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.situacao =2 WHERE p.idProduto = :idProduto")
	public int updateSituacaoDesativar(@Param("idProduto") long idProduto);


	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.situacao =1 WHERE p.idProduto = :idProduto")
	public int updateSituacaoAtivarProduto(@Param("idProduto") long idProduto);

	/**
	 * Metodo responsavel por Editar um certo Produto
	 *
	 * @param Todos
	 *            os Atributos do Produto
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.descricao = :descricao, p.nomeProduto = :nomeProduto,p.codPatrimonio = :codPatrimonio,"
			+ "p.unidadeId = :unidadeId, p.valorInicial = :valorInicial,p.valorPorLance = :valorPorLance WHERE p.idProduto = :idProduto")
	public int updateProduto(@Param("idProduto") long idProduto, @Param("descricao") String descricao,
			@Param("nomeProduto") String nomeProduto, @Param("codPatrimonio") String codPatrimonio,
			@Param("unidadeId") Unidade unidadeId, @Param("valorInicial") double valorInicial,
			@Param("valorPorLance") double valorPorLance);


	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.descricao = :descricao, p.nomeProduto = :nomeProduto,p.codPatrimonio = :codPatrimonio,"
			+ "p.unidadeId = :unidadeId, p.valorInicial = :valorInicial,p.valorPorLance = :valorPorLance WHERE p.idProduto = :idProduto")
	public int updateProdutoSemUnidade(@Param("idProduto") long idProduto, @Param("descricao") String descricao,
			@Param("nomeProduto") String nomeProduto, @Param("codPatrimonio") String codPatrimonio,
			@Param("unidadeId") Unidade unidadeId,@Param("valorInicial") double valorInicial,
			@Param("valorPorLance") double valorPorLance);

	/**
	 * Metodo responsavel por Editar um certo Produto que esteje em um Leilão
	 * agendado
	 *
	 * @param idProduto
	 * @param valorInicial
	 * @param valorPorLance
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.valorInicial = :valorInicial, p.valorPorLance = :valorPorLance WHERE p.idProduto = :idProduto")
	public int updateProdutoLeilao(@Param("valorInicial") double valorInicial,
			@Param("valorPorLance") double valorPorLance, @Param("idProduto") long idProduto);

	@Query("SELECT p.valorInicial FROM produto p WHERE p.idProduto = :idProduto")
	public int pegueValorInicial(@Param("idProduto") long idProduto);

	/**
	 * Metodo responsavel por trazer o produto de volta pra lista agendado
	 *
	 * @param idProduto
	 * @param valorInicial
	 * @param valorPorLance
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE produto p SET p.situacao =1 WHERE p.idProduto in (SELECT l.idProduto FROM leilao l WHERE l.dataInicio < SYSDATE and l.dataFimLeilao < SYSDATE AND l.arrematado = 0)")
	public int updateProdutoNaoArrematado();

	@Query(value = "SELECT Count(id_produto) FROM produto WHERE cod_patrimonio = :codPatrimonio", nativeQuery = true)
	public int pegaQuantidadeProduto(@Param("codPatrimonio") String codPatrimonio);

}
