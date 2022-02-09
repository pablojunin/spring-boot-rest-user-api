package com.company.userapi.exception;

public class EmailAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -9126932046903222114L;

	public EmailAlreadyExistException(String email) {
        super(String.format("El correo ya registrado", email));
    }
}