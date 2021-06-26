package com.dit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dit.model.LoginResponse;
import com.dit.model.User;
import com.dit.repository.UserRepository;

/**
 * Login Controller
 * 
 * @author anavulla
 *
 */
@RestController
public class LoginController {
	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	/**
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LoginResponse> login(@RequestBody User user) {

		LoginResponse loginResponse = new LoginResponse();
		try {
			LOGGER.debug("Invoking Login Controller");
			User foundUser = userRepository.findByusername(user.getUsername());
			LOGGER.debug("found user: " + foundUser.toString());

			if (foundUser.getUsername() != null) {
				LOGGER.info("User found, validating password");
				if (foundUser.getPassword().equals(user.getPassword())) {
					loginResponse.setSucces(true);

					return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
				} else {
					loginResponse.setSucces(false);
					loginResponse.setError("Login Failed");
					return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
				}
			}

		} catch (Exception e) {
			loginResponse.setSucces(false);

			// messages can be customized based on error, setting "Login Failed"
			loginResponse.setError("Login Failed");

			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);

		}
		// generic response
		return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

	}

}
