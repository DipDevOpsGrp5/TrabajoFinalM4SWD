package com.devops.dxc.devops.mi;

import java.io.Serializable;
import java.util.List;

@lombok.Data
public class UnidadFomento implements Serializable {
	private static final long serialVersionUID = 2788204302778304488L;
	private String version;
	private String autor;
	private String codigo;
	private String nombre;
	private String unidad_medida;
	private List<Serie> serie;

	@lombok.Data
	public static class Serie {
		private String fecha;
		private double valor;
	}
}