package com.dit.model;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author anavulla
 *
 */
@Entity
public class User {
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", firstname=" + firstname + ", lastname=" + lastname + ", username="
				+ username + ", password=" + password + ", email=" + email + ", last_login_time=" + last_login_time
				+ "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private Time last_login_time;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Time getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Time last_login_time) {
		this.last_login_time = last_login_time;
	}

}
