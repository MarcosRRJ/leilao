package br.com.gft.auction.auction2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.LeilaoArrematado;

public interface LeilaoArrematadoRepository extends JpaRepository<LeilaoArrematado, Long> {

	@Query(value ="SELECT Max(id_leilao) AS id_leilao "
			+ "from LEILAO l "
			+ "WHERE data_fim_leilao < SYSDATE "
			+ "AND arrematado = 1 "
			+ "AND id_leilao NOT in (SELECT arre.id_leilao FROM leilao_arrematado arre)", nativeQuery = true)
	public int updateLeilaoConflito();

	/**
	 * Metodo responsavel por fazer fazer um insert na tabela leilão arrematado
	 * 
	 * @return
	 */
	
	@Query(value = "SELECT l.id_Leilao FROM LEILAO_ARREMATADO l WHERE l.id_Leilao = :leilao", nativeQuery = true)
	public int pegaLeilaoArrematado(@Param("leilao") Leilao leilao);

	@Query(value = "SELECT * from LEILAO_ARREMATADO ORDER BY data_arremate DESC", nativeQuery = true)
	public List<LeilaoArrematado> findAll();
	
	@Query(value = "SELECT * from LEILAO_ARREMATADO WHERE netid_ganhador = :netId ORDER BY ID_LEILAO_ARREMATADO DESC", nativeQuery = true)
	public List<LeilaoArrematado> meusLeilões(@Param("netId") String netId);
	
	@Query(value = "SELECT Count(id_leilao_arrematado) FROM leilao_arrematado", nativeQuery = true)
	public int pegaQuantidadeLeilao();
	
	@Query(value ="SELECT Max(id_Leilao) from LEILAO_ARREMATADO", nativeQuery = true)
	public int pegaUltimoIdLeilaoArrematado();

}
