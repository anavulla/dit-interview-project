/**
 * 
 */
package com.dit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import com.dit.model.CreateResponse;
import com.dit.model.User;
import com.dit.repository.UserRepository;

/**
 * @author anavulla
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class CreateControllerTest {

	@InjectMocks
	CreateController createController;

	@Mock
	UserRepository userRepository;

	@Test
	void testCreate() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		User user = new User();
		user.setFirstname("John");
		user.setLastname("Joe");
		user.setUsername("jjoe123");
		user.setPassword("test@123");
		user.setEmail("john@joe.com");

		User createdUser = user;
		createdUser.setUser_id(1L);

		when(userRepository.save(any(User.class))).thenReturn(createdUser);

		ResponseEntity<CreateResponse> responseEntity = createController.create(user);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody().getSucces());
		assertThat(responseEntity.getBody().getData().getId()).isEqualTo(1L);
		assertThat(responseEntity.getBody().getError()).isNull();

	}

}
