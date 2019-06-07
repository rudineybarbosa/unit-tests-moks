package br.com.caelum.leilao.dominio;

public class Bid {

	private User usuario;
	private double valor;
	private int id;
	
	public Bid(User usuario, double valor) {
		this.usuario = usuario;
		this.valor = valor;
	}

	public User getUser() {
		return usuario;
	}

	public double getValue() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		if (Double.doubleToLongBits(valor) != Double
				.doubleToLongBits(other.valor))
			return false;
		return true;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
}
