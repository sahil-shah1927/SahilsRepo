package com.fdmgroup.heatseeker.listener;

import static org.mockito.Mockito.mock;

import javax.servlet.ServletContextEvent;

import org.junit.AfterClass;
import org.junit.Test;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;
import com.fdmgroup.heatseeker.DAOs.UserDAO;
import com.fdmgroup.heatseeker.exceptions.DepartmentDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.IssueDoesNotExistException;
import com.fdmgroup.heatseeker.exceptions.UserDoesNotExistException;
import com.fdmgroup.heatseeker.listeners.TableCreationListener;
import com.fdmgroup.heatseeker.model.BasicUser;
import com.fdmgroup.heatseeker.model.User;


public class TestTableCreationListener {

	private TableCreationListener listener = new TableCreationListener();
	
	
	@Test
	public void test_tableCreationListener_smokeTest() throws DepartmentDoesNotExistException {
		ServletContextEvent sce = mock(ServletContextEvent.class);
		listener.contextInitialized(sce);		
	}
	
	
	
	@AfterClass
	public static void deletesystemuser() throws IssueDoesNotExistException{
		UserDAO dao = (UserDAO) ApplicationContextProvider.getApplicationContext().getBean("userDAO");
		User systemUser = new BasicUser("SYSTEM", "ADMIN123", "a@fdmgroup.com");
		User userToBeDeleted;
		try {
			userToBeDeleted = dao.read(systemUser);
			dao.delete(userToBeDeleted);
		} catch (UserDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	


}
