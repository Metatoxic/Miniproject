package com.miniproject.exception;

public class CustomException extends Exception {

	private String errorMessage;

	public CustomException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	@Override
	public String getMessage() {

		return this.errorMessage;
	}

}
