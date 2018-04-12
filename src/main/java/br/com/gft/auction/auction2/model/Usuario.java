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
 * Classe responsável por Salvar os Usuarios no Banco de dados
 *
 * @author Marcos Rafael
 */
@Entity(name = "usuario")
public class Usuario {

	public enum SituacaoEnum {
		ATIVO(1), DESATIVADO(0),ESPERA(2);

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
	@SequenceGenerator(sequenceName = "USUARIO_seq", allocationSize = 1, name = "CUST_SEQ")
	private long id_usuario;

	@Column(name = "nome")
	private String nome;

	@Column(name = "sobrenome")
	private String sobrenome;

	@Column(name = "email")
	private String email;

	@Column(name = "net_id")
	private String netId;

	@Column(name = "cpf")
	private long cpf;

	@Column(name = "senha")
	private String senha;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo")
	private TipoUsuario tipo;

	@Column(name = "situacao")
	private int situacao;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "unidade_id")
	private Unidade unidadeId;
	
	@Transient
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(long id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoUsuario getTipo() {
		return tipo;
	}

	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public Unidade getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Unidade unidadeId) {
		this.unidadeId = unidadeId;
	}


}
