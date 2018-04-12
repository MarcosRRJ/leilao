package br.com.gft.auction.auction2.service;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import br.com.gft.auction.auction2.model.Leilao;

public interface LeilaoService {

	public void saveLeilao(Leilao leilao, long idProduto);

	public List<Leilao> findAll();

	public List<Leilao> findAllArrematados();

	public List<Leilao> findAllProximos();
	
	public List<Leilao> findAllIdLeilaoProximosleiloes();

	public Leilao findByLeilaoId(long idLeilao);

	int updateLeilao(Timestamp dataInicio, long duracao, long idLeilao);

	public int pegaValorAtualLeilao(long idLeilao);
	
	public int verificaNumeroDeLeilaoAcontecendo();
	
	public Calendar verificaSeHaLeilaoAcontecendo();
	
	public int updateLeilaoNaoArrematado();
	
	public List<Leilao> pegaInfoRefresh();
	
	public int updateDesativaLeilaoAgendado(long idLeilao);
	
	public int verificaNumeroDeLeilaoComADataAgendade(String dataInicio);

}
