package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;

public class IssueAlreadyExistsExceptionTest {

	@Test
	public void IssueAlreadyExistsExceptionCreatesSuperMessageTest() 
	{
		Exception IAE = new IssueAlreadyExistsException("sample_Message");
		assertNotNull(IAE);
	}
	
	@Test
	public void IssueAlreadyExistsExceptionCreatesSuperThrowableTest() 
	{
		Exception IAE = new IssueAlreadyExistsException(new Throwable());
		assertNotNull(IAE);
	}
	
	@Test
	public void IssueAlreadyExistsExceptionCreatesSuperFullConstructorTest() 
	{
		Exception IAE = new IssueAlreadyExistsException("sample_Message",new Throwable());
		assertNotNull(IAE);
	}

}
