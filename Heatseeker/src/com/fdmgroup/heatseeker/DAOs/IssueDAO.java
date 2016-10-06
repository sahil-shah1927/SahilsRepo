package com.fdmgroup.heatseeker.DAOs;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.model.Issue;

/**
 * 
 * @author sahil.shah
 * @version 1.0
 */
public class IssueDAO extends DAO<Issue>{

	/**
	 * Default Constructor pulled from super class.
	 * @param emf an Entity Manager Factory
	 */
	public IssueDAO(EntityManagerFactory emf) 
	{
		super(emf);
	}
	
	/**
	 * Persist new Issue Object to the Database
	 * @param newIssue the issue to be inserted
	 * @throws IssueAlreadyExistsException 
	 */
	@Override
	public void create(Issue newIssue) throws IssueAlreadyExistsException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		Issue foundIssue = myEM.find(Issue.class, newIssue.getIssueId());

		if(foundIssue == null)
		{
			myEM.getTransaction().begin();
			myEM.persist(newIssue);
			myEM.getTransaction().commit();

			myEM.close();
		}
		else
		{
			throw new IssueAlreadyExistsException();
		}
		
	}

	/**
	 * Read the specified issue from the database.
	 * @param issueToBeRead the issue to be read.
	 * @throws IssueDoesNotExistException 
	 */
	@Override
	public Issue read(Issue issueToBeRead) throws IssueDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		Issue locatedIssue = myEM.find(Issue.class, issueToBeRead.getIssueId());
		
		if(locatedIssue == null)
		{
			myEM.close();
			throw new IssueDoesNotExistException();
		}
		myEM.close();
		return locatedIssue;
	}
	
	public Issue readById(int issueId) throws IssueDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		Issue locatedIssue = myEM.find(Issue.class, issueId);
		
		if(locatedIssue == null)
		{
			myEM.close();
			throw new IssueDoesNotExistException();
		}
		myEM.close();
		
		return locatedIssue;		
	}

	/**
	 * Read all Issue objects from database
	 * @return a List of Issue Objects populated from the database.
	 */
	@Override
	public List<Issue> readAll() 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		List<Issue> retList = myEM.createQuery("FROM Issue",Issue.class).getResultList();
		
		myEM.close();
		return retList;
	}

	/**
	 * Update the issue with specified ID to the updated issue.
	 * @param issueToBeUpdated the updated Issue object
	 */
	@Override
	public void update(Issue issueToBeUpdated) 
	{
		EntityManager myEM = myFactory.createEntityManager();
				
		myEM.getTransaction().begin();
		myEM.merge(issueToBeUpdated);
		myEM.getTransaction().commit();
		myEM.close();
		
	}

	/**
	 * Delete the specified issue from the database.
	 * @param issueToBeDeleted the issue to be deleted
	 * @throws IssueDoesNotExistException 
	 */
	@Override
	public void delete(Issue issueToBeDeleted) throws IssueDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		Issue test = myEM.find(Issue.class, issueToBeDeleted.getIssueId());

		//Attach Issue to entity manager to delete
		myEM.getTransaction().begin();
		myEM.remove(test);
		myEM.getTransaction().commit();
		myEM.close();
	}
	
	

}
