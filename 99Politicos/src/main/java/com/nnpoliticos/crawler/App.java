package com.nnpoliticos.crawler;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.nnpoliticos.crawler.model.VotacaoModel;
import com.nnpoliticos.crawler.robots.PoliticoRobot;
//import com.nnpoliticos.crawler.robots.VotacoesRobot;
import com.nnpoliticos.repository.PoliticoRepository;

public class App 
{
    public static void main(String[] args)
    {
    	new PoliticoRobot().run();
//    	new VotacoesRobot().run();
    	
    	
		try {
			String url = "https://rl.senado.gov.br/reports/rwservlet?legis&report=/forms/parlam/vono_r01.RDF&paramform=no&p_cod_materia_i=122759&p_cod_materia_f=122759&p_cod_sessao_votacao_i=5512&p_cod_sessao_votacao_f=5512&p_order_by=nom_parlamentar";
			InputStream input = new URL(url).openStream();
			PdfReader reader = new PdfReader(input);
			PdfTextExtractor parser = new PdfTextExtractor(reader);
			
			ArrayList<String> senadores = new ArrayList<String>();
			ArrayList<String> votos = new ArrayList<String>();
			
			for (int i = 1; i <= reader.getNumberOfPages(); i++) {
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
			
			for (int i = 0; i < senadores.size(); i++) {
				System.out.println("Laço: " + i);
				VotacaoModel votacao = new VotacaoModel("pec-113-2015", votos.get(i));
				
				// TODO: Adicionar Senadores anteriores.
				if (PoliticoRepository.getInstance().getPoliticos().get(senadores.get(i)) != null) { // Se nulo deve ser Senador não reeleito
					PoliticoRepository.getInstance().addVotacao(senadores.get(i), votacao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
