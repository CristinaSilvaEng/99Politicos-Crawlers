package com.nnpoliticos.crawler.model;

public class PoliticoModel {

	private String id;
	private String nome;
	private String partido;
	private String estado;
	
	@Override
	public String toString() {
		return "[" + id + "] " + nome + " (" + partido + "/" + estado + ")";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPartido() {
		return partido;
	}
	public void setPartido(String partido) {
		this.partido = partido;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}
