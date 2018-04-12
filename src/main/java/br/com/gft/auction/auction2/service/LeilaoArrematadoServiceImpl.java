package br.com.gft.auction.auction2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.LeilaoArrematado;
import br.com.gft.auction.auction2.repository.LeilaoArrematadoRepository;

@Service("leilaoArrematadoService")
public class LeilaoArrematadoServiceImpl implements LeilaoArrematadoService{

	
	@Autowired
	private LeilaoArrematadoRepository arrematadoRepository;

	@Override
	public int pegaLeilaoArrematado(Leilao leilao) {
		return arrematadoRepository.pegaLeilaoArrematado(leilao);
	}

	@Override
	public List<LeilaoArrematado> findAll() {
		return arrematadoRepository.findAll();
	}

	@Override
	public void saveLeilãoArrematado(LeilaoArrematado leilaoArrematado) {
		arrematadoRepository.save(leilaoArrematado);
	}

	@Override
	public List<LeilaoArrematado> meusLeilões(String netId) {
		return arrematadoRepository.meusLeilões(netId);
	}

	@Override
	public int updateLeilaoConflito() {
		return arrematadoRepository.updateLeilaoConflito();
	}

	@Override
	public int pegaQuantidadeLeilao() {
		return arrematadoRepository.pegaQuantidadeLeilao();
	}

	@Override
	public int pegaUltimoIdLeilaoArrematado() {
	
		return arrematadoRepository.pegaUltimoIdLeilaoArrematado();
	}
}
