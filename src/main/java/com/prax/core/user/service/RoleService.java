/**
 * 
 */
package com.prax.core.user.service;

import java.util.Collection;

import com.prax.core.user.entity.Role;
import com.prax.framework.base.service.Service;

/**
 * @author Huanan
 * 
 */
public interface RoleService extends Service<Role> {
	/**
	 * ��Ȩ�޵�ָ���Ľ�ɫ��
	 * 
	 * @param roleUuid
	 *            ָ�����޸ĵĽ�ɫ��uuid��������null��
	 * @param version
	 * @param permCode
	 *            ָ���������Ȩ�޵Ĵ��롣������null��
	 */
	public void assignPermission(String roleUuid, long version, String permCode);

	/**
	 * ������Ȩ�޵�ָ���Ľ�ɫ��
	 * 
	 * @param roleUuid
	 *            ָ�����޸ĵĽ�ɫ��uuid��������null��
	 * @param version
	 * @param permCodes
	 *            ָ���������Ȩ�޴��뼯�ϡ�������null����ָ����Ȩ�޴��벻���ڣ��������ԡ�
	 * 
	 * @throws BusinessException
	 */
	public void assignPermissions(String roleUuid, long version,
			Collection<String> permCodes);

	/**
	 * ��ָ���Ľ�ɫ��ɾ��Ȩ�ޡ�
	 * 
	 * @param roleUuid
	 *            ָ�����޸ĵĽ�ɫ��uuid��������null��
	 * @param version
	 * @param permCode
	 *            ָ����ɾ����Ȩ�޴��롣������null��
	 */
	public void removePermission(String roleUuid, long version, String permCode);

	/**
	 * ������ָ���Ľ�ɫ��ɾ��Ȩ�ޡ�
	 * 
	 * @param roleUuid
	 *            ָ�����޸ĵĽ�ɫ��uuid��������null��
	 * @param version
	 * @param permCodes
	 *            ָ����ɾ����Ȩ�޴��뼯�ϡ�������null����ָ����Ȩ�޴��벻���ڣ��������ԡ�
	 */
	public void removePermissions(String roleUuid, long version,
			Collection<String> permCodes);

	/**
	 * ����ɫ����ָ���û���
	 * 
	 * @param roleUuid
	 *            ָ������ӵĽ�ɫ��
	 * @param userUuid
	 *            ָ���û���
	 * @param userVersion
	 *            ָ���û���version��
	 */
	public void assignToUser(String roleUuid, String userUuid, long userVersion);

	/**
	 * ɾ��ָ���û���ӵ�еĽ�ɫ��
	 * 
	 * @param roleUuid
	 *            ָ����ɾ���Ľ�ɫ��
	 * @param userUuid
	 *            ָ���û���
	 * @param userVersion
	 *            ָ���û���version��
	 * @param operCtx
	 * @throws BusinessException
	 */
	public void removeFromUser(String roleUuid, String userUuid,
			long userVersion);

	/**
	 * ���ݴ���ȡ�ÿ��õĽ�ɫ����
	 * 
	 * @param domainUuid
	 *            ָ����ɫ�������uuid��
	 * @param code
	 *            ָ����ɫ�Ĵ��롣
	 * @param fetchProps
	 * @return ���ط��������Ľ�ɫ�������Ҳ����򷵻�null��
	 */
	public Role getByCode(String domainUuid, String code, String[] fetchProps);
}
