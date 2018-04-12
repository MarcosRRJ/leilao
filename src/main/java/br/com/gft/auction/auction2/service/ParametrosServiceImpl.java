package br.com.gft.auction.auction2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Parametros;
import br.com.gft.auction.auction2.repository.ParametrosRepository;

@Service("parametrosService")
public class ParametrosServiceImpl implements ParametrosService {

	@Autowired
	private ParametrosRepository parametrosRepository;

	@Override
	public void saveLance(Parametros parametros) {
		parametros.setIdParametro(1);
		parametrosRepository.save(parametros);
	}

	@Override
	public int updateParametrosTempoAcrescimo(int tempoAcrescimo) {
		return parametrosRepository.updateParametrosTempoAcrescimo(tempoAcrescimo);
	}

	@Override
	public int updateParametrosLeiloesSimultaneos(int leiloesSimultaneos) {		
		return parametrosRepository.updateParametrosLeiloesSimultaneos(leiloesSimultaneos);
	}

	@Override
	public Integer peguaNumeroDeLeiloeSimultaneos() {
		return parametrosRepository.peguaNumeroDeLeiloeSimultaneos();
	}

	@Override
	public Integer peguatempoAcrescimo() {		
		return parametrosRepository.peguatempoAcrescimo();
	}

}
