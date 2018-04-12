package br.com.gft.auction.auction2.model;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 * Classe responsável por Salvar os Leilao no Banco de Dados
 *
 * @author Marcos Rafael
 */

@Entity(name = "leilao")
public class Leilao {

	public enum SituacaoEnum {
		ATIVO(1), DESATIVADO(0), NAOARREMATADO(2);

		public int situacaoEnum;

		SituacaoEnum(int valor) {
			situacaoEnum = valor;
		}

		public int getSituacaoEnum() {
			return situacaoEnum;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leilao_seq")
	@SequenceGenerator(sequenceName = "leilao_seq", allocationSize = 1, name = "leilao_seq")
	private long idLeilao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_produto")
	private Produto idProduto;

	@Column(name = "data_inicio")
	private Timestamp dataInicio = new Timestamp(System.currentTimeMillis());

	@Column(name = "duracao")
	private long duracao;

	@Column(name = "arrematado")
	private int arrematado;

	@Column(name = "data_fim_leilao")
	private Calendar dataFimLeilao;

	@Column(name = "valor_Atual")
	private double valorAtual;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario idUsuario;

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	
	public double getValorAtual() {
		return valorAtual;
	}

	public void setValorAtual(double valorAtual) {
		this.valorAtual = valorAtual;
	}

	public int getArrematado() {
		return arrematado;
	}

	public void setArrematado(int arrematado) {
		this.arrematado = arrematado;
	}

	public long getIdLeilao() {
		return idLeilao;
	}

	public void setIdLeilao(long idLeilao) {
		this.idLeilao = idLeilao;
	}

	public Produto getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Produto idProduto) {
		this.idProduto = idProduto;
	}

	public long getDuracao() {
		return duracao;
	}

	public void setDuracao(long duracao) {
		this.duracao = duracao;
	}

	public Timestamp getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Calendar getDataFimLeilao() {
		return dataFimLeilao;
	}

	public void setDataFimLeilao(Calendar dataInicio) {
		this.dataFimLeilao = dataInicio;
	}
}
