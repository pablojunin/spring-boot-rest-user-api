package com.company.userapi.exception;

public class EmailNotValidException extends RuntimeException {

	private static final long serialVersionUID = -9126932046903222114L;

	public EmailNotValidException(String email) {
        super(String.format("Email not valid", email));
    }
}