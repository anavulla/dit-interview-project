/**
 * 
 */
package com.dit.service;

/**
 * @author anavulla
 *
 */
public interface TokenService {

	String createToken(String subject);
	boolean validToken(String jwt);
}
