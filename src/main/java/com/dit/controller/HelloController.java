package com.dit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dit.model.HelloResponse;

/**
 * 
 * Hello Controller
 * 
 * @author anavulla
 *
 */
@RestController
public class HelloController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	/**
	 * @return
	 */
	@GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HelloResponse> hello() {
		LOGGER.debug("Invoking Hello Controller");

		HelloResponse helloResponse = new HelloResponse();
		try {
			helloResponse.setSucces(true);
			helloResponse.setData("Hello, World!");

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(helloResponse);

	}
}
