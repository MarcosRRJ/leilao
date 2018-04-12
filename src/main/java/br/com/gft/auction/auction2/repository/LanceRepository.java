package br.com.gft.auction.auction2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Lance;
import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Usuario;

public interface LanceRepository extends JpaRepository<Lance, Long> {

	/**
	 * Metodo responsavel por pegar o Valor do Ultimo Lance do Usuario De Um
	 * Certo Leilao
	 * 
	 * @param idUsuario
	 * @param idLeilao
	 * @return
	 */
	@Query(value = "SELECT new map(Max(l.valorLance) as valorLance, l.idLeilao as idLeilao) FROM lance l WHERE l.idUsuario = :idUsuario "
			+ " and l.idLeilao IN (SELECT le.idLeilao FROM leilao le where (le.dataFimLeilao > sysdate and le.dataInicio < SYSDATE)) "
			+ " group by l.idLeilao ")
	public List<?> pegaValorUltimoLanceUsuarioDeUmCertoLeilao(@Param("idUsuario") Usuario idUsuario);
	
	/**
	 * Metodo responsavel por pegar a quantidade de Lance De Um certo Leilao
	 * 
	 * @param idLeilao
	 * @return
	 */
	@Query(value = "SELECT Count(l.id_lance) FROM lance l where l.id_Leilao = :idLeilao", nativeQuery = true)
	public int pegalance(@Param("idLeilao") Leilao idLeilao);
}
