package br.com.gft.auction.auction2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Classe responsável por Salvar as Unidades no Banco de dados
 *
 * @author Marcos Rafael
 */
@Entity(name = "unidade")
public class Unidade {

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_seq")
	@SequenceGenerator(sequenceName = "unidade_seq", allocationSize = 1, name = "unidade_seq")
	private long unidadeId;

	@Column(name = "nome_unidade")
	private String unidadeNome;

	public long getUnidadeId() {
		return unidadeId;
	}

	@Column(name = "situacao")
	private int situacao;

	public void setUnidadeId(long unidadeId) {
		this.unidadeId = unidadeId;
	}

	public String getUnidadeNome() {
		return unidadeNome;
	}

	public void setUnidadeNome(String unidadeNome) {
		this.unidadeNome = unidadeNome;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

}
