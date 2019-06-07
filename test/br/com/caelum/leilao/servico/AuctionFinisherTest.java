package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.leilao.builder.TestDataBuilderAuction;
import br.com.caelum.leilao.dominio.Auction;
import br.com.caelum.leilao.infra.dao.AuctionDao;

public class AuctionFinisherTest {
	
	@Test
	public void finishAuctionOlderLastWeek() {
		Calendar oldDate = Calendar.getInstance();
		oldDate.set(1999, 1,20);
		
		Auction auction = new TestDataBuilderAuction().to("Tv plasma").onDate(oldDate).builder();
		Auction auction1 = new TestDataBuilderAuction().to("Geladeira").onDate(oldDate).builder();
		
		AuctionDao daoMock = Mockito.mock(AuctionDao.class);
		
		List<Auction> auctionMocks = Arrays.asList(auction, auction1);
		Mockito.when(daoMock.correntes()).thenReturn(auctionMocks);
		
		AuctionFinisher finisher = new AuctionFinisher(daoMock);
		finisher.finish();
		
		assertTrue(auction.isFinished());
		assertTrue(auction1.isFinished());
		
	}
	
	@Test
	public void wasAuctionUpdated() {
		
		Calendar oldDate = Calendar.getInstance();
		oldDate.set(1999, 1, 1);
		
		Auction auction = new TestDataBuilderAuction().to("Geladeira").onDate(oldDate).builder();

		List<Auction> auctionMocks = Arrays.asList(auction);
		
		AuctionDao daoMock = Mockito.mock(AuctionDao.class);
		Mockito.when(daoMock.correntes()).thenReturn(auctionMocks);
		
		AuctionFinisher finisher = new AuctionFinisher(daoMock);
		finisher.finish();//if we comment this, then update(auction) will note be called. So, jUnit get error
		
		Mockito.verify(daoMock).update(auction);
		
	}
	
}
