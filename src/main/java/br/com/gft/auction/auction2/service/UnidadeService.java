package br.com.gft.auction.auction2.service;

import java.util.List;

import br.com.gft.auction.auction2.model.Unidade;

public interface UnidadeService {

	public void saveUnidade(Unidade unidade);

	public List<Unidade> findByActive();

	public List<Unidade> findAllDesativadas();

	public Unidade findUnidadeById(long unidadeId);

	public Unidade findUnidade(String unidadeNome);

	public int updateSituacao(long unidadeId);

	public int updateUnidade(long unidadeId, String unidadeNome);

	int updateReativar(long unidadeId);

}
