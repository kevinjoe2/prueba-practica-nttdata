package com.kevinchamorro.models.wrappers;

import java.io.Serializable;

public class ResponseWrap implements Serializable {
	
	public int statusCode;
	public String message;
	
	public ResponseWrap() {
		super();
	}

	public ResponseWrap(int statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}

	private static final long serialVersionUID = -4073793815205402559L;

}
