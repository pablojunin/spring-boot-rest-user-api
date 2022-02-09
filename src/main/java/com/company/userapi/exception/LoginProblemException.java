package com.company.userapi.exception;

public class LoginProblemException extends RuntimeException {

	private static final long serialVersionUID = -9126932046903222114L;

	public LoginProblemException(String email) {
        super(String.format("Login problem", email));
    }
}