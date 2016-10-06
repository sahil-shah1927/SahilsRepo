package com.fdmgroup.heatseeker.DAOs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * ApplicationContextProvider gives static access to the Application
 * Context which is created with beans.xml by default. 
 *
 *	@author Kishan Patel
 */
public class ApplicationContextProvider implements ApplicationContextAware
{

	private static ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	
	public ApplicationContextProvider()
	{

	}
	 
    public static ApplicationContext getApplicationContext() 
    {
        return context;
    }
	
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException 
	{
		 context = ctx;
	}

}
