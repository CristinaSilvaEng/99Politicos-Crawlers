package com.nnpoliticos.crawler;

import com.nnpoliticos.crawler.robots.PoliticoRobot;
import com.nnpoliticos.crawler.robots.VotacoesRobot;
import com.nnpoliticos.repository.PoliticoRepository;

public class App 
{
    public static void main(String[] args)
    {
    	new PoliticoRobot().run();
    	new VotacoesRobot().run();
    	
    	PoliticoRepository.getInstance().saveDocuments();
    }
}
