package com.octopusthu.ejw.components.sysconfig.dao.hibernate;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.octopusthu.ejw.components.sysconfig.dao.SysConfigDao;
import com.octopusthu.ejw.components.sysconfig.domain.SysConfig;
import com.octopusthu.ejw.dao.hibernate.HibernateTemplateDao;

@Repository
@Lazy
public class SysConfigDaoImpl extends HibernateTemplateDao<SysConfig> implements SysConfigDao {

	@Override
	protected Class<?> getEntityClass() {
		return SysConfig.class;
	}

	@Override
	@Resource(name = "sysConfigSessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
