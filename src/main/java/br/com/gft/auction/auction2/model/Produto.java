package br.com.gft.auction.auction2.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * Classe responsável por Salvar os Produtos no Banco de dados
 *
 * @author Marcos Rafael
 */
@Entity(name = "produto")

public class Produto {

	public enum SituacaoEnum {
		ATIVO(1), DESATIVADO(0);

		public int situacaoEnum;

		SituacaoEnum(int valor) {
			situacaoEnum = valor;
		}

		public int getSituacaoEnum() {
			return situacaoEnum;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
	@SequenceGenerator(sequenceName = "produto_seq", allocationSize = 1, name = "CUST_SEQ")
	private long idProduto;

	@Column(name = "nome_Produto")
	private String nomeProduto;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "cod_patrimonio")
	private String codPatrimonio;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_id")
	private Unidade unidadeId;

	@Transient
	private String unidade;

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Column(name = "valor_inicial")
	private double valorInicial;

	@Column(name = "valor_por_lance")
	private double valorPorLance;

	@Column(name = "situacao")
	private int situacao;

	@Column(name = "imagem_path")
	private String imagemPath;

	public String getImagemPath() {
		return imagemPath;
	}

	public void setImagemPath(String imagemPath) {
		this.imagemPath = imagemPath;
	}

	public Unidade getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Unidade unidadeId) {
		this.unidadeId = unidadeId;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCodPatrimonio() {
		return codPatrimonio;
	}

	public void setCodPatrimonio(String codPatrimonio) {
		this.codPatrimonio = codPatrimonio;
	}

	public double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public double getValorPorLance() {
		return valorPorLance;
	}

	public void setValorPorLance(double valorPorLance) {
		this.valorPorLance = valorPorLance;
	}



}
