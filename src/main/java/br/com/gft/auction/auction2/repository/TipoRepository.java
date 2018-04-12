package br.com.gft.auction.auction2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.gft.auction.auction2.model.TipoUsuario;


public interface TipoRepository extends JpaRepository<TipoUsuario, Integer> {
	
	/**
	 * Metodo responsavel por pegar tipo usuario
	 * 
	 * @return
	 */
	@Query(value = "SELECT * FROM tipo_usuario t WHERE t.id_tipo =1", nativeQuery = true)
	public TipoUsuario findByTipoAdm();
	
	@Query(value = "SELECT * FROM tipo_usuario t WHERE t.id_tipo =2", nativeQuery = true)
	public TipoUsuario findByTipoUser();


}
