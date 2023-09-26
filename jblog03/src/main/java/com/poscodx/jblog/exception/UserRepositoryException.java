package com.poscodx.jblog.exception;

public class UserRepositoryException extends RuntimeException {
	public UserRepositoryException() {
		super("UserRepositoryException Thrown");
	}
	
	public UserRepositoryException(String message) {
		super(message);
	}

}
