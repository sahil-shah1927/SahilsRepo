package com.fdmgroup.tests.ExceptionTests;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fdmgroup.heatseeker.exceptions.PriorityIsAlreadySetException;

public class PriorityIsAlreadySetExceptionTest 
{
	@Test
	public void PriorityIsAlreadySetExceptionCreatesSuperMessageTest() 
	{
		Exception DDNE = new PriorityIsAlreadySetException("sample_Message");
		assertNotNull(DDNE);
	}
	
	@Test
	public void PriorityIsAlreadySetExceptionCreatesSuperThrowableTest() 
	{
		Exception DDNE = new PriorityIsAlreadySetException(new Throwable());
		assertNotNull(DDNE);
	}
	
	@Test
	public void PriorityIsAlreadySetExceptionCreatesSuperFullConstructorTest() 
	{
		Exception DDNE = new PriorityIsAlreadySetException("sample_Message",new Throwable());
		assertNotNull(DDNE);
	}
}
