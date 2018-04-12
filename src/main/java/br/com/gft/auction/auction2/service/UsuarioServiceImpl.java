package br.com.gft.auction.auction2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.TipoUsuario;
import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario;
import br.com.gft.auction.auction2.model.Usuario.SituacaoEnum;
import br.com.gft.auction.auction2.repository.TipoRepository;
import br.com.gft.auction.auction2.repository.UsuarioRepository;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Usuario findUserByEmail(String email) {
		return usuarioRepository.findByEmail(email);
	}

	// Injeta uma instancia de TipoRepository
	@Autowired
	private TipoRepository repository;

	@Autowired
	private void setService(TipoRepository repository) {
		this.repository = repository;
	}

	@Override
	public void saveUser(Usuario user) {
		if (usuarioRepository.count() == 0) {
			TipoUsuario tipo = repository.findByTipoAdm();
			user.setTipo(tipo);
			user.setSituacao(SituacaoEnum.ATIVO.getSituacaoEnum());
		} else {
			TipoUsuario tipo = repository.findByTipoUser();
			user.setTipo(tipo);
			user.setSituacao(SituacaoEnum.ESPERA.getSituacaoEnum());
		}
		usuarioRepository.save(user);
	}

	@Override
	public void saveUserAdmin(Usuario user) {
		TipoUsuario tipo = repository.findByTipoUser();
		user.setTipo(tipo);
		user.setSituacao(SituacaoEnum.ATIVO.getSituacaoEnum());
		usuarioRepository.save(user);
	}

	@Override
	public Usuario findUserById(long id_usuario) {
		return usuarioRepository.findOne(id_usuario);
	}

	@Override
	public int updateSenhaUser(long id_usuario) {
		// user.setSenha(bCryptPasswordEncoder.encode(user.getSenha()));
		return usuarioRepository.updateSenha(id_usuario);
	}

	@Override
	public int updateUsuario(long id_usuario, String nome, String sobrenome, String email, String netId, long cpf,
			Unidade unidadeId) {
		return usuarioRepository.updateUsuario(id_usuario, nome, sobrenome, email, netId, cpf, unidadeId);
	}

	@Override
	public List<Usuario> findByActive() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario findUserBynetIdAndSenha(String netId, String senha) {
		return usuarioRepository.findUserBynetIdAndSenha(netId, senha);
	}

	@Override
	public List<Usuario> findAllUserEspera() {
		return usuarioRepository.findAllUserEspera();
	}

	@Override
	public List<Usuario> findAllDesativado() {
		return usuarioRepository.findAllDesativado();
	}

	@Override
	public int updateDesativarUser(long id_usuario) {
		return usuarioRepository.updateDesativarUser(id_usuario);
	}

	@Override
	public int updateAtivarUser(long id_usuario) {
		return usuarioRepository.updateAtivarUser(id_usuario);
	}

	@Override
	public Usuario findUserByNetId(String netId) {
		return usuarioRepository.findByNetId(netId);
	}

	@Override
	public Usuario findUserByCpf(long cpf) {
		return usuarioRepository.findByCpf(cpf);
	}

	@Override
	public int updateSenhaUsuario(long id_usuario, String novaSenha, String senha) {
		return usuarioRepository.updateSenhaUsuario(id_usuario, novaSenha, senha);
	}

	@Override
	public int updateUsuarioSemUnidade(long id_usuario, String nome, String sobrenome, String email, String netId,
			long cpf) {
		return usuarioRepository.updateUsuarioSemUnidade(id_usuario, nome, sobrenome, email, netId, cpf);
	}

	@Override
	public int countUsuarioEmEspera() {
		
		return usuarioRepository.countUsuarioEmEspera();
	}

}