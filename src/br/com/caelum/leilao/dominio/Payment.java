package br.com.caelum.leilao.dominio;

import java.util.Calendar;

public class Payment {

	private double value;
	private Calendar data;

	public Payment(double value, Calendar data) {
		this.value = value;
		this.data = data;
	}
	public double getValue() {
		return value;
	}
	public Calendar getData() {
		return data;
	}
}
