package br.com.gft.auction.auction2.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Metodo responsavel por verificar se já existe esse email
	 * 
	 * @param email
	 * @return
	 */
	public Usuario findByEmail(String email);
	
	
	public Usuario findOneByNetId(String netId);

	/**
	 * Metodo responsavel por verificar se já existe esse netId
	 * 
	 * @param netId
	 * @return
	 */
	public Usuario findByNetId(String netId);

	/**
	 * Metodo responsavel por verificar se já existe esse cpf
	 * 
	 * @param cpf
	 * @return
	 */
	public Usuario findByCpf(long cpf);

	/**
	 * Metodo responsavel para pegar o usuario com esses parametro para fazer o
	 * login
	 * 
	 * @param netId
	 * @param senha
	 * @return
	 */
	@Query("select u from usuario u where u.netId = :netId and u.senha= :senha")
	Usuario findUserBynetIdAndSenha(@Param("netId") String netId, @Param("senha") String senha);

	/**
	 * Metodo responsavel por listar todos os Usuarios Ativos
	 * 
	 * @return
	 */
	@Query("select u from usuario u where u.situacao = 1")
	public List<Usuario> findAll();

	/**
	 * Metodo responsavel por listar todos os Usuarios em Espera
	 * 
	 * @return
	 */
	@Query("select u from usuario u where u.situacao = 2")
	public List<Usuario> findAllUserEspera();

	/**
	 * Metodo responsavel por listar todos os Usuarios Desativados
	 * 
	 * @return
	 */
	@Query("select u from usuario u where u.situacao = 0")
	public List<Usuario> findAllDesativado();

	/**
	 * Metodo responsavel por Desativar o usuario
	 * 
	 * @param id_usuario
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.situacao =0 WHERE u.id_usuario = :id_usuario")
	int updateDesativarUser(@Param("id_usuario") long id_usuario);

	/**
	 * Metodo responsavel por Ativar o usuario
	 * 
	 * @param id_usuario
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.situacao =1 WHERE u.id_usuario = :id_usuario")
	int updateAtivarUser(@Param("id_usuario") long id_usuario);

	/**
	 * Metodo responsavel Resetar a Senha de um certo usuario
	 * 
	 * @param id_usuario
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.senha = 'Gftgft0000' WHERE u.id_usuario = :id_usuario")
	int updateSenha(@Param("id_usuario") long id_usuario);

	/**
	 * Metodo responsavel update do usuario
	 * 
	 * @param Todos os atributos do Usuario
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.nome= :nome,u.sobrenome= :sobrenome,u.email= :email,u.netId= :netId,u.cpf= :cpf,u.unidadeId = :unidadeId WHERE u.id_usuario = :id_usuario")
	int updateUsuario(@Param("id_usuario") long id_usuario, @Param("nome") String nome,
			@Param("sobrenome") String sobrenome, @Param("email") String email, @Param("netId") String netId,
			@Param("cpf") long cpf, @Param("unidadeId") Unidade unidadeId);

	
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.nome= :nome,u.sobrenome= :sobrenome,u.email= :email,u.netId= :netId,u.cpf= :cpf WHERE u.id_usuario = :id_usuario")
	int updateUsuarioSemUnidade(@Param("id_usuario") long id_usuario, @Param("nome") String nome,
			@Param("sobrenome") String sobrenome, @Param("email") String email, @Param("netId") String netId,
			@Param("cpf") long cpf);

	/**
	 * Metodo responsavel por fazer o update da Senha de um certo usuario
	 * 
	 * @param id_usuario
	 * @return
	 */
	@Modifying
	@Transactional
	@Query("UPDATE usuario u SET u.senha = :novaSenha WHERE u.id_usuario = :id_usuario and u.senha= :senha")
	int updateSenhaUsuario(@Param("id_usuario") long id_usuario, @Param("novaSenha") String novaSenha, @Param("senha") String senha);

	/**
	 * 
	 * @return
	 */
	@Query("SELECT Count(u.id_usuario) FROM usuario u where u.situacao = 2")
	public int countUsuarioEmEspera();

}