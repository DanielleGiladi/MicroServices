package com.example.demo;

public class CustomerNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5998148777628000308L;

	public CustomerNotFoundException() {
	}

	public CustomerNotFoundException(String message) {
		super(message);
	}

	public CustomerNotFoundException(Throwable cause) {
		super(cause);
	}

	public CustomerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
