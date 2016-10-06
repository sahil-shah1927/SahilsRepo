package com.fdmgroup.heatseeker.DAOs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.Issue;

/**
 * 
 * @author sahil.shah
 * @version 1.3
 * 
 * List of issues can be grabbed by DepartmentID
 */
public class DepartmentDAO extends DAO<Department>{

	/**
	 * Default Constructor pulled from super class.
	 * @param emf an Entity Manager Factory
	 */
	public DepartmentDAO(EntityManagerFactory emf) 
	{
		super(emf);
	}

	/**
	 * Persist new Department Object to the Database
	 * @param newDepartment the Department to be inserted
	 */
	@Override
	public void create(Department newDepartment) 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
			myEM.getTransaction().begin();
			myEM.persist(newDepartment);
			myEM.getTransaction().commit();
			myEM.close();
	}

	/**
	 * Read the specified Department from the database.
	 * @param departmentToBeRead the Department to be read.
	 * @throws DepartmentDoesNotExistException 
	 */
	@Override
	public Department read(Department departmentToBeRead) throws DepartmentDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		Department locatedDepartment = myEM.find(Department.class, departmentToBeRead.getDeptId());

		if(locatedDepartment == null)
		{
			myEM.close();
			throw new DepartmentDoesNotExistException();
		}
		myEM.close();
		return locatedDepartment;
		

	}
	
	public Department readById(int id) throws DepartmentDoesNotExistException 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		Department locatedDepartment = myEM.find(Department.class, id);

		if(locatedDepartment == null)
		{
			myEM.close();
			throw new DepartmentDoesNotExistException();
		}
		myEM.close();
		return locatedDepartment;
		

	}

	/**
	 * Read all Department objects from database
	 * @return a List of Department Objects populated from the database.
	 */
	@Override
	public List<Department> readAll() 
	{
		EntityManager myEM = myFactory.createEntityManager();
		List<Department> retList = myEM.createQuery("FROM Department",Department.class).getResultList();
		myEM.close();
		
		return retList;
	}
	
	/**
	 * Read Issues objects from database with the given 
	 * department Id.
	 * @return List of all issues associated with a department
	 * @throws DepartmentDoesNotExistException 
	 */
	public List<Issue> readAllIssuesByDept(int departmentID) throws DepartmentDoesNotExistException
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		String queryString = "SELECT i FROM Issue i WHERE i.department.deptId = :deptId";
				
		TypedQuery<Issue> issueQuery = myEM.createQuery(queryString,Issue.class);
		issueQuery.setParameter("deptId", departmentID);
		
		try	
		{
			return issueQuery.getResultList();
		}
		catch(NoResultException e)
		{
			throw new DepartmentDoesNotExistException();
		}
		finally
		{
			myEM.close();
		}
	}

	/**
	 * Update the Department with specified ID to the updated Department.
	 * @param departmentToBeUpdated the updated Department object
	 */
	@Override
	public void update(Department departmentToBeUpdated) 
	{
		EntityManager myEM = myFactory.createEntityManager();
		
		myEM.getTransaction().begin();
		myEM.merge(departmentToBeUpdated);
		myEM.getTransaction().commit();
		myEM.close();
		
	}

	/**
	 * Delete the specified Department from the database by deptName.
	 * @param departmentToBeDeleted the Department to be deleted
	 * @throws DepartmentDoesNotExistException 
	 */
	@Override
	public void delete(Department departmentToBeDeleted) throws DepartmentDoesNotExistException 
	{

		EntityManager myEM = myFactory.createEntityManager();

		String deptName = departmentToBeDeleted.getDeptName();

		TypedQuery<Department> userQuery = myEM.createQuery("SELECT d FROM Department d WHERE d.deptName = :deptName", Department.class);
		userQuery.setParameter("deptName", deptName);
		
		myEM.getTransaction().begin();
		try
		{
			myEM.remove(userQuery.getSingleResult());
			
		}
		catch(NoResultException e)
		{
			throw new DepartmentDoesNotExistException();
		}
		myEM.getTransaction().commit();
		myEM.close();
	}

}
