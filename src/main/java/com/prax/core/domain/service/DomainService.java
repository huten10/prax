/**
 * 
 */
package com.prax.core.domain.service;

import com.prax.core.domain.entity.Domain;
import com.prax.framework.base.service.Service;

/**
 * @author Huanan
 * 
 */
public interface DomainService extends Service<Domain> {

	/** 根域的uuid */
	public static final String VALUE_ROOT_UUID = "ROOT";
	/** 根域的代码。 */
	public static final String VALUE_ROOT_CODE = "ROOT";
	/** 根域的名称。 */
	public static final String VALUE_ROOT_NAME = "根域";

	/**
	 * 取得整个系统唯一的根域对象。在任何时候都可以被取得。
	 * 
	 * @return
	 */
	public Domain getRoot();
}
