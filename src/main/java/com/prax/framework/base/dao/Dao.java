package com.prax.framework.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Data Access Object (Dao) interface. This is an interface used to tag our Dao
 * classes and to provide common methods to all Daos.
 * 
 * 
 * @author Alvin.Hu
 */
public interface Dao<T> {

	/**
	 * Generic method to get an object based on class and identifier. An
	 * ObjectRetrievalFailureException Runtime Exception is thrown if nothing is
	 * found.
	 * 
	 * @param id
	 *            the identifier (primary key) of the class
	 * @return a populated object
	 * @see org.springframework.orm.ObjectRetrievalFailureException
	 */
	public T get(Serializable id);

	/**
	 * Generic method to get a list of objects based on class and identifiers.
	 * 
	 * @param ids
	 *            the identifiers (primary key) of the class
	 * @return a list of objects
	 */
	public List<T> gets(List<Serializable> ids);

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

	/**
	 * Return type of managed domain object.
	 * 
	 * @return
	 */
	public Class<T> getType();

	/**
	 * This method will take an object of Collection and will save or update all
	 * the elements in that collection object.
	 * 
	 * @param collection
	 */
	public void saveOrUpdateAll(Collection<T> collection);

}
