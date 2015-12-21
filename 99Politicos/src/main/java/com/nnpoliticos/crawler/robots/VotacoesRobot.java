package com.nnpoliticos.crawler.robots;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nnpoliticos.crawler.model.MateriaModel;
import com.nnpoliticos.crawler.normalize.MateriaNormalizer;

public class VotacoesRobot implements Robots {

	public void run() {
		// TODO: Completar URL com datas dinamicamente.
		String baseURL = "http://www25.senado.leg.br/web/atividade/votacoes-nominais/-/v/periodo/01/01/2015/a/31/12/2015";
		
		try {
			Document document = Jsoup.connect(baseURL).get();
			for (Element element : document.select("table.table")) {
				String materia = element.select("tbody > tr > td").first().text();
				if (!materia.contains("(votação secreta)")) {
					// Warning! Shit code is coming.
					MateriaModel materiaModel = getMateria(materia);
					if (materiaModel != null) {
						System.out.println(materiaModel);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MateriaModel getMateria(String content) {
		String tipo = content.split("   ")[0].toLowerCase();
		String numero = String.valueOf(Integer.parseInt(content.split("   ")[1].split(" ")[0]));
		String ano =  content.split("   ")[1].split(" ")[2];
		String link = "http://www.votenaweb.com.br/projetos/" + tipo + "-" + numero + "-" + ano;
		try {
			return new MateriaNormalizer(Jsoup.connect(link).get()).normalize();
		} catch (IOException e) {
//			e.printStackTrace();
			return null;
		}
	}
}
