package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;

public class UserDoesNotExistExceptionTest {

	@Test
	public void UserDoesNotExistExceptionCreatesSuperMessageTest() 
	{
		Exception UDNE = new UserDoesNotExistException("sample_Message");
		assertNotNull(UDNE);
	}
	
	@Test
	public void IssueAlreadyExistsExceptionCreatesSuperThrowableTest() 
	{
		Exception UDNE = new UserDoesNotExistException(new Throwable());
		assertNotNull(UDNE);
	}
	
	@Test
	public void IssueAlreadyExistsExceptionCreatesSuperFullConstructorTest() 
	{
		Exception UDNE = new UserDoesNotExistException("sample_Message",new Throwable());
		assertNotNull(UDNE);
	}

}
