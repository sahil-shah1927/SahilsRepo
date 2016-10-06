package com.fdmgroup.heatseeker.exceptions;

/**
 * Exception Thrown When Priority Being Set Is Same As Specified.
 * Unchecked Exception.
 * @author Sahil Shah
 *
 */
public class PriorityIsAlreadySetException extends RuntimeException 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5326914336985001390L;
	
	/**
	 * Default Constructor from Exception class
	 */
	public PriorityIsAlreadySetException()
	 { 
		 super(); 
	 }
	 
	 public PriorityIsAlreadySetException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public PriorityIsAlreadySetException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public PriorityIsAlreadySetException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }

}
