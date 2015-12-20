package com.nnpoliticos.crawler.normalize;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.nnpoliticos.crawler.model.PoliticoModel;

public class PoliticoNormalizer implements Normalizer {

	private String profile;
	private HashMap<String, String> estados;

	public PoliticoNormalizer(String profile) {
		this.profile = profile;
		
		estados = new HashMap<String, String>();
		estados.put("Acre", "AC");
		estados.put("Alagoas", "Alagoas");
		estados.put("Amapá", "AP");
		estados.put("Amazonas", "AM");
		estados.put("Bahia", "BA");
		estados.put("Ceará", "CE");
		estados.put("Distrito Federal", "DF");
		estados.put("Espírito Santo", "ES");
		estados.put("Goiás", "GO");
		estados.put("Maranhão", "MA");
		estados.put("Mato Grosso", "MT");
		estados.put("Mato Grosso do Sul", "MS");
		estados.put("Minas Gerais", "MG");
		estados.put("Pará", "PA");
		estados.put("Paraíba", "PB");
		estados.put("Paraná", "PR");
		estados.put("Pernambuco", "PE");
		estados.put("Piauí", "PI");
		estados.put("Rio de Janeiro", "RJ");
		estados.put("Rio Grande do Norte", "RN");
		estados.put("Rio Grande do Sul", "RS");
		estados.put("Rondônia", "RO");
		estados.put("Roraima", "RR");
		estados.put("Santa Catarina", "SC");
		estados.put("São Paulo", "SP");
		estados.put("Sergipe", "SE");
		estados.put("Tocantins", "TO");
	}
	
	public void run() {
		Document document = Jsoup.parse(profile);
		PoliticoModel politico = new PoliticoModel();
		politico.setId(document.select("h2.name > a").attr("href").split("/")[2]);
		politico.setNome(document.select("h2.name").text());
		politico.setPartido(document.select("p.entourage").text().split(" - ")[0]);
		politico.setEstado(estados.get(document.select("div.about > small.bio").text().split(" • ")[2]));
//		System.out.println(politico);
	}

}
