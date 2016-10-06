package com.fdmgroup.heatseeker.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Issue Update Class is an object which holds the update to an issue. Is used
 * inside the Issue class.
 * 
 * @author Michael Loconte
 *
 */
@Entity
public class IssueUpdates implements Comparable<IssueUpdates>{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int updateId;
	
	@ManyToOne
	@JoinColumn(name="submitted_by", nullable=false)
	private User submittedBy;
	
	@Column(name="Update_date", nullable=false)
	private Date updateDate;
	
	@Column(length=300)
	private String updateText;
	
	public IssueUpdates(){
		super();
	}

	public int getUpdateId() {
		return updateId;
	}

	public void setUpdateId(int updateId) {
		this.updateId = updateId;
	}


	public User getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(User submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateText() {
		return updateText;
	}

	public void setUpdateText(String update) {
		this.updateText = update;
	}
	
	
	public String getFormattedDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		return dateFormatter.format(updateDate);
	}

	@Override
	public int compareTo(IssueUpdates o) {
		// TODO Auto-generated method stub
		return o.getUpdateDate().compareTo(updateDate);
	}
	
}
