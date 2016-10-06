package com.fdmgroup.heatseeker.exceptions;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 */
public class IssueAlreadyExistsException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8266846887252186029L;

	/**
	 * Default Constructor from Exception class
	 */
	public IssueAlreadyExistsException()
	 { 
		 super(); 
	 }
	 
	 public IssueAlreadyExistsException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public IssueAlreadyExistsException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public IssueAlreadyExistsException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }
}
