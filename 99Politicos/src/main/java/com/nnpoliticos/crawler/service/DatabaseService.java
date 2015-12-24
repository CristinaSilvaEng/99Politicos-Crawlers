package com.nnpoliticos.crawler.service;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.nnpoliticos.crawler.model.MateriaModel;
import com.nnpoliticos.crawler.model.PoliticoModel;
import com.nnpoliticos.crawler.model.VotacaoModel;

public class DatabaseService {

	private MongoClient client = null;
	private MongoDatabase db = null;
	
	public DatabaseService() {
		MongoClientURI uri = new MongoClientURI("mongodb://user:pswd@sub.mongolab.com:port/db");
		client = new MongoClient(uri);
		db = client.getDatabase("db");
	}
	
	public void addSenador(PoliticoModel politico) {
		
		Document document = new Document("id", politico.getId())
		.append("nome", politico.getNome())
		.append("partido", politico.getPartido());
		for (VotacaoModel votacao : politico.getVotacoes()) {
			document.append(votacao.getId(), votacao.getVoto());
		}
		
		db.getCollection("politicos").insertOne(document);
	}
	
	public void addMateria(MateriaModel materia) {
		Document document = new Document("id", materia.getId())
		.append("tipo", materia.getTipo())
		.append("numero", materia.getNumero())
		.append("ano", materia.getAno())
		.append("titulo", materia.getTitulo())
		.append("descricao", materia.getDescricao())
		.append("categoria", materia.getCategoria());
		
		db.getCollection("materias").insertOne(document);
	}
	
	public void closeClient() {
		client.close();
	}
}
