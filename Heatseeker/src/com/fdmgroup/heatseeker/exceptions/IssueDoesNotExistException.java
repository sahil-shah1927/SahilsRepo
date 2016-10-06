package com.fdmgroup.heatseeker.exceptions;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 */
public class IssueDoesNotExistException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3593717564949402998L;

	/**
	 * Default Constructor from Exception class
	 */
	public IssueDoesNotExistException()
	 { 
		 super(); 
	 }
	 
	 public IssueDoesNotExistException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public IssueDoesNotExistException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public IssueDoesNotExistException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }
}
