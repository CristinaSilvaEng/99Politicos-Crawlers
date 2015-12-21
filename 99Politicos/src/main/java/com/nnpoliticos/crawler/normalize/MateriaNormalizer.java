package com.nnpoliticos.crawler.normalize;

import org.jsoup.nodes.Document;

import com.nnpoliticos.crawler.model.MateriaModel;

public class MateriaNormalizer {
	
	Document document;

	public MateriaNormalizer(Document document) {
		this.document = document;
	}
	
	public MateriaModel normalize() {
		MateriaModel materia = new MateriaModel();
		
		materia.setId(document.select("div.cover-footer").text().toLowerCase().replace(" - ", "-").replace(" / ", "-").replace(" ", "-").trim());
		materia.setTipo(materia.getId().split("-")[0].toUpperCase());
		materia.setNumero(materia.getId().split("-")[1]);
		materia.setAno(materia.getId().split("-")[2]);
		materia.setCategoria(document.select("ul.bill-meta > li > a").text().trim());
		materia.setTitulo(document.select("div.bill-content > h2").text());
		materia.setDescricao(document.select("div.bill-content > p").toString());
		
		return materia;
	}

}
