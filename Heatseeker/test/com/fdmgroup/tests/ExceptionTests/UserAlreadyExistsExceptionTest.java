package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;

public class UserAlreadyExistsExceptionTest 
{
	@Test
	public void UserAlreadyExistsExceptionTestCreatesSuperMessageTest() 
	{
		Exception UDNE = new UserAlreadyExistsException("sample_Message");
		assertNotNull(UDNE);
	}
	
	@Test
	public void UserAlreadyExistsExceptionTestCreatesSuperThrowableTest() 
	{
		Exception UDNE = new UserAlreadyExistsException(new Throwable());
		assertNotNull(UDNE);
	}
	
	@Test
	public void UserAlreadyExistsExceptionTestCreatesSuperFullConstructorTest() 
	{
		Exception UDNE = new UserAlreadyExistsException("sample_Message",new Throwable());
		assertNotNull(UDNE);
	}
}
