package fr.opensagres.xdocreport.dao.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDAO {
	/**
	 * Find an instance that matches the given ID in the persistent store
	 * 
	 * @param entityClass
	 * @param id
	 */
	public <T> T findById(final Class<T> entityClass, final Serializable id);

	/**
	 * Save a generic object.
	 * 
	 * @param entity
	 * @return
	 */
	public void save(Object entity);

	/**
	 * Either <tt>save()</tt> or <tt>update()</tt> the given instances,
	 * depending upon the values of the list
	 * 
	 * @param List
	 *            <T> entities
	 */
	public <T> void saveList(List<T> entities);

	/**
	 * Remove a persistent instance from the datastore
	 * 
	 * @param <T>
	 * @param entity
	 */
	public <T> void delete(T entity);

	/**
	 * Remove a colelction of persistent instances from the datastore
	 * 
	 * @param <T>
	 * @param entities
	 */
	<T> void deleteAll(Collection<T> entities);
	
	/**
	 * Find instances of 'entityClass' that match the criteria given by
	 * 'queryString' and the dynamic parameters 'params'
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryString
	 * @param params
	 * @return
	 */
	public <T> List<T> find(Class<T> entityClass, String queryString, Object[] params);

	/**
	 * Find instances of 'entityClass' that match the criteria given by
	 * 'queryString'
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryString
	 * @return
	 */
	public <T> List<T> find(Class<T> entityClass, String queryString);
}
