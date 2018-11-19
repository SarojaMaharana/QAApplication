package com.stackroute.auth.exceptions;

public class UserAlreadyExistsException extends Exception{

	/**
	 *  User Not exists exception
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public UserAlreadyExistsException(String message) {
		super(message);
		this.message = message;
    }
	

	public String getMessage(){
		return message;
	}

	public void setMessage(String mesage){
		this.message = message;
	}


}
