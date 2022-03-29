package com.devops.dxc.devops.model;

import java.io.Serializable;

@lombok.Data
public class Dxc implements Serializable {

	private static final long serialVersionUID = -2988002029080131424L;

	private Integer dxc;
	private Integer saldo;
	private Integer impuesto;
	private Integer sueldo;
	private Integer ahorro;
	private Double uf;

	public Dxc(int ahorro, int sueldo, double uf) {
		this.ahorro = ahorro;
		this.sueldo = sueldo;
		this.uf = uf;
	}

	public int getDxc() {
		return Util.getDxc(getAhorro(), getSueldo());
	}

	public int getSaldo() {
		return getAhorro() - getDxc();
	}

	public int getImpuesto() {
		return Util.calcularImpuesto(getSueldo(), getDxc());
	}
}
