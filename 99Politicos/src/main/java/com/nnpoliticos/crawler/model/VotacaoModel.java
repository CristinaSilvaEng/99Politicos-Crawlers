package com.nnpoliticos.crawler.model;

public class VotacaoModel {
	
	public VotacaoModel(String id, String voto) {
		this.id = id;
		this.voto = voto;
	}
	
	private String id;
	private String voto;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVoto() {
		return voto;
	}
	public void setVoto(String voto) {
		this.voto = voto;
	}
	
	@Override
	public String toString() {
		return id + " : " + voto;
	}
}
