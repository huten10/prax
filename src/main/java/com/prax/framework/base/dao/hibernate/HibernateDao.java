package com.prax.framework.base.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.prax.framework.base.dao.Dao;
import com.prax.framework.base.search.Page;
import com.prax.framework.base.search.PropertyFilter;

/**
 * @author Alvin.Hu
 * 
 */
public interface HibernateDao<T> extends Dao<T> {

    public HibernateTemplate getHibernateTemplate();

    /**
     * ���ݲ�ѯHQL������б���Query����.
     * 
     * �����װ��find()����ȫ��Ĭ�Ϸ��ض�������ΪT,����ΪTʱʹ�ñ�����.
     * 
     * @param values
     *            �����ɱ�Ĳ���,��˳���.
     */
    public Query createQuery(final String queryString, final Object... values);

    /**
     * ���ݲ�ѯHQL������б���Query����.
     * 
     * @param values
     *            ��������,�����ư�.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values);

    /**
     * ��ҳ��ȡȫ������.
     */
    public Page<T> findAll(final Page<T> page);

    /**
     * ��HQL��ҳ��ѯ.
     * 
     * @param page
     *            ��ҳ����.��֧�����е�orderBy����.
     * @param hql
     *            hql���.
     * @param values
     *            �����ɱ�Ĳ�ѯ����,��˳���.
     * 
     * @return ��ҳ��ѯ���, ��������б����в�ѯʱ�Ĳ���.
     */
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values);

    /**
     * ��HQL��ҳ��ѯ.
     * 
     * @param page
     *            ��ҳ����.
     * @param hql
     *            hql���.
     * @param values
     *            ��������,�����ư�.
     * 
     * @return ��ҳ��ѯ���, ��������б����в�ѯʱ�Ĳ���.
     */
    public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values);

    /**
     * ��Criteria��ҳ��ѯ.
     * 
     * @param page
     *            ��ҳ����.
     * @param criterions
     *            �����ɱ��Criterion.
     * 
     * @return ��ҳ��ѯ���.��������б����в�ѯʱ�Ĳ���.
     */
    public Page<T> findPage(final Page<T> page, final Criterion... criterions);

    /**
     * �����Թ��������б��ҳ���Ҷ���.
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters);

    /**
     * ָ��һ��־û����󣬶���������������FetchType.LAZY��ʽ���õ���������ͨ���˷��������Դ����ݿ���ص��ڴ��С�
     * 
     * @param obj
     *            ָ���ĳ־û����󼯺ϡ�not null��
     * @param propNames
     *            ָ������ȡ����ϸ��Ϣ�����������飬����ÿ������Ԫ�ص����������μ�
     *            {@link #fetchDetail(Object, String)} �е��й�˵����������null����ʾ�����κ��¡�
     */
    public void fetchProperties(Object obj, String[] propNames);

}
