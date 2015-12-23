package com.nnpoliticos.repository;

import java.util.HashMap;

import com.nnpoliticos.crawler.model.PoliticoModel;
import com.nnpoliticos.crawler.model.VotacaoModel;

public class PoliticoRepository {

	private static PoliticoRepository instance = null;
	private HashMap<String, PoliticoModel> politicos;
	 
	protected PoliticoRepository() {
		politicos = new HashMap<String, PoliticoModel>();
	}
 
	// Lazy Initialization (If required then only)
	public static PoliticoRepository getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (PoliticoRepository.class) {
				if (instance == null) {
					instance = new PoliticoRepository();
				}
			}
		}
		return instance;
	}

	public void addPolitico(PoliticoModel politico) {
		getPoliticos().put(politico.getNome(), politico);
	}
	
	public void addVotacao(String nome, VotacaoModel votacao) {
		politicos.get(nome).getVotacoes().add(votacao);
	}

	public HashMap<String, PoliticoModel> getPoliticos() {
		return politicos;
	}
}
