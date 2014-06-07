package com.prax.framework.base.dao.hibernate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.prax.framework.base.search.Page;
import com.prax.framework.base.search.PropertyFilter;
import com.prax.framework.base.search.PropertyFilter.MatchType;
import com.prax.framework.util.ExceptionUtils;
import com.prax.framework.util.ReflectionUtils;

/**
 * @author Alvin.Hu
 * 
 */
@SuppressWarnings("unchecked")
public class BaseDaoHibernate<T> extends HibernateDaoSupport implements HibernateDao<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> entityClass;

    /**
     * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class.
     */
    public BaseDaoHibernate() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public BaseDaoHibernate(Class<T> entityClass) {
        if (entityClass != null)
            this.entityClass = entityClass;
        else
            this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public T get(Serializable id) {
        Assert.notNull(id, "id can not be null");

        return (T) getHibernateTemplate().get(entityClass, id);
    }

    public List<T> gets(List<Serializable> ids) {

        Assert.notNull(ids, "entity can not be null");

        Criteria criteria = createCriteria(Restrictions.in(getIdName(), ids));
        return criteria.list();
    }

    public void add(T entity) {

        Assert.notNull(entity, "entity can not be null");

        getHibernateTemplate().saveOrUpdate(entity);

    }

    public void update(T entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        Assert.notNull(entity, "entity can not be null");
        getHibernateTemplate().delete(entity);
        logger.debug("delete entity: {}", entity);
    }

    public void delete(Serializable id) {
        Assert.notNull(id, "id can not be null");
        delete(get(id));
        logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
    }

    public void saveOrUpdateAll(Collection<T> records) {
        getHibernateTemplate().saveOrUpdateAll(records);
    }

    public Class<T> getType() {
        return entityClass;
    }

    /**
     * get identifier property name.
     */
    private String getIdName() {
        ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    private Criteria createCriteria(final Criterion... criterions) {
        Criteria criteria = getSession().createCriteria(entityClass);
        for (Criterion c : criterions) {
            criteria.add(c);
        }
        return criteria;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    public Query createQuery(final String queryString, final Object... values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /**
     * 根据查询HQL与参数列表创建Query对象.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    public Query createQuery(final String queryString, final Map<String, ?> values) {
        Assert.hasText(queryString, "queryString不能为空");
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            query.setProperties(values);
        }
        return query;
    }

    // -- 分页查询函数 --//
    /**
     * 分页获取全部对象.
     */
    public Page<T> findAll(final Page<T> page) {
        return findPage(page);
    }

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
    public Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameter(q, page);
        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

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
    public Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
        Assert.notNull(page, "page不能为空");

        Query q = createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        setPageParameter(q, page);

        List<T> result = q.list();
        page.setResult(result);
        return page;
    }

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
    public Page<T> findPage(final Page<T> page, final Criterion... criterions) {
        Assert.notNull(page, "page不能为空");

        Criteria c = createCriteria(criterions);

        if (page.isAutoCount()) {
            long totalCount = countCriteriaResult(c);
            page.setTotalCount(totalCount);
        }

        setPageParameter(c, page);
        List<T> result = c.list();
        page.setResult(result);
        return page;
    }

    /**
     * 设置分页参数到Query对象,辅助函数.
     */
    protected Query setPageParameter(final Query q, final Page<T> page) {
        // hibernate的firstResult的序号从0开始
        q.setFirstResult(page.getFirst() - 1);
        q.setMaxResults(page.getPageSize());
        return q;
    }

    /**
     * 设置分页参数到Criteria对象,辅助函数.
     */
    protected Criteria setPageParameter(final Criteria c, final Page<T> page) {
        // hibernate的firstResult的序号从0开始
        c.setFirstResult(page.getFirst() - 1);
        c.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted()) {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');

            Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

            for (int i = 0; i < orderByArray.length; i++) {
                if (Page.ASC.equals(orderArray[i])) {
                    c.addOrder(Order.asc(orderByArray[i]));
                } else {
                    c.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return c;
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String hql, final Object... values) {
        String fromHql = hql;
        // select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values
     *            数量可变的参数,按顺序绑定.
     */
    public <X> X findUnique(final String hql, final Object... values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 按HQL查询唯一对象.
     * 
     * @param values
     *            命名参数,按名称绑定.
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values) {
        return (X) createQuery(hql, values).uniqueResult();
    }

    /**
     * 执行count查询获得本次Hql查询所能获得的对象总数.
     * 
     * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
     */
    protected long countHqlResult(final String hql, final Map<String, ?> values) {
        String fromHql = hql;
        // select子句与order by子句会影响count查询,进行简单的排除.
        fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
        fromHql = StringUtils.substringBefore(fromHql, "order by");

        String countHql = "select count(*) " + fromHql;

        try {
            Long count = findUnique(countHql, values);
            return count;
        } catch (Exception e) {
            throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
        }
    }

    /**
     * 执行count查询获得本次Criteria查询所能获得的对象总数.
     */
    protected long countCriteriaResult(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;

        // 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();

        List<CriteriaImpl.OrderEntry> orderEntries = null;
        try {
            orderEntries = (List<CriteriaImpl.OrderEntry>) ReflectionUtils.getFieldValue(impl, "orderEntries");
            ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList<CriteriaImpl.OrderEntry>());
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        // 执行Count查询
        long totalCount = (Long) c.setProjection(Projections.rowCount()).uniqueResult();

        // 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
        c.setProjection(projection);

        if (projection == null) {
            c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null) {
            c.setResultTransformer(transformer);
        }
        try {
            ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
        } catch (Exception e) {
            logger.error("不可能抛出的异常:{}", e.getMessage());
        }

        return totalCount;
    }

    /**
     * 按属性过滤条件列表分页查找对象.
     */
    public Page<T> findPage(final Page<T> page, final List<PropertyFilter> filters) {
        Criterion[] criterions = buildPropertyFilterCriterions(filters);
        return findPage(page, criterions);
    }

    /**
     * 按属性条件列表创建Criterion数组,辅助函数.
     */
    protected Criterion[] buildPropertyFilterCriterions(final List<PropertyFilter> filters) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        for (PropertyFilter filter : filters) {
            if (!filter.isMultiProperty()) { // 只有一个属性需要比较的情况.
                Criterion criterion = buildPropertyFilterCriterion(filter.getPropertyName(), filter.getPropertyValue(),
                        filter.getMatchType());
                criterionList.add(criterion);
            } else {// 包含多个属性需要比较的情况,进行or处理.
                Disjunction disjunction = Restrictions.disjunction();
                for (String param : filter.getPropertyNames()) {
                    Criterion criterion = buildPropertyFilterCriterion(param, filter.getPropertyValue(),
                            filter.getMatchType());
                    disjunction.add(criterion);
                }
                criterionList.add(disjunction);
            }
        }
        return criterionList.toArray(new Criterion[criterionList.size()]);
    }

    /**
     * 按属性条件参数创建Criterion,辅助函数.
     */
    protected Criterion buildPropertyFilterCriterion(final String propertyName, final Object propertyValue,
            final MatchType matchType) {
        Assert.hasText(propertyName, "propertyName不能为空");
        Criterion criterion = null;
        try {

            // 根据MatchType构造criterion
            if (MatchType.EQ.equals(matchType)) {
                criterion = Restrictions.eq(propertyName, propertyValue);
            } else if (MatchType.LIKE.equals(matchType)) {
                criterion = Restrictions.like(propertyName, (String) propertyValue, MatchMode.ANYWHERE);
            } else if (MatchType.LE.equals(matchType)) {
                criterion = Restrictions.le(propertyName, propertyValue);
            } else if (MatchType.LT.equals(matchType)) {
                criterion = Restrictions.lt(propertyName, propertyValue);
            } else if (MatchType.GE.equals(matchType)) {
                criterion = Restrictions.ge(propertyName, propertyValue);
            } else if (MatchType.GT.equals(matchType)) {
                criterion = Restrictions.gt(propertyName, propertyValue);
            }
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
        return criterion;
    }

    /**
     * {@inheritDoc}
     */
    public void fetchProperties(Object obj, String[] propNames) {
        if (obj == null)
            return;
        if (propNames == null || propNames.length == 0)
            return;
        if (getSession().contains(obj) == false)
            throw new IllegalArgumentException("指定的参数obj必须是PO。");

        for (String propName : propNames)
            fetchProperty(obj, propName);
    }

    private void fetchProperty(Object po, String propName) {
        assert po != null;
        if (propName == null)
            return;

        String[] words = propName.split("\\.", 2);
        Object obj = initializeProperty(po, words[0]);
        if (obj == null)
            return;

        Hibernate.initialize(obj);

        if (words.length <= 1)
            return;

        if (obj instanceof Collection) {
            Collection<Object> col = (Collection<Object>) obj;
            for (Object item : col) {
                fetchProperty(item, words[1]);
            }
        } else
            fetchProperty(obj, words[1]);
    }

    private Object initializeProperty(Object po, String propName) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(po.getClass());
            for (PropertyDescriptor propDesc : beanInfo.getPropertyDescriptors()) {
                if (propName.equals(propDesc.getName())) {
                    Method m = propDesc.getReadMethod();
                    if (m == null)
                        return null;
                    Object o = m.invoke(po);
                    Hibernate.initialize(o);
                    return o;
                }
            }
            return null;
        } catch (Exception e) {
            throw ExceptionUtils.wrapThrow(e);
        }
    }
}
