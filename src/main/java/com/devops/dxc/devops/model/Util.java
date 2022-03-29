package com.devops.dxc.devops.model;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devops.dxc.devops.cmf.CMFClient;
import com.devops.dxc.devops.cmf.CMFResponse;

import retrofit2.Response;

public class Util {

	static Double valorUf;
	public static final double MIN_UF = 35;
	public static final double MAX_UF = 150;
	private final static Logger LOGGER = LoggerFactory.getLogger(Util.class);

	/**
	 * Método para cacular el 10% del ahorro en la AFP. Las reglas de negocio se
	 * pueden conocer en
	 * https://www.previsionsocial.gob.cl/sps/preguntas-frecuentes-nuevo-retiro-seguro-10/
	 * 
	 * @param ahorro
	 * @param sueldo
	 * @return
	 */
	public static int getDxc(int ahorro, int sueldo) {
		double currentUFValue = getUf();
		double dxc = ahorro * 0.1;
		double totalRetiro;
		if ((dxc / currentUFValue) > MAX_UF) {
			totalRetiro = MAX_UF * currentUFValue;
		} else if (dxc <= (currentUFValue * MIN_UF) && ahorro >= (currentUFValue * MIN_UF)) {
			totalRetiro = currentUFValue * MIN_UF;
		} else if (ahorro <= (currentUFValue * MIN_UF)) {
			totalRetiro = ahorro;
		} else {
			totalRetiro = ahorro * 0.1;
		}
		return Math.round((float) totalRetiro);
	}

	/**
	 * Método que retorna el valor de la UF. Este método debe ser refactorizado por
	 * una integración a un servicio que retorne la UF en tiempo real. Por ejemplo
	 * mindicador.cl
	 * 
	 * @return
	 * @throws IOException
	 */
	public static double getUf() {
		try {
			Response<CMFResponse> response = CMFClient.getClient().getUfValue().execute();
			if (response.isSuccessful())
				valorUf = Double.parseDouble(
						response.body().getUFs().get(0).getValor().replaceAll("\\.", "").replaceAll(",", "."));
		} catch (Exception e) {
			LOGGER.error("Error getting UF value from UF service '{}'", e.getMessage());
		}
		return valorUf;
	}

	enum TramoImpuestoRenta {
		TRAMO_EXENTO(0, 752004, 0),
		TRAMO_1(752004, 1671120, 0.04),
		TRAMO_2(1671120, 2785200, 0.08),
		TRAMO_3(2785200, 3899280, 0.135),
		TRAMO_4(3899280, 5013360, 0.23),
		TRAMO_5(5013360, 6684480, 0.304),
		TRAMO_6(6684480, 17268240, 0.35),
		TRAMO_7(17268240, 2147483647, 0.4);

		int desde;
		int hasta;
		double factor;

		private TramoImpuestoRenta(int desde, int hasta, double factor) {
			this.desde = desde;
			this.hasta = hasta;
			this.factor = factor;
		}

		public static TramoImpuestoRenta findSectionBySalary(int sueldo) {
			return Arrays.asList(TramoImpuestoRenta.values()).stream().filter(t -> t.desde < sueldo && sueldo < t.hasta)
					.findFirst().orElse(TRAMO_6);
		}
	}

	public static int calcularImpuesto(int sueldoImponible, int dxc) {
		if (sueldoImponible < 0)
			throw new RuntimeException("Sueldo debe ser mayor a cero '0'.");
		else if (sueldoImponible < 1500000)
			return 0;
		else
			return (int) Math.round(dxc * TramoImpuestoRenta.findSectionBySalary(sueldoImponible).factor);
	}
}
