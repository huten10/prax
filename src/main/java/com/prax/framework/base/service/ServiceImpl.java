/*
 * Project EBam
 * Copyright Information
 * (C) 2009-2011, SunGard Inc. All rights reserved.
 * 3F, No.210 Liangjing Road, Zhangjiang High-Tech Park, Shanghai, 201203, China.
 * 
 * This document is protected by copyright. No part of this
 * document may be reproduced in any form by any means without
 * prior written authorization of SunGard.
 * 
 */
package com.prax.framework.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.springframework.transaction.annotation.Transactional;

import com.prax.framework.base.dao.Dao;
import com.prax.framework.base.dao.hibernate.HibernateDao;
import com.prax.framework.base.search.Page;
import com.prax.framework.base.search.PropertyFilter;
import com.prax.framework.util.ExceptionUtils;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 * 
 */
@Transactional
public class ServiceImpl<T> implements Service<T> {

	protected Dao<T> dao = null;

	public Dao<T> getDao() {
		return dao;
	}

	public void setDao(Dao<T> dao) {
		this.dao = dao;
	}

	public HibernateDao<T> getHibernateDao() {
		return (HibernateDao<T>) dao;
	}

	public T create() {
		Class<T> type = getDao().getType();
		T o = null;
		try {
			o = type.newInstance();
		}
		catch (Exception e) {
			// 正常情况下不会发生，如果发了异常之间抛出。
			throw ExceptionUtils.wrapThrow(e);
		}
		return o;
	}

	public Page<T> findAll(Page<T> page) {
		return getHibernateDao().findAll(page);
	}

	public Page<T> findPage(Page<T> page, Criterion... criterions) {
		return getHibernateDao().findPage(page, criterions);
	}

	public Page<T> findPage(Page<T> page, List<PropertyFilter> filters) {
		return getHibernateDao().findPage(page, filters);
	}

	public T get(Serializable id, String[] fetchProps) {
		T o = getHibernateDao().get(id);
		getHibernateDao().fetchProperties(o, fetchProps);
		return o;
	}

	public List<T> gets(List<Serializable> ids, String[] fetchProps) {
		List<T> list = getHibernateDao().gets(ids);
		getHibernateDao().fetchProperties(list, fetchProps);
		return list;
	}

	public void add(T entity) {
		getHibernateDao().add(entity);
	}

	public void update(T entity) {
		getHibernateDao().update(entity);
	}

	public void delete(T entity) {
		getHibernateDao().delete(entity);
	}

	public void delete(Serializable id) {
		getHibernateDao().delete(id);
	}

}
