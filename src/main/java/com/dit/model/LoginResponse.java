package com.dit.model;

public class LoginResponse {

	@Override
	public String toString() {
		return "LoginResponse [succes=" + succes + ", data=" + data + ", error=" + error + "]";
	}

	private Boolean succes;
	private String data;
	private String error;

	public Boolean getSucces() {
		return succes;
	}

	public void setSucces(Boolean succes) {
		this.succes = succes;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
