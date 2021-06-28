package com.dit.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.dit.model.AuthenticateResponse;
import com.dit.model.Data;
import com.dit.service.TokenService;

/**
 * User Authentication Controller
 * 
 * @author anavulla
 *
 */
@RestController
public class AuthenticateController {

	@Autowired
	private TokenService tokenService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateController.class);

	AuthenticateResponse authenticateResponse = new AuthenticateResponse();
	Data data = new Data();

	/**
	 * @param headers
	 * @return
	 */
	@GetMapping(path = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticateResponse> authenticate(@RequestHeader Map<String, String> headers) {

		try {
			LOGGER.debug("Invoking Authentication Controller");
			LOGGER.debug("All headers: {}", headers);

			/**
			 * JWT token is expected to be sent in cookie or header; Authorization: Bearer
			 * header is the preferable way.
			 * 
			 * 
			 * --header 'Authorization: Bearer OR --header 'Cookie: default
			 **/

			String authorizationHeader = null;

			for (Map.Entry<String, String> entry : headers.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("authorization")) {
					authorizationHeader = entry.getValue();
					break;

				}
			}

			LOGGER.debug(authorizationHeader);

			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				LOGGER.error("JWT is missing in header");
				setAuthorizationFailure();
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(authenticateResponse);
			} else {
				LOGGER.info("validating the token");
				String jwt = authorizationHeader.substring(7, authorizationHeader.length());
				if (tokenService.validToken(jwt)) {
					setAuthorizationSuccess();
					return ResponseEntity.status(HttpStatus.OK).body(authenticateResponse);
				}

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			setAuthorizationFailure();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(authenticateResponse);
		}

		// all other cases return 403
		setAuthorizationFailure();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(authenticateResponse);

	}

	void setAuthorizationSuccess() {
		authenticateResponse.setSucces(true);
		data.setId(null);
		data.setAuthorized(true);
		authenticateResponse.setData(data);
	}

	void setAuthorizationFailure() {
		authenticateResponse.setSucces(false);
		data.setId(null);
		data.setAuthorized(false);
		authenticateResponse.setData(data);
	}
}
