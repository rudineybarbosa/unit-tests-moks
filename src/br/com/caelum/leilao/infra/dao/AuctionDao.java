package br.com.caelum.leilao.infra.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.leilao.dominio.Bid;
import br.com.caelum.leilao.dominio.Auction;
import br.com.caelum.leilao.dominio.User;

public class AuctionDao {

	private Connection conexao;

	public AuctionDao() {
		try {
			this.conexao = DriverManager.getConnection(
					"jdbc:mysql://localhost/mocks", "root", "");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Calendar data(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	public void salva(Auction leilao) {
		try {
			String sql = "INSERT INTO LEILAO (DESCRICAO, DATA, ENCERRADO) VALUES (?,?,?);";
			PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, leilao.getDescricao());
			ps.setDate(2, new java.sql.Date(leilao.getData().getTimeInMillis()));
			ps.setBoolean(3, leilao.isFinished());
			
			ps.execute();
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            leilao.setId(generatedKeys.getInt(1));
	        }
			
			for(Bid lance : leilao.getBids()) {
				sql = "INSERT INTO LANCES (LEILAO_ID, USUARIO_ID, VALOR) VALUES (?,?,?);";
				PreparedStatement ps2 = conexao.prepareStatement(sql);
				ps2.setInt(1, leilao.getId());
				ps2.setInt(2, lance.getUser().getId());
				ps2.setDouble(3, lance.getValue());
				
				ps2.execute();
				ps2.close();
				
			}
			
			ps.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Auction> encerrados() {
		return porEncerrado(true);
	}
	
	public List<Auction> correntes() {
		return porEncerrado(false);
	}
	
	private List<Auction> porEncerrado(boolean status) {
		try {
			String sql = "SELECT * FROM LEILAO WHERE ENCERRADO = " + status + ";";
			
			PreparedStatement ps = conexao.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			List<Auction> leiloes = new ArrayList<Auction>();
			while(rs.next()) {
				Auction leilao = new Auction(rs.getString("descricao"), data(rs.getDate("data")));
				leilao.setId(rs.getInt("id"));
				if(rs.getBoolean("encerrado")) leilao.finish();
				
				String sql2 = "SELECT VALOR, NOME, U.ID AS USUARIO_ID, L.ID AS LANCE_ID FROM LANCES L INNER JOIN USUARIO U ON U.ID = L.USUARIO_ID WHERE LEILAO_ID = " + rs.getInt("id");
				PreparedStatement ps2 = conexao.prepareStatement(sql2);
				ResultSet rs2 = ps2.executeQuery();
				
				while(rs2.next()) {
					User usuario = new User(rs2.getInt("id"), rs2.getString("nome"));
					Bid lance = new Bid(usuario, rs2.getDouble("valor"));
					
					leilao.propose(lance);
				}
				rs2.close();
				ps2.close();
				
				leiloes.add(leilao);
				
			}
			rs.close();
			ps.close();
			
			return leiloes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(Auction leilao) {
		
		try {
			String sql = "UPDATE LEILAO SET DESCRICAO=?, DATA=?, ENCERRADO=? WHERE ID = ?;";
			PreparedStatement ps = conexao.prepareStatement(sql);
			ps.setString(1, leilao.getDescricao());
			ps.setDate(2, new java.sql.Date(leilao.getData().getTimeInMillis()));
			ps.setBoolean(3, leilao.isFinished());
			ps.setInt(4, leilao.getId());

			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int x() { return 10; }
}
