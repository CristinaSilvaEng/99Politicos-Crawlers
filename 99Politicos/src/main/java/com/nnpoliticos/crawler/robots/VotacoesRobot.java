package com.nnpoliticos.crawler.robots;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.nnpoliticos.crawler.model.MateriaModel;
import com.nnpoliticos.crawler.normalize.MateriaNormalizer;
import com.nnpoliticos.crawler.normalize.VotacaoNormalizer;
import com.nnpoliticos.repository.PoliticoRepository;

public class VotacoesRobot implements Robots {
	
	public void run() {
		HashMap<String, MateriaModel> materias = new HashMap<String, MateriaModel>();
		PoliticoRepository repository = PoliticoRepository.getInstance();
		
		// TODO: Completar URL com datas dinamicamente.
		String baseURL = "http://www25.senado.leg.br/web/atividade/votacoes-nominais/-/v/periodo/01/01/2011/a/31/12/2015";
		
		try {
			Document document = Jsoup.connect(baseURL).get();
			for (Element element : document.select("table.table")) {
				Elements row = element.select("tbody > tr");
				String materia = row.select("td").first().text();
				if (!materia.contains("(votação secreta)")) {
					// Warning! Shit code is coming.
					MateriaModel materiaModel = getMateria(materia);
					if (materiaModel != null) {
						materias.put(row.select("td").get(2).select("a").attr("href"), materiaModel);
						repository.addMateria(materiaModel);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		for (String materia : materias.keySet()) {
			VotacaoNormalizer votacaoNormalizer = new VotacaoNormalizer(materia);
			votacaoNormalizer.normalize(materias.get(materia).getId());
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
