package br.com.gft.auction.auction2.service;

import java.util.List;

import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario;

public interface UsuarioService {

	public Usuario findUserByEmail(String email);

	public Usuario findUserByNetId(String netId);

	public Usuario findUserByCpf(long cpf);

	public Usuario findUserBynetIdAndSenha(String netId, String senha);

	public List<Usuario> findByActive();

	public void saveUser(Usuario user);

	public void saveUserAdmin(Usuario user);

	public Usuario findUserById(long id_usuario);

	public int updateDesativarUser(long id_usuario);

	public int updateAtivarUser(long id_usuario);

	public int updateSenhaUser(long id_usuario);

	public int updateUsuario(long id_usuario, String nome, String sobrenome, String email, String netId, long cpf,
			Unidade unidadeId);

	public List<Usuario> findAllUserEspera();

	public List<Usuario> findAllDesativado();

	public int updateSenhaUsuario(long id_usuario, String novaSenha, String senha);

	public int updateUsuarioSemUnidade(long id_usuario, String nome, String sobrenome, String email, String netId, long cpf);

	public int countUsuarioEmEspera();
}
