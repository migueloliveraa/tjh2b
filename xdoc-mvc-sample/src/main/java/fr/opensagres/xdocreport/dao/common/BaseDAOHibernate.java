package fr.opensagres.xdocreport.dao.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.NonUniqueObjectException;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDAOHibernate extends HibernateDaoSupport implements BaseDAO {

	@Override
	public <T> T findById(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public void save(Object entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (HibernateSystemException systemExc) {
			if (systemExc.getRootCause() instanceof NonUniqueObjectException) {
				getHibernateTemplate().merge(entity);
			} else {
				throw systemExc;
			}
		}
	}

	@Override
	public <T> void saveList(List<T> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void delete(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void deleteAll(Collection<T> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, String queryString, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

}
