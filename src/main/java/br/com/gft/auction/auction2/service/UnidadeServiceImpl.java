package br.com.gft.auction.auction2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Unidade;
import br.com.gft.auction.auction2.model.Usuario.SituacaoEnum;
import br.com.gft.auction.auction2.repository.UnidadeRepository;

@Service("unidadeService")
public class UnidadeServiceImpl implements UnidadeService {

	@Autowired
	private UnidadeRepository unidadeRepository;

	@Override
	public void saveUnidade(Unidade unidade) {
		unidade.setSituacao(SituacaoEnum.ATIVO.getSituacaoEnum());
		unidadeRepository.save(unidade);
	}

	@Override
	public List<Unidade> findByActive() {
		return unidadeRepository.findAll();
	}

	@Override
	public Unidade findUnidadeById(long unidadeId) {
		return unidadeRepository.findOne(unidadeId);
	}

	@Override
	public Unidade findUnidade(String unidadeNome) {
		return unidadeRepository.findByUnidadeNome(unidadeNome);
	}

	@Override
	public int updateSituacao(long unidadeId) {
		return unidadeRepository.updateSituacao(unidadeId);
	}

	@Override
	public int updateUnidade(long unidadeId, String unidadeNome) {
		return unidadeRepository.updateUnidade(unidadeId, unidadeNome);
	}

	@Override
	public List<Unidade> findAllDesativadas() {
		return unidadeRepository.findAllDesativadas();
	}

	@Override
	public int updateReativar(long unidadeId) {
		return unidadeRepository.updateReativar(unidadeId);
	}

}
