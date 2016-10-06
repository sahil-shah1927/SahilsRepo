package com.fdmgroup.heatseeker.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdmgroup.heatseeker.DAOs.ApplicationContextProvider;

/**
 * Abstract class User defines attributes and behaviours for BasicUser,
 * GeneralAdmin, and DepartmentAdmin classes. All users have a list of issues
 * which allows them to file their own issues.
 * 
 * @author Lawrence Leo
 *
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "Role")
@Table(name = "HeatseekerUsers")
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@OneToMany(cascade={CascadeType.MERGE,CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH }, 
			fetch=FetchType.EAGER, 
			mappedBy="submittedBy")
	private List<Issue> issues;

	@ManyToOne
	private Department dept;
	
	@Column(length=30)
	private String email;
	
	@Column(nullable=false, length=30)
	private String username;
	
	@Column(nullable=false, length=60)
	private String password;
	
	public User() {
		issues = new ArrayList<Issue>();

	}

	public User(String username, String password, String email) {
		PasswordEncoder encoder = ApplicationContextProvider.getApplicationContext().getBean("passwordEncoder",
			BCryptPasswordEncoder.class);

		this.username = username;
		this.password = encoder.encode(password);
		this.email = email;
		issues = new ArrayList<Issue>();
		
	}
	
	
	
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Department getDept() {
		return dept;

	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	
	public void addIssue(Issue issue){
		this.getIssues().add(issue);
	}
}
