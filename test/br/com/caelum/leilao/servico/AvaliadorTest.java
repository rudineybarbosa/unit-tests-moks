package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.TestDataBuilderAuction;
import br.com.caelum.leilao.dominio.Bid;
import br.com.caelum.leilao.dominio.Auction;
import br.com.caelum.leilao.dominio.User;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private User maria;
	private User jose;
	private User joao;

	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new User("Jo�o");
		this.jose = new User("Jos�");
		this.maria = new User("Maria");
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Auction leilao = new TestDataBuilderAuction().to("Playstation 3 Novo").builder();
		
		leiloeiro.avalia(leilao);
		
	}
	
    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        // parte 1: cenario
         
        Auction leilao = new Auction("Playstation 3 Novo");
         
        leilao.propose(new Bid(joao, 250.0));
        leilao.propose(new Bid(jose, 300.0));
        leilao.propose(new Bid(maria, 400.0));
         
        // parte 2: acao
        leiloeiro.avalia(leilao);
         
        // parte 3: validacao
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
    }
 
    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
    	User joao = new User("Jo�o");
        Auction leilao = new Auction("Playstation 3 Novo");
         
        leilao.propose(new Bid(joao, 1000.0));
         
        leiloeiro.avalia(leilao);
         
        assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
    }
     
    @Test
    public void deveEncontrarOsTresMaioresLances() {
        
        Auction leilao = new TestDataBuilderAuction().to("Playstation 3 Novo")
        		.lance(joao, 100.0)
        		.lance(maria, 200.0)
        		.lance(joao, 300.0)
        		.lance(maria, 400.0)
        		.builder();
         
        leiloeiro.avalia(leilao);
         
        List<Bid> maiores = leiloeiro.getTresMaiores();
        assertEquals(3, maiores.size());
        
        assertThat(maiores, hasItems(
        		new Bid(maria, 400),
        		new Bid(joao, 300),
        		new Bid(maria, 200)
        ));
        
    }
     
}
