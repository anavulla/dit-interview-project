package com.dit.repository;

import org.springframework.data.repository.CrudRepository;

import com.dit.model.User;

/**
 * This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
 * along with additional extended custom repositories
 * 
 * @author anavulla
 *
 */
public interface UserRepository extends CrudRepository<User, Integer>, UserAuthRepository<User, String> {

}
