package com.learnspring.api.ws.exceptions;

public class UserServiceException extends RuntimeException{

	private static final long serialVersionUID = 1902120001415527695L;

	public UserServiceException(String message)
	{
		super(message);
	}

}
