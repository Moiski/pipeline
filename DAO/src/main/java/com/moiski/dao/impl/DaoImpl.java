package com.moiski.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moiski.dao.IDao;
import com.moiski.dao.exceptions.DaoExeptions;

@Repository
public class DaoImpl<T> implements IDao<T> {

	protected static Logger logger = Logger.getLogger(DaoImpl.class);
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public DaoImpl() {
	}

	public void saveOrUpdate(T t) throws DaoExeptions {
		logger.info("Save or update entity: " + getClass().getName());
		try {
			getSession().saveOrUpdate(t);
			logger.info("Save or update entity: " + getClass().getName());
		} catch (HibernateException e) {
			logger.error("Error save or update entity of" + t.getClass().getName() + "class in DAO" + e);
			throw new DaoExeptions(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T get(Serializable id) throws DaoExeptions {
		logger.info("Get entity by id:" + id);
		T t = null;
		try {
			t = (T) getSession().get(getPersistentClass(), id);
			logger.info("Get entity: " + t);
		} catch (HibernateException e) {
			logger.error("Error getting entity of " + getPersistentClass() + "class in Dao" + e);
			throw new DaoExeptions(e);
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public T load(Serializable id) throws DaoExeptions {
		logger.info("Load entity by id:" + id);
		T t = null;
		try {
			t = (T) getSession().load(getPersistentClass(), id);
			logger.info("Loaded entity:" + t);
		} catch (ObjectNotFoundException e) {
			logger.error("Error getting entity of " + getPersistentClass() + "class in Dao" + e);
			throw new DaoExeptions(e);
		} catch (HibernateException e) {
			logger.error("Error getting entity of " + getPersistentClass() + "class in Dao" + e);
			throw new DaoExeptions(e);
		}
		return t;
	}

	public void delete(T t) throws DaoExeptions {
		logger.info("Delete entity: " + t);
		try {
			getSession().delete(t);
			logger.info("Deleted entity:" + t);
		} catch (HibernateException e) {
			logger.error("Error deleting entity of " + t.getClass().getName() + " class in Dao" + e);
			throw new DaoExeptions(e);
		}
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getPersistentClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

}
