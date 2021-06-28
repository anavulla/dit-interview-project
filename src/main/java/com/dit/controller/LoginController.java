package com.dit.controller;

import java.sql.Timestamp;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dit.model.LoginResponse;
import com.dit.model.User;
import com.dit.repository.UserRepository;
import com.dit.service.TokenService;

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

	@Autowired
	private TokenService tokenService;

	@Value("${cookie.security}")
	boolean cookieSecurity;

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

					String JWT_TOKEN = tokenService.createToken(foundUser.getUsername());
					HttpHeaders headers = new HttpHeaders();
					if (cookieSecurity) {
						headers.add("Set-Cookie",
								"default=" + JWT_TOKEN + "; Max-Age=604800; Secure; Path=/; HttpOnly");
					} else {
						headers.add("Set-Cookie", "default=" + JWT_TOKEN + "; Max-Age=604800; Path=/; HttpOnly");
					}

					// update the last login time for the user
					foundUser.setLast_login_time(Timestamp.from(Instant.now()));
					User updatedUser = userRepository.save(foundUser);
					LOGGER.debug("updated user:" + updatedUser.toString());

					return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginResponse);
				} else {
					loginResponse.setSucces(false);
					loginResponse.setError("Login Failed");
					return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
				}
			} else {
				loginResponse.setSucces(false);
				loginResponse.setError("Login Failed");
				return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
			}

		} catch (Exception e) {
			loginResponse.setSucces(false);

			// messages can be customized based on error, setting "Login Failed"
			loginResponse.setError("Login Failed");

			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginResponse);

		}

	}

}
