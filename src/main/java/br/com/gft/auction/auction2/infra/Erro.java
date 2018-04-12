package br.com.gft.auction.auction2.infra;

import java.util.ArrayList;
import java.util.List;

public class Erro {
	private String id_erro;
	private int contLinha;
	private String redireciona;
	private List<Integer> Lista = new ArrayList<Integer>();

	public String getId_erro() {
		return id_erro;
	}
	
	public void setId_erro(String id_erro) {
		this.id_erro = id_erro;
	}
	
	public int getContaLinha() {
		return contLinha;
	}
	
	public void setContaLinha(int contLinha) {
		this.contLinha = contLinha;
	}
	
	public String getRedireciona() {
		return redireciona;
	}
	
	public void setRedireciona(String redireciona) {
		this.redireciona = redireciona;
	}

	public List<Integer> getLista() {
		return Lista;
	}

	public void setLista(List<Integer> lista) {
		Lista = lista;
	}

	@Override
	public String toString() {
		return "Erro [id_erro=" + id_erro + ", Numero de Linha=" + contLinha + ", redireciona=" + redireciona + ", Lista=" + Lista.toString() + "]";
	}
}
