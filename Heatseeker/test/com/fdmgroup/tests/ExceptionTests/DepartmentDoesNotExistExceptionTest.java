package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;

public class DepartmentDoesNotExistExceptionTest {

	@Test
	public void DepartmentDoesNotExistExceptionCreatesSuperMessageTest() 
	{
		Exception DDNE = new DepartmentDoesNotExistException("sample_Message");
		assertNotNull(DDNE);
	}
	
	@Test
	public void DepartmentDoesNotExistExceptionCreatesSuperThrowableTest() 
	{
		Exception DDNE = new DepartmentDoesNotExistException(new Throwable());
		assertNotNull(DDNE);
	}
	
	@Test
	public void DepartmentDoesNotExistExceptionCreatesSuperFullConstructorTest() 
	{
		Exception DDNE = new DepartmentDoesNotExistException("sample_Message",new Throwable());
		assertNotNull(DDNE);
	}

}
