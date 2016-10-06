package com.fdmgroup.heatseeker.exceptions;

public class UserAlreadyExistsException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7000603587300615644L;

	/**
	 * Default Constructor from Exception class
	 */
	public UserAlreadyExistsException()
	 { 
		 super(); 
	 }
	 
	 public UserAlreadyExistsException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public UserAlreadyExistsException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public UserAlreadyExistsException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }
}
