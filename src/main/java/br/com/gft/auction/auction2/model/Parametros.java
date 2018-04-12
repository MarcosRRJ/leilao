package br.com.gft.auction.auction2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Classe responsável por Salvar os Parametros no Banco de dados
 *
 * @author Marcos Rafael
 */
@Entity(name = "parametros")
public class Parametros {

	@Id
	private long idParametro;

	@Column(name = "tempo_acrescimo")
	private int tempoAcrescimo;

	@Column(name = "Leiloes_simultaneos")
	private int leiloesSimultaneos;

	public long getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(long idParametro) {
		this.idParametro = idParametro;
	}

	public int getTempoAcrescimo() {
		return tempoAcrescimo;
	}

	public void setTempoAcrescimo(int tempoAcrescimo) {
		this.tempoAcrescimo = tempoAcrescimo;
	}

	public int getLeiloesSimultaneos() {
		return leiloesSimultaneos;
	}

	public void setLeiloesSimultaneos(int leiloesSimultaneos) {
		this.leiloesSimultaneos = leiloesSimultaneos;
	}

}
