package br.com.caelum.leilao.infra.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.leilao.dominio.Auction;

public class LeilaoDaoFalso {

	private static List<Auction> leiloes = new ArrayList<Auction>();;
	
	public void salva(Auction leilao) {
		leiloes.add(leilao);
	}

	public List<Auction> encerrados() {
		
		List<Auction> filtrados = new ArrayList<Auction>();
		for(Auction leilao : leiloes) {
			if(leilao.isFinished())  filtrados.add(leilao);
		}

		return filtrados;
	}
	
	public List<Auction> correntes() {
		
		List<Auction> filtrados = new ArrayList<Auction>();
		for(Auction leilao : leiloes) {
			if(!leilao.isFinished()) filtrados.add(leilao);
		}

		return filtrados;
	}
	
	public void atualiza(Auction leilao) { /* faz nada! */ }
}
