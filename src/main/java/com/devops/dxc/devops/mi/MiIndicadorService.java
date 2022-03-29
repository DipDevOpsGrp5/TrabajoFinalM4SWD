package com.devops.dxc.devops.mi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MiIndicadorService {

	@GET("uf/{fecha}")
	Call<UnidadFomento> getUfValue(@Path("fecha") String fecha);
}
