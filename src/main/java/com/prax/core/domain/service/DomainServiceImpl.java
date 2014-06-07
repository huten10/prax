package com.prax.core.domain.service;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.prax.core.domain.entity.Domain;
import com.prax.framework.base.model.BasicState;
import com.prax.framework.base.service.ServiceImpl;

public class DomainServiceImpl extends ServiceImpl<Domain> implements
		DomainService {

	@Transactional
	public Domain getRoot() {
		Domain rootDomain = getByCode(VALUE_ROOT_CODE);
		if (rootDomain == null)
			return initRoot();
		return rootDomain;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Domain getByCode(String code) {

		String hql = "from " + Domain.class.getName()
				+ " d where upper(d.code) = :code and d.state <> :deleted";

		Query qry = getHibernateDao().createQuery(hql);
		qry.setParameter("code", code.toUpperCase());
		qry.setParameter("deleted", BasicState.DELETED);
		List<Domain> list = qry.list();
		if (list.size() == 0)
			return null;
		Domain domain = list.get(0);
		return domain;
	}

	private Domain initRoot() {
		Domain rootDomain = new Domain();
		rootDomain.setCode(VALUE_ROOT_CODE);
		rootDomain.setName("Root”Ú");
		rootDomain.setNote("Root”Ú");
		
		add(rootDomain);
		return rootDomain;
	}

}
