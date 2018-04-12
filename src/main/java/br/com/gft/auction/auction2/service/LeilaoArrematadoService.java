package br.com.gft.auction.auction2.service;

import java.util.List;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.LeilaoArrematado;

public interface LeilaoArrematadoService {
	
	public int pegaLeilaoArrematado(Leilao leilao);
	
	public List<LeilaoArrematado> findAll();
	
	public void saveLeilãoArrematado(LeilaoArrematado leilaoArrematado); 
	
	public List<LeilaoArrematado> meusLeilões(String netId);
	
	public int updateLeilaoConflito();
	
	public int pegaQuantidadeLeilao();
	
	public int pegaUltimoIdLeilaoArrematado();
	
}
