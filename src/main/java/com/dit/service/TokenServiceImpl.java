/**
 * 
 */
package com.dit.service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author anavulla
 *
 */
@Service
public class TokenServiceImpl implements TokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

	@Value("${base64.apikey}")
	String base64ApiKey;

	@Value("${token.issuer}")
	String tokenIssuer;

	@Value("${token.ttlMillis}")
	Long ttlMillis;

	@Override
	public String createToken(String subject) {
		// setting signature algorithm
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64ApiKey);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setSubject(subject)
				.setIssuer(tokenIssuer).signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {
			LOGGER.info("Token is being generated with expiration of " + ttlMillis + "ms");
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		LOGGER.debug("JWT token is " + builder.compact());

		return builder.compact();
	}

	@Override
	public boolean validToken(String jwt) {

		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64ApiKey))
					.parseClaimsJws(jwt).getBody();
			LOGGER.debug(claims.toString());
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}

	}

}
