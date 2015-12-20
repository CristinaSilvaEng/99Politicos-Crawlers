package com.nnpoliticos.crawler.robots;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.nnpoliticos.crawler.normalize.Normalizer;
import com.nnpoliticos.crawler.normalize.PoliticoNormalizer;

public class PoliticoRobot implements Robots {

	public void run() {
		String baseURL = "http://www.votenaweb.com.br/politicos?apenas=Senador&page=";
		
		for (int page = 1; page <= 8; page++) {
			try {
				Document document = Jsoup.connect(baseURL + page).get();
				for (Element element : document.select("div.politician")) {
					Normalizer normalizer = new PoliticoNormalizer(element.toString());
					normalizer.run();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
