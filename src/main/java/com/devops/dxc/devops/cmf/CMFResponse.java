package com.devops.dxc.devops.cmf;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@lombok.Data
public class CMFResponse {
	@SerializedName("UFs")
	private List<Uf> uFs;

	@lombok.Data
	public class Uf {
		@SerializedName("Valor")
		private String valor;
		@SerializedName("Fecha")
		private String fecha;
	}

}