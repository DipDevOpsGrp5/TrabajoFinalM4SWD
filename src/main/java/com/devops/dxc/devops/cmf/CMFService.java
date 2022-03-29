package com.devops.dxc.devops.cmf;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CMFService {

	@GET("uf?apikey=6819aeaebd8d92aa58b6247004953db21a37bd22&formato=json")
	public Call<CMFResponse> getUfValue();
}
