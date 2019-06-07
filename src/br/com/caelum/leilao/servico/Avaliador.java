package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Bid;
import br.com.caelum.leilao.dominio.Auction;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Bid> maiores;

	public void avalia(Auction leilao) {
		
		if(leilao.getBids().size() == 0) {
			throw new RuntimeException("N�o � poss�vel avaliar um leil�o sem lances!");
		}
		
		for(Bid lance : leilao.getBids()) {
			if(lance.getValue() > maiorDeTodos) maiorDeTodos = lance.getValue();
			if (lance.getValue() < menorDeTodos) menorDeTodos = lance.getValue();
		}
		
		tresMaiores(leilao);
	}

	private void tresMaiores(Auction leilao) {
		maiores = new ArrayList<Bid>(leilao.getBids());
		Collections.sort(maiores, new Comparator<Bid>() {

			public int compare(Bid o1, Bid o2) {
				if(o1.getValue() < o2.getValue()) return 1;
				if(o1.getValue() > o2.getValue()) return -1;
				return 0;
			}
		});
		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}

	public List<Bid> getTresMaiores() {
		return maiores;
	}
	
	public double getMaiorLance() {
		return maiorDeTodos;
	}
	
	public double getMenorLance() {
		return menorDeTodos;
	}
}
