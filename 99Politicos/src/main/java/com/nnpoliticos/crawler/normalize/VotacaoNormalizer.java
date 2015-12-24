package com.nnpoliticos.crawler.normalize;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.nnpoliticos.crawler.model.VotacaoModel;
import com.nnpoliticos.repository.PoliticoRepository;

public class VotacaoNormalizer {

	private String url;
	
	public VotacaoNormalizer(String url) {
		this.url = url;
	}
	
	public void normalize(String idMateria) {
	  	
		try {
			InputStream input = new URL(url.replace("http", "https")).openStream();
			PdfReader reader = new PdfReader(input);
			PdfTextExtractor parser = new PdfTextExtractor(reader);
			
			ArrayList<String> senadores = new ArrayList<String>();
			ArrayList<String> votos = new ArrayList<String>();
			
			input.close();
			int pages = reader.getNumberOfPages();
			reader.close();
			
			for (int i = 1; i <= pages; i++) {
				String page = parser.getTextFromPage(i);
				try {
					String votantes = page.substring(page.indexOf("Ementa:") + 8, page.indexOf("SENADOR"));
					for(String votante : votantes.split("\n")) {
						if (votante  != "") {
							senadores.add(votante.trim());
						}
					}
					String resultados = page.substring(page.indexOf("PARTIDO") + 8, page.indexOf("VOTO"));
					for(String resultado : resultados.split("\n")) {
						if (resultado  != "") {
							votos.add(resultado.trim());
						}
					}
				} catch(Exception e) {}
			}
			
			for (int i = 0; i < senadores.size() - 1; i++) {
				
				VotacaoModel votacao = new VotacaoModel(idMateria, votos.get(i));
				
				// TODO: Adicionar Senadores anteriores.
				if (PoliticoRepository.getInstance().getPoliticos().get(senadores.get(i)) != null) { // Se nulo deve sem mandato atual
					PoliticoRepository.getInstance().addVotacao(senadores.get(i), votacao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
