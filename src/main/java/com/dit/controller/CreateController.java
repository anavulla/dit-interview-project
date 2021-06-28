package com.dit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dit.model.CreateResponse;
import com.dit.model.Data;
import com.dit.model.User;
import com.dit.repository.UserRepository;

/**
 * User Creation Controller
 * 
 * @author anavulla
 *
 */
@RestController
public class CreateController {
	@Autowired
	private UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateController.class);

	/**
	 * @param user
	 * @return
	 */
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<CreateResponse> create(@RequestBody User user) {
		CreateResponse createResponse = new CreateResponse();
		Data data = new Data();

		try {
			LOGGER.debug("Invoking Create Controller");
			LOGGER.debug("creating user: " + user.toString());
			
			/**
			 * Password can be hashed and salted before storing in database
			 */

			User createdUser = userRepository.save(user);

			if (createdUser.getUser_id() != null) {
				createResponse.setSucces(true);
				data.setId(createdUser.getUser_id());
				data.setAuthorized(null);
				createResponse.setData(data);

				return ResponseEntity.status(HttpStatus.CREATED).body(createResponse);
			}

		} catch (Exception e) {

			createResponse.setSucces(false);

			// messages can be customized based on error, throwing as is for now
			createResponse.setError(e.getMessage());

			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createResponse);
		}
		// generic response
		return ResponseEntity.status(HttpStatus.OK).body(createResponse);

	}

}
