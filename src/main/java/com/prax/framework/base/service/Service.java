package com.prax.framework.base.service;

import java.io.Serializable;
import java.util.List;

import com.prax.framework.base.dao.Dao;
import com.prax.framework.base.search.Page;

public interface Service<T> {
	/**
	 * Get Dao object
	 * 
	 * @param dao
	 */
	public Dao<T> getDao();

	/**
	 * Expose the setDao method for testing purposes
	 * 
	 * @param dao
	 */
	public void setDao(Dao<T> dao);
	
	/**
	 * Generic method to create an object.
	 * 
	 * @param entity
	 *            the object to add
	 */
	public T create();

	/**
	 * Generic method used to get a all objects of a particular type.
	 * 
	 * @param params
	 *            the sorting filtering parameters
	 * @return List of populated objects
	 */
	public Page<T> findAll(Page<T> page);

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the class
	 * @param fetchProps
	 *            the properties need to eager fetch
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	public T get(Serializable id, String[] fetchProps);

	/**
	 * Generic method to get a list of objects based on class and identifiers.
	 * 
	 * @param ids
	 *            the identifiers (primary key) of the class
	 * @return a list of objects
	 */
	public List<T> gets(List<Serializable> ids, String[] fetchProps);

	/**
	 * Generic method to add an object.
	 * 
	 * @param entity
	 *            the object to add
	 */
	public void add(T entity);

	/**
	 * Generic method to update an object.
	 * 
	 * @param entity
	 *            the object to save
	 */
	public void update(T entity);

	/**
	 * Generic method to delete an object
	 * 
	 * @param entity
	 *            the object to delete
	 */
	public void delete(T entity);

	/**
	 * Generic method to delete an object based on class and id
	 * 
	 * @param id
	 *            the identifier (primary key) of the class
	 */
	public void delete(Serializable id);

}
