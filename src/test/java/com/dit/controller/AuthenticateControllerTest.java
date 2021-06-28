/**
 * 
 */
package com.dit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.dit.model.AuthenticateResponse;
import com.dit.service.TokenService;

/**
 * @author anavulla
 *
 */
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class AuthenticateControllerTest {

	@InjectMocks
	AuthenticateController authenticateController;

	@Mock
	TokenService tokenService;

	@Test
	void testSuccessAuthenticate() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer goodToken");

		when(tokenService.validToken("goodToken")).thenReturn(true);

		ResponseEntity<AuthenticateResponse> responseEntity = authenticateController.authenticate(headers);

		System.out.println(responseEntity);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		assertTrue(responseEntity.getBody().getSucces());
		assertTrue(responseEntity.getBody().getData().getAuthorized());

	}

	@Test
	void testForbiddenAuthenticate() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Bearer someRandomToken");

		ResponseEntity<AuthenticateResponse> responseEntity = authenticateController.authenticate(headers);

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(403);
		assertFalse(responseEntity.getBody().getSucces());
		assertFalse(responseEntity.getBody().getData().getAuthorized());

	}

}
