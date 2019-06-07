package br.com.caelum.leilao.builder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Bid;
import br.com.caelum.leilao.dominio.Auction;
import br.com.caelum.leilao.dominio.User;

public class TestDataBuilderAuction {

	private String descricao;
	private Calendar data;
	private List<Bid> lances;
	private boolean encerrado;

	public TestDataBuilderAuction() {
		this.data = Calendar.getInstance();
		lances = new ArrayList<Bid>();
	}
	
	public TestDataBuilderAuction to(String descricao) {
		this.descricao = descricao;
		return this;
	}
	
	public TestDataBuilderAuction onDate(Calendar data) {
		this.data = data;
		return this;
	}

	public TestDataBuilderAuction lance(User usuario, double valor) {
		lances.add(new Bid(usuario, valor));
		return this;
	}

	public TestDataBuilderAuction encerrado() {
		this.encerrado = true;
		return this;
	}

	public Auction builder() {
		Auction auction = new Auction(descricao, data);
		for(Bid lanceDado : lances) auction.propose(lanceDado);
		if(encerrado) auction.finish();
				
		return auction;
	}

}
