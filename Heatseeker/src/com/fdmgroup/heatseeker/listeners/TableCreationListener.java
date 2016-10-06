package com.fdmgroup.heatseeker.listeners;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.DepartmentDAO;
import com.fdmgroup.heatseeker.DAOs.IssueDAO;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.IssueAlreadyExistsException;
import com.fdmgroup.heatseeker.exceptions.UserAlreadyExistsException;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.Department;
import com.fdmgroup.heatseeker.model.DepartmentAdmin;
import com.fdmgroup.heatseeker.model.GeneralAdmin;
import com.fdmgroup.heatseeker.model.Issue;
import com.fdmgroup.heatseeker.model.Priority;
import com.fdmgroup.heatseeker.model.User;

/**
 * Application Lifecycle Listener implementation class UserCreationListener
 * 
 * @author Michael Loconte
 * 
 */
public class TableCreationListener implements ServletContextListener {

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		
		DepartmentDAO deptDao = (DepartmentDAO) ApplicationContextProvider.getApplicationContext().getBean("departmentDAO");
		UserDAO userDao = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		IssueDAO issueDao = (IssueDAO) ApplicationContextProvider.getApplicationContext().getBean("issueDAO");
				
		if (deptDao.readAll().isEmpty()) {
			Department genDept = new Department();
			genDept.setDeptName("General");
			deptDao.create(genDept);
			
			Department itDept = new Department();
			itDept.setDeptName("IT");
			deptDao.create(itDept);
			
			Department hrDept = new Department();
			hrDept.setDeptName("HR");
			deptDao.create(hrDept);
			
			Department marketingDept = new Department();
			marketingDept.setDeptName("Marketing");
			deptDao.create(marketingDept);
			
			Department financeDept = new Department();
			financeDept.setDeptName("Finance");
			deptDao.create(financeDept);
			
			Department maintenanceDept = new Department();
			maintenanceDept.setDeptName("Maintenance");
			deptDao.create(maintenanceDept);

			User systemuser = new BasicUser("SYSTEM", "ADMIN123", "a@fdmgroup.com");
			User sahil = new BasicUser("Sahil.Shah", "sahil", "Sahil.Shah@fdmgroup.com");
			User kishan = new BasicUser("Kishan.Patel1", "kishan", "Kishan.Patel1@fdmgroup.com");
			
			User ming = new DepartmentAdmin("Ming.Kong", "ming", "Ming.Kong@fdmgroup.com");
			ming.setDept(maintenanceDept);
			
			User lei = new DepartmentAdmin("Lei.Lin", "lei", "Lei.Lin@fdmgroup.com");
			lei.setDept(itDept);	
			
			User joel = new DepartmentAdmin("Joel.Hyman", "joel", "Joel.Hyman@fdmgroup.com");
			joel.setDept(hrDept);	
			
			User michael = new DepartmentAdmin("Michael.Loconte", "michael", "Michael.Loconte@fdmgroup.com");
			michael.setDept(marketingDept);
			
			User shayna = new DepartmentAdmin("Shayna.Froimowitz", "shayna", "Shayna.Froimowitz@fdmgroup.com");
			shayna.setDept(financeDept);

			User antony = new GeneralAdmin("Antony.Kwok", "antony", "Antony.Kwok@fdmgroup.com");
			antony.setDept(genDept);
						
			Calendar c = Calendar.getInstance();
			c.set(2016, 4, 24);
			Date d1 = c.getTime();
			
			c.set(2016, 3, 21);
			Date d2 = c.getTime();
			
			c.set(2016, 7, 16);
			Date d3 = c.getTime();
			
			c.set(2016, 7, 20);
			Date d4 = c.getTime();
			
			c.set(2016, 7, 23);
			Date d5 = c.getTime();
			
			c.set(2016, 7, 25);
			Date d6 = c.getTime();
			
			Issue issue1 = new Issue("Need new keyboard", "Hi, my number pad does not work and I would like a new keyboard please.", itDept, sahil, Priority.HIGH, d1);
			sahil.addIssue(issue1);
			
			Issue issue2 = new Issue("I'm locked out of my email", "I am trying to access my email but my password is not working. Can you please look into it? If you can reset the password and send me a temporary password, that would be great. Thank you", hrDept, kishan, Priority.LOW, d2);
			kishan.addIssue(issue2);
			
			Issue issue3 = new Issue("My pay is not up to date", "My pay is incorrect, can you please look into it?", financeDept, sahil, Priority.MEDIUM, d3);
			sahil.addIssue(issue3);
			
			Issue issue4 = new Issue("How can I apply for 401k?", "I need help and more instructions to enroll in 401k. Can you help?", itDept, kishan, Priority.LOW, d4);
			kishan.addIssue(issue4);
			
			Issue issue5 = new Issue("Toilet paper in the urinal", "Someone put toilet paper in the urinal and it is currently clogged", genDept, antony, Priority.MEDIUM, d5);
			antony.addIssue(issue5);
			
			Issue issue6 = new Issue("AC does not work", "Our classroom is very hot and stuffy. It is getting very difficult to breathe", itDept, joel, Priority.HIGH, d6);
			joel.addIssue(issue6);
			
			Issue issue7 = new Issue("Door knob fell off the door", "I tried to open the door of my classroom and it just fell off. We are now all trapped", itDept, lei, Priority.HIGH, new Date());
			lei.addIssue(issue7);
						
			Issue issue8 = new Issue("Incorrect name on newsletter", "I just checked out this month's newsletter and I noticed that my name is spelled incorrectly", marketingDept, shayna, Priority.LOW, new Date());
			shayna.addIssue(issue8);
			
			Issue issue9 = new Issue("Elevator is down", "The smaller elevator that transport between 29-31st floor is not working", itDept, michael, Priority.MEDIUM, new Date());
			michael.addIssue(issue9);
			
			Issue issue10 = new Issue("Refridgerator is full", "Hi, the refridgerator is full and I need to store my leftover pizza", genDept, antony, Priority.LOW, new Date());
			antony.addIssue(issue10);
			
			
			
			try {
				userDao.create(ming);
				userDao.create(sahil);
				userDao.create(kishan);
				userDao.create(shayna);
				userDao.create(michael);
				userDao.create(antony);
				userDao.create(lei);
				userDao.create(joel);
				userDao.create(systemuser);
				

				issueDao.create(issue1);
				issueDao.create(issue2);
				issueDao.create(issue3);
				issueDao.create(issue4);
				issueDao.create(issue5);
				issueDao.create(issue6);
				issueDao.create(issue7);
				issueDao.create(issue8);
				issueDao.create(issue9);
				issueDao.create(issue10);
			} catch (UserAlreadyExistsException | IssueAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		} 
	
	}
	
	
	

}
