package br.com.gft.auction.auction2.service;

import br.com.gft.auction.auction2.model.Parametros;

public interface ParametrosService {
	
	public void saveLance(Parametros parametros);
	
	public int updateParametrosTempoAcrescimo(int tempoAcrescimo);
	
	public int updateParametrosLeiloesSimultaneos(int leiloesSimultaneos);
	
	public Integer peguaNumeroDeLeiloeSimultaneos();
	
	public Integer peguatempoAcrescimo();
}
