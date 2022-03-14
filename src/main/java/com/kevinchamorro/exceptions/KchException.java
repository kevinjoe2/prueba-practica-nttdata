package com.kevinchamorro.exceptions;

import org.springframework.http.HttpStatus;

public class KchException extends RuntimeException {

	private String code;
	private HttpStatus status;
	
	public KchException(String code, HttpStatus status, String message) {
		super(message);
		this.code = code;
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	private static final long serialVersionUID = -2270638155376622160L;
	
}
