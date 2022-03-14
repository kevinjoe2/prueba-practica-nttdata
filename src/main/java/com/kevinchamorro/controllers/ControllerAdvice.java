package com.kevinchamorro.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kevinchamorro.exceptions.KchException;
import com.kevinchamorro.models.wrappers.ErrorWrap;

@RestControllerAdvice
public class ControllerAdvice {
	
	@ExceptionHandler(value = KchException.class)
	public ResponseEntity<ErrorWrap> runtimeExceptionHandler( KchException ex ){
		ErrorWrap error = new ErrorWrap(ex.getCode(),ex.getMessage());
		return new ResponseEntity<>(error, ex.getStatus());
	}
	
}
