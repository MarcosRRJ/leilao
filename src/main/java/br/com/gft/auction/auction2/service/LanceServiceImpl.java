package br.com.gft.auction.auction2.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Lance;
import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.Usuario;
import br.com.gft.auction.auction2.repository.LanceRepository;
import br.com.gft.auction.auction2.repository.LeilaoRepository;
import br.com.gft.auction.auction2.repository.ParametrosRepository;

@Service("lanceService")
public class LanceServiceImpl implements LanceService {

	@Autowired
	private LanceRepository lancerepository;

	@Autowired
	private LeilaoRepository leilaorepository;

	@Autowired
	private ParametrosRepository parametrorepository;

	@Override
	public void saveLance(Lance lance, Leilao leilao, long idLeilao) {
		
		//pega a tempo do parametro de acrescimo do leilão
		int tempoAcrescimoEmMilissegundos = (parametrorepository.peguatempoAcrescimo() * 1000);

		//pega a tempo do parametro de acrescimo do leilão e subtrai tempo final do leilão 
		long dataVerificacao = (leilao.getDataFimLeilao().getTimeInMillis() - tempoAcrescimoEmMilissegundos);

		//pega o tempo final do leilão e convert em Data
		Date dataFim = new Date(leilao.getDataFimLeilao().getTimeInMillis());

		//pega a sysdate do sistema e convert em Data
		Timestamp dataAtual = new Timestamp(System.currentTimeMillis());
		long start = dataAtual.getTime();
		Date sysDate = new Date(start);

		//convert em data o tempo que sera ativo acrecimo de tempo
		Date dataAction = new Date(dataVerificacao);
		

		if ((sysDate.after(dataAction)) && (sysDate.before(dataFim))) {

			
			long acrescentaDiferencaTempoLance = start + tempoAcrescimoEmMilissegundos;

//			Date dataA = new Date(acrescentaDiferencaTempoLance);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(acrescentaDiferencaTempoLance);			
			leilao.setDataFimLeilao(calendar);
			
			leilaorepository.updateLeilaoConflito(idLeilao);

		}
		leilaorepository.updateUsuarioemLeilao(lance.getIdUsuario(), idLeilao);		
		leilaorepository.updateLeilaoLanceAtual(idLeilao);
		lance.setValorLance(leilaorepository.pegaValorAtualLeilao(idLeilao));
		lancerepository.save(lance);
	}

	@Override
	public int findByLanceId(Leilao idLeilao) {
		return lancerepository.pegalance(idLeilao);
	}


	@Override
	public List<?> pegaValorUltimoLanceUsuarioDeUmCertoLeilao(Usuario idUsuario) {
		return lancerepository.pegaValorUltimoLanceUsuarioDeUmCertoLeilao(idUsuario);
	}

	@Override
	public int peguaValorPorLanceDoProduto(Leilao idLeilao) {
		return leilaorepository.peguaValorPorLanceDoProduto(idLeilao);
	}

}
