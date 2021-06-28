/**
 * 
 */
package com.dit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dit.model.LoginResponse;
import com.dit.model.User;
import com.dit.repository.UserRepository;
import com.dit.service.TokenService;

/**
 * @author anavulla
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class LoginControllerTest {

	@InjectMocks
	LoginController loginController;

	@Mock
	UserRepository userRepository;

	@Mock
	TokenService tokenService;

	@Test
	void testSuccessLogin() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		User user = new User();
		user.setUsername("jjoe123");
		user.setPassword("test@123");

		User foundUser = user;

		User updatedUser = user;
		updatedUser.setLast_login_time(Timestamp.from(Instant.now()));

		String jwt = "someRandomToken";

		when(userRepository.findByusername(user.getUsername())).thenReturn(foundUser);
		when(userRepository.save(any(User.class))).thenReturn(updatedUser);
		when(tokenService.createToken(foundUser.getUsername())).thenReturn(jwt);

		ResponseEntity<LoginResponse> responseEntity = loginController.login(user);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody().getSucces());
		assertThat(responseEntity.getBody().getError()).isNull();
		assertThat(responseEntity.getHeaders().containsKey("Set-Cookie"));

	}

	@Test
	void testFailedLogin() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		User user = new User();
		user.setUsername("jjoe123");
		user.setPassword("test@123");

		User foundUser = new User();

		User updatedUser = user;
		updatedUser.setLast_login_time(Timestamp.from(Instant.now()));

		when(userRepository.findByusername(user.getUsername())).thenReturn(foundUser);

		ResponseEntity<LoginResponse> responseEntity = loginController.login(user);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertFalse(responseEntity.getBody().getSucces());
		assertThat(responseEntity.getBody().getError()).isEqualTo("Login Failed");
		assertThat(responseEntity.getHeaders()).isEmpty();

	}

}
