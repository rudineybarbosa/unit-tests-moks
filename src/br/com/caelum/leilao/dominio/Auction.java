package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Auction {

	private String descricao;
	private Calendar data;
	private List<Bid> lances;
	private boolean finished;
	private int id;
	
	public Auction(String descricao) {
		this(descricao, Calendar.getInstance());
	}
	
	public Auction(String descricao, Calendar data) {
		this.descricao = descricao;
		this.data = data;
		this.lances = new ArrayList<Bid>();
	}
	
	public void propose(Bid lance) {
		if(lances.isEmpty() || canMakeBids(lance.getUser())) {
			lances.add(lance);
		}
	}

	private boolean canMakeBids(User user) {
		return !lastBid().getUser().equals(user) && amountBidsByUser(user) <5;
	}

	private int amountBidsByUser(User usuario) {
		int total = 0;
		for(Bid l : lances) {
			if(l.getUser().equals(usuario)) total++;
		}
		return total;
	}

	private Bid lastBid() {
		return lances.get(lances.size()-1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Bid> getBids() {
		return Collections.unmodifiableList(lances);
	}

	public Calendar getData() {
		return (Calendar) data.clone();
	}

	public void finish() {
		this.finished = true;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
