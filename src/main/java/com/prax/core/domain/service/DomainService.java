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

	/** �����uuid */
	public static final String VALUE_ROOT_UUID = "ROOT";
	/** ����Ĵ��롣 */
	public static final String VALUE_ROOT_CODE = "ROOT";
	/** ��������ơ� */
	public static final String VALUE_ROOT_NAME = "����";

	/**
	 * ȡ������ϵͳΨһ�ĸ���������κ�ʱ�򶼿��Ա�ȡ�á�
	 * 
	 * @return
	 */
	public Domain getRoot();
}
