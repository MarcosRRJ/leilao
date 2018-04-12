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

/**
 * Classe responsável por Salvar os Lances no Banco de Dados
 *
 * @author Marcos Rafael
 */


@Entity(name = "lance")
public class Lance {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lance_seq")
	@SequenceGenerator(sequenceName = "lance_seq", allocationSize = 1, name = "lance_seq")
	private long idLance;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_leilao")
	private Leilao idLeilao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	private Usuario idUsuario;
	
	@Column(name = "valor_lance")
	private double valorLance;
	
	public long getIdLance() {
		return idLance;
	}

	public void setIdLance(long idLance) {
		this.idLance = idLance;
	}

	public Leilao getIdLeilao() {
		return idLeilao;
	}

	public void setIdLeilao(Leilao idLeilao) {
		this.idLeilao = idLeilao;
	}

	public Usuario getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Usuario idUsuario) {
		this.idUsuario = idUsuario;
	}

	public double getValorLance() {
		return valorLance;
	}

	public void setValorLance(double valorLance) {
		this.valorLance = valorLance;
	}

}
