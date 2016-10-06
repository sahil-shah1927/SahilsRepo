package com.fdmgroup.heatseeker.model;

/**
 * Priority ENUM defines priority that can be assigned to issues.
 * 
 * @author Sahil Shah
 *
 */
public enum Priority 
{
	LOW,MEDIUM,HIGH;
	
	@Override
	public String toString() 
	{
		String str = super.toString();
		return str.substring(0, 1) + str.substring(1).toLowerCase();
	}
}
