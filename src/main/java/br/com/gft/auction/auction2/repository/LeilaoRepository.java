package br.com.gft.auction.auction2.repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Usuario;

public interface LeilaoRepository extends JpaRepository<Leilao, Long> {

	/**
	 * Metodo responsavel por listar todos os Leilões em Andamentos
	 *
	 * @return
	 */
	@Query("select l from leilao l where (l.dataFimLeilao > sysdate and l.dataInicio < sysdate) and l.arrematado <> 2  ORDER BY idLeilao DESC")
	public List<Leilao> findAll();

	/**
	 * Metodo responsavel por listar todos os Leilões Arrematados
	 *
	 * @return
	 */
	@Query("select l, lan from leilao l, lance lan where l.arrematado = 1 " + "and l.dataFimLeilao < sysdate "
			+ "and l.valorAtual = lan.valorLance ORDER BY idLeilao DESC")
	public List<Leilao> findAllArrematados();

	/**
	 * Metodo responsavel por listar todos os Leilões Agendados
	 *
	 * @return
	 */
	@Query("SELECT l FROM leilao l WHERE l.dataInicio > SYSDATE and arrematado = 0 ORDER BY dataInicio, idLeilao ASC")
	public List<Leilao> findAllAgendados();

	/**
	 * Metodo responsavel por listar todos os Leilões Agendados
	 *
	 * @return
	 */
	@Query("SELECT idLeilao FROM leilao l WHERE l.dataInicio > sysdate and arrematado = 0 ORDER BY dataInicio, idLeilao ASC")
	public List<Leilao> findAllIdLeilaoAgendados();

	/**
	 * Metodo responsavel por fazer o update Leilões Agendados
	 *
	 * @param dataInicio
	 * @param duracao
	 * @param dataFimLeilao
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE leilao l SET l.dataInicio = :dataInicio, l.duracao = :duracao WHERE l.idLeilao = :idLeilao")
	public int updateLeilao(@Param("dataInicio") Timestamp dataInicio, @Param("duracao") long duracao,
			@Param("idLeilao") long idLeilao);

	/**
	 * Metodo responsavel por fazer o update Leilões do valor atual do leilão
	 *
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE leilao l "
			+ "SET l.valor_Atual = l.valor_Atual + (SELECT p.valor_Por_Lance FROM produto p, leilao l WHERE p.id_Produto = l.id_Produto and l.id_Leilao= :idLeilao)"
			+ "WHERE l.id_Leilao= :idLeilao", nativeQuery = true)
	public int updateLeilaoLanceAtual(@Param("idLeilao") long idLeilao);

	/**
	 * Metodo responsavel por pegar Valor Atual Leilao
	 *
	 * @param idLeilao
	 * @return
	 */
	@Query("select l.valorAtual from leilao l where idLeilao= :idLeilao")
	public int pegaValorAtualLeilao(@Param("idLeilao") long idLeilao);

	/**
	 * Metodo responsavel por setar o valor do lance de cada usuario na tabela
	 *
	 * @param idLeilao
	 * @return
	 */
	@Query(value = "SELECT p.valor_por_lance FROM produto p, leilao l WHERE p.id_produto = l.id_produto and l.id_Leilao = :idLeilao", nativeQuery = true)
	public int peguaValorPorLanceDoProduto(@Param("idLeilao") Leilao idLeilao);

	/**
	 * Metodo responsavel por verificar Se Ha Algum Leilao Acontecendo
	 *
	 * @return
	 */
	@Query("SELECT Count(l.idLeilao) FROM leilao l WHERE l.dataInicio < SYSDATE AND l.dataFimLeilao > SYSDATE and l.arrematado <> 2")
	public int verificaNumeroDeLeilaoAcontecendo();


//	@Query(value ="SELECT Count(id_leilao) FROM leilao"
//			+ " WHERE data_fim_leilao > TO_DATE(TO_CHAR(TO_TIMESTAMP(=:dataInicio, 'YYYY-MM-DD HH24:MI:SS.FF'),'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS') "
//			+ "AND data_Inicio <= TO_DATE(TO_CHAR(TO_TIMESTAMP(=:dataInicio, 'YYYY-MM-DD HH24:MI:SS.FF'),'MM/DD/YYYY HH24:MI:SS'),'MM/DD/YYYY HH24:MI:SS')", nativeQuery = true)
//	public int verificaNumeroDeLeilaoComADataAgendade(@Param("dataInicio") Timestamp dataInicio);

	@Query(value ="SELECT Count(id_leilao) FROM leilao"
			+ " WHERE data_fim_leilao > TO_TIMESTAMP(:dataInicio, 'YYYY-MM-DD hh24:mi:ss')"
			+ "AND data_inicio <= TO_TIMESTAMP(:dataInicio, 'YYYY-MM-DD hh24:mi:ss') "
			+ "AND arrematado =0 ", nativeQuery = true)
	public int verificaNumeroDeLeilaoComADataAgendade(@Param("dataInicio") String dataInicio);
	/**
	 * Metodo responsavel por verificar Se Ha Algum Leilao Acontecendo
	 *
	 * @return
	 */
	@Query("SELECT max(l.dataFimLeilao) FROM leilao l WHERE l.dataInicio < SYSDATE AND l.dataFimLeilao > SYSDATE")
	public Calendar verificaSeHaLeilaoAcontecendo();

	/**
	 * Metodo responsavel por fazer o update da data de inicio de um leilao
	 * agendado para não acontecer um conflito
	 *
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE leilao l "
			+ "SET l.dataInicio = ((SELECT l.dataFimLeilao FROM leilao l WHERE l.idLeilao = :idLeilao) + (SELECT tempoAcrescimo  * 1000 FROM parametros)),"
			+ "l.dataFimLeilao = ((l.dataFimLeilao) + (SELECT tempoAcrescimo  * 1000 FROM parametros))  "
			+ "WHERE l.dataInicio < (SELECT l.dataFimLeilao FROM leilao l WHERE l.idLeilao = :idLeilao) "
			+ "AND l.dataFimLeilao > (SELECT l.dataFimLeilao FROM leilao l WHERE l.idLeilao = :idLeilao)"
			+ "AND dataInicio > SYSDATE ")
	public int updateLeilaoConflito(@Param("idLeilao") long idLeilao);

	/**
	 * Metodo responsavel por desativar o Leilao não arrematado arrematado
	 *
	 * @param idProduto
	 * @param valorInicial
	 * @param valorPorLance
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE leilao l SET l.arrematado =2 WHERE l.idLeilao in (SELECT l.idLeilao FROM leilao l WHERE l.dataInicio < SYSDATE and l.dataFimLeilao < SYSDATE AND l.arrematado = 0)")
	public int updateLeilaoNaoArrematado();

	/**
	 * Metodo responsavel por desativar o Leilao agendado
	 *
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE leilao l SET l.arrematado =2 where idLeilao = :idLeilao")
	public int updateDesativaLeilaoAgendado(@Param("idLeilao") long idLeilao);

	/**
	 * Metodo responsavel por definir o usuario que de lance
	 *
	 * @param idUsuario
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE leilao l SET l.idUsuario = :idUsuario WHERE l.idLeilao = :idLeilao")
	public int updateUsuarioemLeilao(@Param("idUsuario") Usuario idUsuario, @Param("idLeilao") long idLeilao);

	@Query("SELECT l FROM leilao l "
			+ "where (l.dataFimLeilao > sysdate and l.dataInicio < sysdate) and l.arrematado <> 2 ORDER BY idLeilao DESC")
	public List<Leilao> pegaInfoRefresh();

}
