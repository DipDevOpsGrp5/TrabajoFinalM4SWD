package com.devops.dxc.devops.cmf;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CMFService {

	@GET("uf")
	public Call<CMFResponse> getUfValue();
}
