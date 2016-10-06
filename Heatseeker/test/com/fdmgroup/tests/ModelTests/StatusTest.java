package com.fdmgroup.tests.ModelTests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fdmgroup.heatseeker.model.Status;

public class StatusTest {

	@Test
	public void TestBasicUser_StatusToUpdateIsREJECTED_ReturnsFalse() {
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("BasicUser", "REJECTED", "RESOLVED");
		assertFalse(isUserChangeable);
	}
	
	@Test
	public void TestBasicUser_StatusToUpdateIsClosed_ReturnsTrue() {
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("BasicUser", "CLOSED", "RESOLVED");
		assertTrue(isUserChangeable);
	}
	
	@Test 
	public void TestBasicUser_StatusToUpdateIsOpen_CurrentStatusIsClosed_ReturnsFalse(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("BasicUser", "open", "CLOSED");
		assertFalse(isUserChangeable);
	}
	
	@Test 
	public void TestBasicUser_StatusToUpdateIsOpen_CurrentStatusIsResolved_ReturnsTrue(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("BasicUser", "OPEN", "RESOLVED");
		assertTrue(isUserChangeable);
	}
	
	@Test
	public void TestDepartmentAdmin_StatusToUpdateIsClosed_CurrentStatusIsOpen_ReturnsFalse(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("DepartmentAdmin", "Closed", "OPEN");
		assertFalse(isUserChangeable);
	}
	
	@Test
	public void TestDepartmentAdmin_StatusToUpdateIsResolved_CurrentStatusIsOpen_ReturnsTrue(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("DepartmentAdmin", "Resolved", "OPEN");
		assertTrue(isUserChangeable);
	}
	
	@Test
	public void TestGeneralAdmin_StatusToUpdateIsRejected_CurrentStatusIsNew_ReturnsFalse(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("GeneralAdmin", "rejected", "new");
		assertFalse(isUserChangeable);
	}
	
	@Test
	public void TestGeneralAdmin_StatusToUpdateIsNew_CurrentStatusIsRejected_ReturnsFalse(){
		boolean isUserChangeable = false;
		isUserChangeable = Status.isUserChangeable("GeneralAdmin", "NEW", "REJECTED");
		assertFalse(isUserChangeable);
	}

}
