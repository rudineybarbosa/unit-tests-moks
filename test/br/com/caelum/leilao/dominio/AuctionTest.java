package br.com.caelum.leilao.dominio;
import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AuctionTest {

	@Test
	public void mustReceiveOneBid() {
		Auction auction = new Auction("Macbook Pro 15");
		assertEquals(0, auction.getBids().size());
		
		auction.propose(new Bid(new User("Steve Jobs"), 2000));
		
		assertEquals(1, auction.getBids().size());
		assertEquals(2000, auction.getBids().get(0).getValue(), 0.00001);
	}
	
	@Test
	public void musReceiveALotOfBids() {
		Auction auction = new Auction("Macbook Pro 15");
		auction.propose(new Bid(new User("Steve Jobs"), 2000));
		auction.propose(new Bid(new User("Steve Wozniak"), 3000));
		
		assertEquals(2, auction.getBids().size());
		assertEquals(2000.0, auction.getBids().get(0).getValue(), 0.00001);
		assertEquals(3000.0, auction.getBids().get(1).getValue(), 0.00001);
	}
	
	@Test
	public void ignoreSecondBidInSequenceByUser() {
		User steveJobs = new User("Steve Jobs");
		Auction leilao = new Auction("Macbook Pro 15");
		leilao.propose(new Bid(steveJobs, 2000));
		leilao.propose(new Bid(steveJobs, 3000));
		
		assertEquals(1, leilao.getBids().size());
		assertEquals(2000.0, leilao.getBids().get(0).getValue(), 0.00001);
	}
	
	@Test
	public void ignoreMoreThanFiveBidsByUser() {
		User steveJobs = new User("Steve Jobs");
		User billGates = new User("Bill Gates");

		Auction auciotn = new Auction("Macbook Pro 15");
		auciotn.propose(new Bid(steveJobs, 2000));
		auciotn.propose(new Bid(billGates, 3000));
		auciotn.propose(new Bid(steveJobs, 4000));
		auciotn.propose(new Bid(billGates, 5000));
		auciotn.propose(new Bid(steveJobs, 6000));
		auciotn.propose(new Bid(billGates, 7000));
		auciotn.propose(new Bid(steveJobs, 8000));
		auciotn.propose(new Bid(billGates, 9000));
		auciotn.propose(new Bid(steveJobs, 10000));
		auciotn.propose(new Bid(billGates, 11000));
		auciotn.propose(new Bid(steveJobs, 12000));
		
		assertEquals(10, auciotn.getBids().size());
		assertEquals(11000.0, auciotn.getBids().get(auciotn.getBids().size()-1).getValue(), 0.00001);
	}	
}
