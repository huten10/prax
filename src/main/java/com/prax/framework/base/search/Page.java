/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Page.java 838 2010-01-06 13:47:36Z calvinxiu $
 */
package com.prax.framework.base.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * �����ORMʵ���޹صķ�ҳ��������ѯ�����װ. ע��������Ŵ�1��ʼ.
 * 
 * @param <T>
 *            Page�м�¼������.
 * 
 * @author calvin
 */
public class Page<T> {
    // -- �������� --//
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    // -- ��ҳ���� --//
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected String orderBy = null;
    protected String order = null;
    protected boolean autoCount = true;
    
    //Eager fetch properties
    private String[] fetchProps;

	// -- ���ؽ�� --//
    protected List<T> result = new ArrayList<T>();
    protected long totalCount = -1;

    // -- ���캯�� --//
    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    // -- ���ʲ�ѯ�������� --//
    /**
     * ��õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,Ĭ��Ϊ1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * ���õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,����1ʱ�Զ�����Ϊ1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    public Page<T> pageNo(final int thePageNo) {
        setPageNo(thePageNo);
        return this;
    }

    /**
     * ���ÿҳ�ļ�¼����,Ĭ��Ϊ1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * ����ÿҳ�ļ�¼����,����1ʱ�Զ�����Ϊ1.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;

        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }

    public Page<T> pageSize(final int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * ����pageNo��pageSize���㵱ǰҳ��һ����¼���ܽ�����е�λ��,��Ŵ�1��ʼ.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize) + 1;
    }

    /**
     * ��������ֶ�,��Ĭ��ֵ.��������ֶ�ʱ��','�ָ�.
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * ���������ֶ�,��������ֶ�ʱ��','�ָ�.
     */
    public void setOrderBy(final String orderBy) {
        this.orderBy = orderBy;
    }

    public Page<T> orderBy(final String theOrderBy) {
        setOrderBy(theOrderBy);
        return this;
    }

    /**
     * ���������.
     */
    public String getOrder() {
        return order;
    }

    /**
     * ��������ʽ��.
     * 
     * @param order
     *            ��ѡֵΪdesc��asc,��������ֶ�ʱ��','�ָ�.
     */
    public void setOrder(final String order) {
    	if(StringUtils.isEmpty(order))
    		return;
        // ���order�ַ����ĺϷ�ֵ
        String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
        for (String orderStr : orders) {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
                throw new IllegalArgumentException("������" + orderStr + "���ǺϷ�ֵ");
            }
        }

        this.order = StringUtils.lowerCase(order);
    }

    public Page<T> order(final String theOrder) {
        setOrder(theOrder);
        return this;
    }

    /**
     * �Ƿ������������ֶ�,��Ĭ��ֵ.
     */
    public boolean isOrderBySetted() {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    /**
     * ��ѯ����ʱ�Ƿ��Զ�����ִ��count��ѯ��ȡ�ܼ�¼��, Ĭ��Ϊfalse.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * ��ѯ����ʱ�Ƿ��Զ�����ִ��count��ѯ��ȡ�ܼ�¼��.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }
    
    public String[] getFetchProps() {
		return fetchProps;
	}

	public void setFetchProps(String[] fetchProps) {
		this.fetchProps = fetchProps;
	}

    // -- ���ʲ�ѯ������� --//

    /**
     * ȡ��ҳ�ڵļ�¼�б�.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * ����ҳ�ڵļ�¼�б�.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * ȡ���ܼ�¼��, Ĭ��ֵΪ-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * �����ܼ�¼��.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * ����pageSize��totalCount������ҳ��, Ĭ��ֵΪ-1.
     */
    public int getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return (int)count;
    }

    /**
     * �Ƿ�����һҳ.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪβҳʱ�Է���βҳ���.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * �Ƿ�����һҳ.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪ��ҳʱ������ҳ���.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }
}
