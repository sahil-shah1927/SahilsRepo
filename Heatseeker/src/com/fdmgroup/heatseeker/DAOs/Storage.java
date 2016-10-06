package com.fdmgroup.heatseeker.DAOs;

import java.util.List;

/**
 * 
 * @author Sahil Shah
 * @version 1.0
 * @param <T>
 */
public interface Storage<T> 
{
	public void create(T t) throws Exception; 
	public T read(T t) throws Exception;
	public List<T> readAll();
	public void update(T t);
	public void delete(T t) throws Exception;
}
