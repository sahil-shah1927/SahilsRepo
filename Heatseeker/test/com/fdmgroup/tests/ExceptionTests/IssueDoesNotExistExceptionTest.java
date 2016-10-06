package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;

public class IssueDoesNotExistExceptionTest {

	@Test
	public void IssueDoesNotExistExceptionCreatesSuperMessageTest() 
	{
		Exception IDNE = new IssueDoesNotExistException("sample_Message");
		assertNotNull(IDNE);
	}
	
	@Test
	public void IssueDoesNotExistExceptionCreatesSuperThrowableTest() 
	{
		Exception IDNE = new IssueDoesNotExistException(new Throwable());
		assertNotNull(IDNE);
	}
	
	@Test
	public void IssueDoesNotExistExceptionCreatesSuperFullConstructorTest() 
	{
		Exception IDNE = new IssueDoesNotExistException("sample_Message",new Throwable());
		assertNotNull(IDNE);
	}

}
