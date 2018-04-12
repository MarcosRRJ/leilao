package br.com.gft.auction.auction2.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.repository.LeilaoRepository;
import br.com.gft.auction.auction2.repository.ProdutoRepository;

@Service("leilaoService")
public class LeilaoServiceImpl implements LeilaoService {

	@Autowired
	private LeilaoRepository repository;

	@Autowired
	private ProdutoRepository produtorepository;

	public void saveLeilao(Leilao leilao,long idProduto) {
		
		long millisecond = leilao.getDataInicio().getTime(); //pega a data do inicio do  leilÃ£o e converte para milesegundos
		
		long data = (millisecond + leilao.getDuracao()); // soma a data do inicio do leilÃƒÂ£o e a duraÃ§Ã£o
		
		//pega o tempo em milisegundos e converte para calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(data);	
		
		leilao.setValorAtual(produtorepository.pegueValorInicial(idProduto));
		leilao.setDataFimLeilao(calendar);
		repository.save(leilao);
	}

	@Override
	public Leilao findByLeilaoId(long idLeilao) {
		return repository.findOne(idLeilao);
	}

	@Override
	public List<Leilao> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Leilao> findAllArrematados() {
		return repository.findAllArrematados();
	}

	@Override
	public List<Leilao> findAllProximos() {	
		return repository.findAllAgendados();
	}

	@Override
	public int updateLeilao(Timestamp dataInicio, long duracao, long idLeilao) {
		return repository.updateLeilao(dataInicio, duracao, idLeilao);
	}

	@Override
	public int pegaValorAtualLeilao(long idLeilao) {		
		return repository.pegaValorAtualLeilao(idLeilao);
	}

	@Override
	public int verificaNumeroDeLeilaoAcontecendo() {		
		return repository.verificaNumeroDeLeilaoAcontecendo();
	}

	@Override
	public Calendar verificaSeHaLeilaoAcontecendo() {
		return repository.verificaSeHaLeilaoAcontecendo();
	}

	@Override
	public int updateLeilaoNaoArrematado() {
		return repository.updateLeilaoNaoArrematado();
	}

	@Override
	public List<Leilao> pegaInfoRefresh() {
		return repository.pegaInfoRefresh();
	}

	@Override
	public int updateDesativaLeilaoAgendado(long idLeilao) {
		return repository.updateDesativaLeilaoAgendado(idLeilao);
	}

	@Override
	public List<Leilao> findAllIdLeilaoProximosleiloes() {
		return repository.findAllIdLeilaoAgendados();
	}

	@Override
	public int verificaNumeroDeLeilaoComADataAgendade(String dataInicio) {
		return repository.verificaNumeroDeLeilaoComADataAgendade(dataInicio);
	}
}