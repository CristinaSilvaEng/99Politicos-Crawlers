package com.nnpoliticos.repository;

import java.util.HashMap;

import com.nnpoliticos.crawler.model.MateriaModel;
import com.nnpoliticos.crawler.model.PoliticoModel;
import com.nnpoliticos.crawler.model.VotacaoModel;
import com.nnpoliticos.crawler.service.DatabaseService;

public class PoliticoRepository {

	private static PoliticoRepository instance = null;
	private HashMap<String, PoliticoModel> politicos;
	private HashMap<String, MateriaModel> materias; // Isto não deveria estar aqui, apenas para teste.
	 
	protected PoliticoRepository() {
		politicos = new HashMap<String, PoliticoModel>();
		materias = new HashMap<String, MateriaModel>();
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
	
	public void addMateria(MateriaModel materia) {
		materias.put(materia.getId(), materia);
	}
	
	public void addVotacao(String nome, VotacaoModel votacao) {
		politicos.get(nome).getVotacoes().add(votacao);
	}

	public HashMap<String, PoliticoModel> getPoliticos() {
		return politicos;
	}
	
	public HashMap<String, MateriaModel> getMaterias() {
		return materias;
	}

	public void setMaterias(HashMap<String, MateriaModel> materias) {
		this.materias = materias;
	}

	public void saveDocuments() {
		DatabaseService db = new DatabaseService();
//		for (String politico : politicos.keySet()) {
//			db.addSenador(politicos.get(politico));
//		}
		for (String materia : materias.keySet()) {
			db.addMateria(materias.get(materia));
		}
		db.closeClient();
	}
}
