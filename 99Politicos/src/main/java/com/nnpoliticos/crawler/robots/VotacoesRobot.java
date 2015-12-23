package com.nnpoliticos.crawler.robots;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nnpoliticos.crawler.model.MateriaModel;
import com.nnpoliticos.crawler.normalize.MateriaNormalizer;

public class VotacoesRobot implements Robots {
	
	public void run() {
		HashMap<String, MateriaModel> materias = new HashMap<String, MateriaModel>();
		
		// TODO: Completar URL com datas dinamicamente.
		String baseURL = "http://www25.senado.leg.br/web/atividade/votacoes-nominais/-/v/periodo/01/01/2011/a/31/12/2015";
		
		try {
			Document document = Jsoup.connect(baseURL).get();
			for (Element element : document.select("table.table")) {
				String materia = element.select("tbody > tr > td").first().text();
				if (!materia.contains("(votação secreta)")) {
					// Warning! Shit code is coming.
					MateriaModel materiaModel = getMateria(materia);
					if (materiaModel != null) {
						materias.put(materiaModel.getId(), materiaModel);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		int i = 0;
		for (String materia : materias.keySet()) {
			System.out.println("["+ ++i +"]\t" + materias.get(materia));
		}
	}

	private MateriaModel getMateria(String content) {
		try {
			String tipo = content.split("   ")[0].toLowerCase();
			String numero = String.valueOf(Integer.parseInt(content.split("   ")[1].split(" ")[0]));
			String ano =  content.split("   ")[1].split(" ")[2];
			String link = "http://www.votenaweb.com.br/projetos/" + tipo + "-" + numero + "-" + ano;
			return new MateriaNormalizer(Jsoup.connect(link).get()).normalize();
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
}
