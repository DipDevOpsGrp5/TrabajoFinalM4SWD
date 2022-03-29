package com.devops.dxc.devops.cmf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CMFClient {

	private static CMFService cmfService = null;
	private static final String BASE_URL = "https://api.cmfchile.cl/api-sbifv3/recursos_api/";
	private static Logger LOGGER = LoggerFactory.getLogger(CMFClient.class);

	public static CMFService getClient() {
		OkHttpClient client = new OkHttpClient.Builder().build();
		if (cmfService == null) {
			cmfService = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
					.client(client).build().create(CMFService.class);
			LOGGER.info("Singleton [CMFService] instance created");
		}
		return cmfService;
	}
}
