package br.com.gft.auction.auction2.model;

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
 * Classe responsável por Salvar os LEILOES ARREMATADO no Banco de dados
 *
 * @author Marcos Rafael
 */

@Entity(name = "LEILAO_ARREMATADO")
public class LeilaoArrematado {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEILAO_ARREMATADO_SEQ")
	@SequenceGenerator(sequenceName = "LEILAO_ARREMATADO_SEQ", allocationSize = 1, name = "LEILAO_ARREMATADO_SEQ")
	private long idLeilaoArrematado;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "id_usuario")
//	private Usuario idUsuario;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_leilao")
	private Leilao idLeilao;

	@Column(name = "netid_ganhador")
	private String netidGanhador;

	@Column(name = "nome_ganhador")
	private String nomeGanhador;

	@Column(name = "unidade_ganhador")
	private String unidadeGanhador;

	@Column(name = "nome_produto")
	private String nomeProduto;

	@Column(name = "unidade_produto")
	private String unidadeProduto;

	@Column(name = "n_patrimonio_produto")
	private String nPatrimonioProduto;

	@Column(name = "valor_arrematado")
	private double valorArrematado;

	@Column(name = "data_arremate")
	private Calendar dataArremate;

	public String getNetidGanhador() {
		return netidGanhador;
	}

	public void setNetidGanhador(String netidGanhador) {
		this.netidGanhador = netidGanhador;
	}

	public String getNomeGanhador() {
		return nomeGanhador;
	}

	public void setNomeGanhador(String nomeGanhador) {
		this.nomeGanhador = nomeGanhador;
	}

	public String getUnidadeGanhador() {
		return unidadeGanhador;
	}

	public void setUnidadeGanhador(String unidadeGanhador) {
		this.unidadeGanhador = unidadeGanhador;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUnidadeProduto() {
		return unidadeProduto;
	}

	public void setUnidadeProduto(String unidadeProduto) {
		this.unidadeProduto = unidadeProduto;
	}

	public String getNPatrimonioProduto() {
		return nPatrimonioProduto;
	}

	public void setNPatrimonioProduto(String nPatrimonioProduto) {
		this.nPatrimonioProduto = nPatrimonioProduto;
	}

	public double getValorArrematado() {
		return valorArrematado;
	}

	public void setValorArrematado(double valorArrematado) {
		this.valorArrematado = valorArrematado;
	}

	public Calendar getDataArremate() {
		return dataArremate;
	}

	public void setDataArremate(Calendar dataArremate) {
		this.dataArremate = dataArremate;
	}

	public long getIdLeilaoArrematado() {
		return idLeilaoArrematado;
	}

	public void setIdLeilaoArrematado(long idLeilaoArrematado) {
		this.idLeilaoArrematado = idLeilaoArrematado;
	}

//	public Usuario getIdUsuario() {
//		return idUsuario;
//	}
//
//	public void setIdUsuario(Usuario idUsuario) {
//		this.idUsuario = idUsuario;
//	}

	public Leilao getIdLeilao() {
		return idLeilao;
	}

	public void setIdLeilao(Leilao idLeilao) {
		this.idLeilao = idLeilao;
	}

}