package com.fdmgroup.heatseeker.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


/**
 * Basic User inherits all the behaviours of an abstract User. Basic Users
 * can file new issues and post updates to each issue. They can also interact 
 * with the admin that is managing their issue and have the ability to close 
 * an issue once it has been dealt with. 
 * 
 * @author Lawrence Leo
 * 
 */
@Entity
@DiscriminatorValue("BasicUser")
public class BasicUser extends User 
{
	public BasicUser()
	{
		super();
	}
	
	public BasicUser(String username, String password, String email) {
		super(username, password, email);
	}
	
}
