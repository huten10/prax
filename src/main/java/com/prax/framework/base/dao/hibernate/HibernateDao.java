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
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values);

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values);

    /**
     * 分页获取全部对象.
     */
    public Page<T> findAll(final Page<T> page);

    /**
     * 按HQL分页查询.
     * 
     * @param page
     *            分页参数.不支持其中的orderBy参数.
     * @param hql
     *            hql语句.
     * @param values
     *            数量可变的查询参数,按顺序绑定.
     * 
     * @return 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values);

    /**
     * 按HQL分页查询.
     * 
     * @param page
     *            分页参数.
     * @param hql
     *            hql语句.
     * @param values
     *            命名参数,按名称绑定.
     * 
     * @return 分页查询结果, 附带结果列表及所有查询时的参数.
     */
    public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values);

    /**
     * 按Criteria分页查询.
     * 
     * @param page
     *            分页参数.
     * @param criterions
     *            数量可变的Criterion.
     * 
     * @return 分页查询结果.附带结果列表及所有查询时的参数.
     */
    public Page<T> findPage(final Page<T> page, final Criterion... criterions);

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters);

    /**
     * 指定一组持久化对象，对于其所包含的以FetchType.LAZY方式引用的其它对象，通过此方法将尝试从数据库加载到内存中。
     * 
     * @param obj
     *            指定的持久化对象集合。not null。
     * @param propNames
     *            指定期望取得明细信息的属性名数组，关于每个数组元素的描述方法参见
     *            {@link #fetchDetail(Object, String)} 中的有关说明。允许传入null，表示不做任何事。
     */
    public void fetchProperties(Object obj, String[] propNames);

}
