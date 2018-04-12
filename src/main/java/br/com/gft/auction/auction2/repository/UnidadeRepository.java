package br.com.gft.auction.auction2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Unidade;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

	/**
	 * Metodo responsavel por verificar se já existe essa Unidade
	 *
	 * @return
	 */
	Unidade findByUnidadeNome(String unidadeNome);

	/**
	 * Metodo responsavel por listar todos as Unidade ativas
	 *
	 * @return
	 */
	@Query("select u from unidade u where u.situacao = 1 ORDER BY unidadeId ASC")
	public List<Unidade> findAll();

	/**
	 * Metodo responsavel por listar todos as Unidade Desativadas
	 *
	 * @return
	 */
	@Query("select u from unidade u where u.situacao = 0 ORDER BY unidadeId ASC")
	public List<Unidade> findAllDesativadas();


	/**
	 * Metodo responsavel por desativar uma unidade
	 *
	 * @param unidadeId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE unidade u SET u.situacao =0 WHERE u.unidadeId = :unidadeId")
	int updateSituacao(@Param("unidadeId") long unidadeId);

	/**
	 * Metodo responsavel por desativar uma unidade
	 *
	 * @param unidadeId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE unidade u SET u.situacao =1 WHERE u.unidadeId = :unidadeId")
	int updateReativar(@Param("unidadeId") long unidadeId);

	/**
	 * Metodo responsavel por fazer o update de uma unidade
	 *
	 * @param unidadeId
	 * @param unidadeNome
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE unidade u SET u.unidadeNome= :unidadeNome WHERE u.unidadeId = :unidadeId")
	int updateUnidade(@Param("unidadeId") long unidadeId, @Param("unidadeNome") String unidadeNome);

}
