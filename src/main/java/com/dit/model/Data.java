package com.dit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author anavulla
 *
 */
public class Data {

	@Override
	public String toString() {
		return "Data [id=" + id + ", authorized=" + authorized + "]";
	}

	@JsonInclude(Include.NON_NULL)
	private Long id;

	@JsonInclude(Include.NON_NULL)
	private Boolean authorized;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}

}
