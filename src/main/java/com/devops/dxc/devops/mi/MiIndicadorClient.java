package com.devops.dxc.devops.mi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiIndicadorClient {

	private static MiIndicadorService miIndicadorService = null;
	private static final String BASE_URL = "https://mindicador.cl/api/";
	private static Logger LOGGER = LoggerFactory.getLogger(MiIndicadorClient.class);

	public static MiIndicadorService getClient() {
		OkHttpClient client = new OkHttpClient.Builder().build();
		if (miIndicadorService == null) {
			miIndicadorService = new Retrofit.Builder().baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create()).client(client).build()
					.create(MiIndicadorService.class);
			LOGGER.info("Singleton [MiIndicadorService] instance created");
		}
		return miIndicadorService;
	}
}
