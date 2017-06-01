package com.octopusthu.ejw.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.octopusthu.ejw.dao.BaseDao;

public abstract class HibernateTemplateDao<T> implements BaseDao<T> {
	protected static final Log log = LogFactory.getLog(HibernateTemplateDao.class);

	protected SessionFactory sessionFactory;

	protected abstract Class<?> getEntityClass();

	protected abstract void setSessionFactory(SessionFactory sessionFactory);

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getById(Serializable id) {
		return (T) getSession().get(getEntityClass(), id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getByUniqueKey(String columnName, Object value) {
		return (T) getSession().createCriteria(getEntityClass()).add(Restrictions.eq(columnName, value)).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getListByColumn(String columnName, Object value) {
		List<T> list = null;
		try {
			list = (List<T>) getSession().createCriteria(getEntityClass()).add(Restrictions.eq(columnName, value))
					.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getListByColumns(Map<String, Object> nameValuePairs) {
		List<T> list = null;
		try {
			Criteria criteria = getSession().createCriteria(getEntityClass());
			for (Map.Entry<String, Object> entry : nameValuePairs.entrySet()) {
				criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}
			list = (List<T>) criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return (List<T>) getSession().createCriteria(getEntityClass()).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T getUniqueResult(Map<String, Object> nameValuePairs) {
		Criteria criteria = getSession().createCriteria(getEntityClass());
		for (Map.Entry<String, Object> entry : nameValuePairs.entrySet()) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(getEntityClass(), id);
	}

	@Override
	public Serializable save(T t) {
		return getSession().save(t);
	}

	@Override
	public void update(T t) {
		this.getSession().update(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
	}

	@Override
	public void delete(T t) {
		this.getSession().delete(t);
	}

}
