package com.fdmgroup.heatseeker.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * Department Admin inherits all the behaviours of an abstract User. Department
 * Admins can file new issues of their own. They can also see the issues that 
 * have been assigned to their respective department. They can change the status
 * of each issue and can also reject the issue if it was incorrectly assigned
 * which will defer the issue to the general admin. Lastly, they can interact with
 * the user for each issue.
 * 
 * @author Lawrence Leo
 * 
 * 
 */
@Entity
@DiscriminatorValue("DepartmentAdmin")
public class DepartmentAdmin extends User
{
	public DepartmentAdmin() 
	{
		super();
	}

	public DepartmentAdmin(String username, String password, String email) {
		super(username, password, email);
	}
	
	
}
