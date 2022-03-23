package com.devops.dxc.devops.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.devops.dxc.devops.model.Dxc;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/msdxc")
public class RestData {
	
	private final static Logger LOGGER = Logger.getLogger("devops.subnivel.Control");

	@GetMapping(path = "/dxc", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Dxc getData(@RequestParam(name = "sueldo") String sueldo, @RequestParam(name = "ahorro") String ahorro){
		
		LOGGER.log(Level.INFO, "< Trabajo DevOps - DXC > <Consultado Diez por ciento>");
		Dxc response = new Dxc();
		try {
			response = new Dxc(Integer.parseInt(ahorro), Integer.parseInt(sueldo));	
		}
		catch(Exception e) {
			
		}
        
		return response;
	}
}