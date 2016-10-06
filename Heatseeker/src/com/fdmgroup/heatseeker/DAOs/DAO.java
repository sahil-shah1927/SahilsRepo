package com.fdmgroup.heatseeker.DAOs;

import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 * @param <T>
 * 
 * Abstract DAO sets up structure for all data access objects. Holds
 * an Entity Manager Factory.
 */
public abstract class DAO<T> implements Storage<T> 
{
	protected EntityManagerFactory myFactory;

	/**
	 * Default Constructor pulls Application Context from beans.xml.
	 * Entity Manager Factory pulled from bean.
	 */
	public DAO(EntityManagerFactory emf)
	{
		setMyFactory(emf);
	}
	

	/**
	 * @return the myFactory
	 */
	public EntityManagerFactory getMyFactory() {
		return myFactory;
	}

	/**
	 * @param myFactory the myFactory to set
	 */
	public void setMyFactory(EntityManagerFactory myFactory) {
		this.myFactory = myFactory;
	}
}
