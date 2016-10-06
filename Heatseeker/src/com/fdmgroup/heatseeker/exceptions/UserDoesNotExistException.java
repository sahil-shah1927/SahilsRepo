package com.fdmgroup.heatseeker.exceptions;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 */
public class UserDoesNotExistException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4210246251884126307L;
	
	/**
	 * Default Constructor from Exception class
	 */
	public UserDoesNotExistException()
	 { 
		 super(); 
	 }
	 
	 public UserDoesNotExistException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public UserDoesNotExistException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public UserDoesNotExistException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }
}
