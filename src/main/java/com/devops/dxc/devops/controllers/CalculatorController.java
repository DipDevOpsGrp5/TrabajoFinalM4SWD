package com.devops.dxc.devops.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devops.dxc.devops.model.Dxc;
import com.devops.dxc.devops.model.Util;

@RestController
@RequestMapping(path = "/rest/msdxc")
public class CalculatorController {

	private final static Logger LOGGER = LoggerFactory.getLogger(CalculatorController.class);
	private static final int MIN_SALARY = 0;
	private static final int MAX_SALARY = 2147483647;
	private static final int MIN_AHORRO = 0;
	private static final int MAX_AHORRO = 2147483647;

	@SuppressWarnings("unused")
	@CrossOrigin
	@GetMapping(path = "/dxc", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getData(@RequestParam Integer sueldo, @RequestParam Integer ahorro) {
		ResponseEntity<?> salaryValidation = validateSalary(sueldo);
		if (salaryValidation != null)
			return salaryValidation;
		ResponseEntity<?> ahorroValidation = validateAhorro(ahorro);
		if (ahorroValidation != null)
			return ahorroValidation;
		Double ufValue = Util.getUf();
		if (ufValue == null)
			return ufServiceError();
		LOGGER.info("Calculating withdrawal : sueldo[{}] ahorro[{}]", sueldo, ahorro);
		Dxc response = new Dxc(ahorro, sueldo, ufValue);
		return ResponseEntity.ok(response);
	}

	private ResponseEntity<?> ufServiceError() {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", "Error getting UF value from UF service");
		return ResponseEntity.badRequest().body(errorResponse);
	}

	private ResponseEntity<?> validateAhorro(Integer ahorro) {
		Map<String, String> errorResponse = new HashMap<>();
		if (ahorro < MIN_AHORRO) {
			errorResponse.put("message", "ahorro deben ser igual o mayor a " + MIN_AHORRO);
			return ResponseEntity.badRequest().body(errorResponse);
		} else if (ahorro > MAX_AHORRO) {
			errorResponse.put("message", "ahorro deben ser igual o menor a " + MAX_AHORRO);
			return ResponseEntity.badRequest().body(errorResponse);
		} else {
			return null;
		}
	}

	private ResponseEntity<?> validateSalary(Integer sueldo) {
		Map<String, String> errorResponse = new HashMap<>();
		if (sueldo < MIN_SALARY) {
			errorResponse.put("message", "sueldo deben ser igual o mayor a " + MIN_SALARY);
			return ResponseEntity.badRequest().body(errorResponse);
		} else if (sueldo > MAX_SALARY) {
			errorResponse.put("message", "sueldo deben ser igual o menor a " + MAX_SALARY);
			return ResponseEntity.badRequest().body(errorResponse);
		} else {
			return null;
		}
	}
}