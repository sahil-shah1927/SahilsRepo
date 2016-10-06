package com.fdmgroup.heatseeker.DAOs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.model.User;

/**
 * 
 * @author sahil.shah
 * @version 1.2
 * 
 * Delete was updated. Previously tried to delete and unmanaged entity.
 * Fixed.
 * 
 */
public class UserDAO extends DAO<User>
{
	/**
	 * Default Constructor pulled from super class.
	 * @param emf an Entity Manager Factory
	 */
	public UserDAO(EntityManagerFactory emf) 
	{
		super(emf);
	}

	/**
	 * Persist new User Object to the Database
	 * @param newUser the User to be inserted
	 */
	@Override
	public void create(User newUser) throws UserAlreadyExistsException 
	{
		
		try
		{
			read(newUser);
			throw new UserAlreadyExistsException();
		}
		catch(UserDoesNotExistException e)
		{
			EntityManager myEM = myFactory.createEntityManager();
			myEM.getTransaction().begin();
			myEM.persist(newUser);
			myEM.getTransaction().commit();
			myEM.close();

		}
		
		
	}

	/**
	 * Read the specified User from the database by their username.
	 * @param userToBeRead the User to be read.
	 * @throws UserDoesNotExistException 
	 */
	@Override
	public User read(User userToBeRead) throws UserDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		String username = userToBeRead.getUsername();


		TypedQuery<User> userQuery = myEM.createQuery("SELECT d FROM User d WHERE upper(d.username) = upper(:username)", User.class);
		
		userQuery.setParameter("username", username);
		
		try
		{
			return userQuery.getSingleResult();
		}
		catch(NoResultException e)
		{
			throw new UserDoesNotExistException();
		}
		finally
		{
			myEM.close();
		}
		
	}

	/**
	 * Read all User objects from database
	 * @return a List of User Objects populated from the database.
	 */
	@Override
	public List<User> readAll() 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		List<User> retList = myEM.createQuery("FROM User",User.class).getResultList();
		myEM.close();
		return retList;
	}

	/**
	 * Update the User with specified ID to the updated issue.
	 * @param userToBeUpdated the updated User object
	 */
	@Override
	public void update(User userToBeUpdated) 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		myEM.getTransaction().begin();
		myEM.merge(userToBeUpdated);
		myEM.getTransaction().commit();
		myEM.close();
	}

	/**
	 * Delete the specified User from the database.
	 * @param userToBeDeleted the User to be deleted
	 * @throws UserDoesNotExistException 
	 */
	@Override
	public void delete(User userToBeDeleted) throws UserDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		String username = userToBeDeleted.getUsername();

		TypedQuery<User> userQuery = myEM.createQuery("SELECT d FROM User d WHERE upper(d.username) = upper(:username)", User.class);
		
		userQuery.setParameter("username", username);
		myEM.getTransaction().begin();

		try
		{
			myEM.remove(userQuery.getSingleResult());
			
		}
		catch(NoResultException e)
		{
			throw new UserDoesNotExistException();
		}
		
		myEM.getTransaction().commit();
		myEM.close();
	}

}
