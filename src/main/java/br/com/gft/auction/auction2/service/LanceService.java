package br.com.gft.auction.auction2.service;

import java.util.List;

import br.com.gft.auction.auction2.model.Lance;
import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Usuario;

public interface LanceService {
	
	public void saveLance(Lance lance,Leilao leilao, long idLeilao);
	
	public int findByLanceId(Leilao idLeilao);
	
	public List<?> pegaValorUltimoLanceUsuarioDeUmCertoLeilao(Usuario idUsuario);
	
	public int peguaValorPorLanceDoProduto(Leilao idLeilao);
}
