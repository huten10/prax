/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: PropertyFilter.java 873 2010-01-18 16:38:24Z calvinxiu $
 */
package com.prax.framework.base.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.util.Assert;

import com.prax.framework.util.ReflectionUtils;

/**
 * �����ORMʵ���޹ص����Թ���������װ��.
 * 
 * PropertyFilter��Ҫ��¼ҳ���м򵥵�������������,��Hibernate��CriterionҪ��.
 * 
 * @author calvin
 */
public class PropertyFilter {

	public static final String FILTER_PREFIX = "FILTER_";

	/** ������Լ�OR��ϵ�ķָ���. */
	public static final String OR_SEPARATOR = "_OR_";

	/** ���ԱȽ�����. */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE;
	}

	/** ������������. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String[] propertyNames = null;

	private Class<?> propertyType = null;

	private Object propertyValue = null;

	private MatchType matchType = null;

	public PropertyFilter() {
	}

	/**
	 * ����Filter
	 * @param request
	 * @return
	 */
	public static List<PropertyFilter> buildFilers(ServletRequest request) {
		return buildFilers(request, FILTER_PREFIX);
	}

	@SuppressWarnings("unchecked")
	public static List<PropertyFilter> buildFilers(ServletRequest request, String prefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		ArrayList<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values != null) && (values.length > 0)) {
					String value = StringUtils.trim(values[0]);
					if (!StringUtils.isEmpty(value)) {
						filters.add(new PropertyFilter(unprefixed, value));
					}
				}
			}
		}
		return filters;
	}

	/**
	 * @param filterName �Ƚ������ַ���,�����ȽϵıȽ����͡�����ֵ���ͼ������б�. eg. LIKES_NAME_OR_LOGIN_NAME
	 * @param value ���Ƚϵ�ֵ.
	 */
	public PropertyFilter(final String filterName, final String value) {

		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		String matchTypeCode = StringUtils.substring(matchTypeStr, 0, matchTypeStr.length() - 1);
		String propertyTypeCode = StringUtils.substring(matchTypeStr, matchTypeStr.length() - 1, matchTypeStr.length());
		try {
			matchType = Enum.valueOf(MatchType.class, matchTypeCode);
		}
		catch (RuntimeException e) {
			throw new IllegalArgumentException("filter����" + filterName + "û�а������д,�޷��õ����ԱȽ�����.", e);
		}

		try {
			propertyType = Enum.valueOf(PropertyType.class, propertyTypeCode).getValue();
		}
		catch (RuntimeException e) {
			throw new IllegalArgumentException("filter����" + filterName + "û�а������д,�޷��õ�����ֵ����.", e);
		}

		String propertyNameStr = StringUtils.substringAfter(filterName, "_");
		propertyNames = StringUtils.split(propertyNameStr, PropertyFilter.OR_SEPARATOR);

		Assert.isTrue(propertyNames.length > 0, "filter����" + filterName + "û�а������д,�޷��õ���������.");
		// ��entity property�е����ͽ��ַ���ת��Ϊʵ������.
		this.propertyValue = ReflectionUtils.convertStringToObject(value, propertyType);
	}

	/**
	 * �Ƿ�Ƚ϶������.
	 */
	public boolean isMultiProperty() {
		return (propertyNames.length > 1);
	}

	/**
	 * ��ȡ�Ƚ����������б�.
	 */
	public String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * ��ȡΨһ�ıȽ���������.
	 */
	public String getPropertyName() {
		if (propertyNames.length > 1) {
			throw new IllegalArgumentException("There are not only one property");
		}
		return propertyNames[0];
	}

	/**
	 * ��ȡ�Ƚ�ֵ.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * ��ȡ�Ƚ�ֵ������.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * ��ȡ�ȽϷ�ʽ����.
	 */
	public MatchType getMatchType() {
		return matchType;
	}
}
