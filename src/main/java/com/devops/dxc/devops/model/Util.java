package com.devops.dxc.devops.model;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
public class Util {

    /**
     * Método para cacular el 10% del ahorro en la AFP.  Las reglas de negocio se pueden conocer en 
     * https://www.previsionsocial.gob.cl/sps/preguntas-frecuentes-nuevo-retiro-seguro-10/
     * 
     * @param ahorro
     * @param sueldo
     * @return
     * @throws Exception 
     */
    public static int getDxc(int ahorro, int sueldo) throws Exception{

		if(((ahorro*0.1)/getUf()) > 150 ){
		    return (int) (150*getUf()) ;
		} else if((ahorro*0.1)<=1000000 && ahorro >=1000000){
		    return (int) 1000000;
		} else if( ahorro <=1000000){
		    return (int) ahorro;
		} else {
		    return (int) (ahorro*0.1);
		}

    }

    /**
     * Método que retorna el valor de la UF.  Este método debe ser refactorizado por una integración a un servicio
     * que retorne la UF en tiempo real.  Por ejemplo mindicador.cl
     * @return
     * @throws Exception 
     */
    public static double getUf() throws Exception{
    	final HttpClient http = new HttpClient();
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
    	LocalDateTime now = LocalDateTime.now();  
    	String url = "https://mindicador.cl/api/uf/" + dtf.format(now);
        String r;
		r = http.sendGet(url);
        if (r == null || r.isEmpty()) {
            throw new Exception("Error");
        }
        final JSONObject root = new JSONObject(r);
        if (!root.getString("codigo").equals("uf")) {
            throw new Exception("Error.");
        }
        JSONArray serieArray = root.getJSONArray("serie");
        double valor = serieArray.getJSONObject(0).getDouble("valor");
        return valor;
    }
    
}
