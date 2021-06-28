package com.dit.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dit.model.HelloResponse;

/**
 * @author anavulla
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class HelloControllerTest {

	@InjectMocks
	HelloController helloController;

	@Test
	public void testHello() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ResponseEntity<HelloResponse> responseEntity = helloController.hello();

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertThat(responseEntity.getBody().getSucces());
		assertThat(responseEntity.getBody().getData().contains("Hello, World!"));
		assertThat(responseEntity.getBody().getError()).isNull();

	}

}
