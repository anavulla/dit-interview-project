package com.dit.repository;

import com.dit.model.User;

/**
 * @author anavulla
 *
 * @param <T>
 * @param <S>
 */
public interface UserAuthRepository<T,S> {

	public User findByusername(String username);

}
