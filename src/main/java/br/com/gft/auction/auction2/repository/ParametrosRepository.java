package br.com.gft.auction.auction2.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Parametros;

public interface ParametrosRepository extends JpaRepository<Parametros, Long> {

	/**
	 * Metodo responsavel por fazer o update do tempo de Acrescimo do fim Leilão
	 * 
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE parametros p SET p.tempoAcrescimo = :tempoAcrescimo")
	public int updateParametrosTempoAcrescimo(@Param("tempoAcrescimo") int tempoAcrescimo);

	/**
	 * Metodo responsavel por fazer o update do numero de leilões simultanios
	 * 
	 * @param idLeilao
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE parametros p SET p.leiloesSimultaneos = :leiloesSimultaneos")
	public int updateParametrosLeiloesSimultaneos(@Param("leiloesSimultaneos") int leiloesSimultaneos);

	/**
	 * Metodo responsavel por peguar o tempo Acrescimo do fim leilao
	 * 
	 * @return
	 */
	@Query(value = "SELECT p.tempo_Acrescimo FROM parametros p", nativeQuery = true)
	public Integer peguatempoAcrescimo();

	/**
	 * Metodo responsavel por peguar o numero de leilões simultanios
	 * 
	 * @return
	 */
	@Query(value = "SELECT p.Leiloes_simultaneos FROM parametros p", nativeQuery = true)
	public Integer peguaNumeroDeLeiloeSimultaneos();

}
