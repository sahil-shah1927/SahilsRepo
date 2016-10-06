package com.fdmgroup.heatseeker.exceptions;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 */
public class DepartmentDoesNotExistException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3525154052827932352L;

	/**
	 * Default Constructor from Exception class
	 */
	public DepartmentDoesNotExistException()
	 { 
		 super(); 
	 }
	 
	 public DepartmentDoesNotExistException(String message) 
	 { 
		 super(message); 
	 }
	 
	 public DepartmentDoesNotExistException(Throwable cause)
	 { 
		 super(cause); 
	 }
	 
	 public DepartmentDoesNotExistException(String message, Throwable cause)
	 { 
		 super(message, cause); 
	 }
}
