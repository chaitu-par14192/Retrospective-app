package com.example.demo.exceptions;

@SuppressWarnings("serial")
public class RetroNotFoundException extends RuntimeException{
	private String message;
	
	public RetroNotFoundException() {}

	public RetroNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}
