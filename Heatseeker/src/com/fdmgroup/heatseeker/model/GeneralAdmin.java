package com.fdmgroup.heatseeker.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * General Admin inherits all the behaviours of an abstract User. General Admins
 * can file issues of their own. They can also see all issues and reassign departments
 * to which issues have been assigned if the issue was rejected by that department.
 * 
 * @author Lawrence Leo
 * 
 */
@Entity
@DiscriminatorValue("GeneralAdmin")
public class GeneralAdmin extends User
{
	public GeneralAdmin() 
	{
		super();
	}

	public GeneralAdmin(String username, String password, String email) {
		super(username, password, email);
	}
}
