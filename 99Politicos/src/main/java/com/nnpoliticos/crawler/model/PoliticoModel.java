package com.nnpoliticos.crawler.model;

import java.util.ArrayList;

public class PoliticoModel {

	private String id;
	private String nome;
	private String partido;
	private String estado;
	private ArrayList<VotacaoModel> votacoes;
	
	public PoliticoModel() {
		votacoes = new ArrayList<VotacaoModel>();
	}
	
	@Override
	public String toString() {
		return "[" + id + "]\t" + nome + " (" + partido + "/" + estado + ")";
	}
	
	public ArrayList<VotacaoModel> getVotacoes() {
		return votacoes;
	}

	public void setVotacoes(ArrayList<VotacaoModel> votacoes) {
		this.votacoes = votacoes;
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
