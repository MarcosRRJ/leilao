package br.com.gft.auction.auction2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe responsável por Salvar os Tipo de Usuarios Existentes no Banco de dados
 *
 * @author Marcos Rafael
 */
@Entity
@Table(name = "tipo_usuario")
public class TipoUsuario {
		
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_USUARIO_seq")
	@SequenceGenerator(sequenceName = "TIPO_USUARIO_seq", allocationSize = 1, name = "TIPO_USUARIO_seq")
	@Column(name = "id_tipo")
	private int id_tipo;

	@Column(name = "tipo")
	private String tipo;

	public int getId() {
		return id_tipo;
	}

	public void setId(int id_tipo) {
		this.id_tipo = id_tipo;
	}

	public String getRole() {
		return tipo;
	}

	public void setRole(String tipo) {
		this.tipo = tipo;
	}

}
