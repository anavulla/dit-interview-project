package com.dit.model;

/**
 * @author anavulla
 *
 */
public class AuthenticateResponse {

	@Override
	public String toString() {
		return "AuthenticateResponse [succes=" + succes + ", data=" + data + ", error=" + error + "]";
	}

	private Boolean succes;
	private Data data;
	private String error;

	public Boolean getSucces() {
		return succes;
	}

	public void setSucces(Boolean succes) {
		this.succes = succes;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
