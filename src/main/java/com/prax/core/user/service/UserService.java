/**
 * 
 */
package com.prax.core.user.service;

import java.util.Collection;
import java.util.List;

import com.prax.core.user.entity.Role;
import com.prax.core.user.entity.User;
import com.prax.framework.base.service.Service;

/**
 * @author Huanan
 * 
 */
public interface UserService extends Service<User> {
    /** ϵͳ�û���uuid�� */
    public static final String SYSUSER_UUID = "SYSUSER_UUID";
    /** ϵͳ�û��ĵ�¼���� */
    public static final String SYSUSER_LOGIN = "**SYSUSER**";
    /** ϵͳ�û���ȫ���� */
    public static final String SYSUSER_NAME = "ϵͳ�û�";
    
    /** �������Ա�û��ĵ�¼���� */
    public static final String ROOTADMIN_LOGIN = "admin";
    
    /**
     * ȡ��ϵͳ�û���
     * 
     * @return
     */
    public User getSysUser();
    
    /**
     * �ж�ָ�����û��Ƿ��Ǹ���Ĺ���Ա�û���
     * 
     * @param role
     *            ָ���û�������null����ʼ�շ���false��
     * @return
     */
    public boolean isRootAdmin(User user);
    
    /**
     * ���ݵ�¼��ȡ�ÿ��õ��û���
     * 
     * @param domainUuid
     *            ָ���û��������uuid��
     * @param login
     *            ָ���û��ĵ�¼����
     * @param fetchProps
     * @return ���ط����������û��������Ҳ����򷵻�null��
     */
    public User getByLogin(String domainUuid, String login, String[] fetchProps);
    
    /**
     * ���ݵ�¼��ȡ�õ�ǰ��Ŀ����û���
     * 
     * @param login
     *            ָ���û��ĵ�¼����
     * @param fetchProps
     * @return ���ط����������û��������Ҳ����򷵻�null��
     */
    public User getByLogin(String login, String[] fetchProps);
    
    /**
     * �����ⲿIDȡ�õ�ǰ��Ŀ����û���
     * 
     * @param login
     *            ָ���û��ĵ�¼����
     * @param fetchProps
     * @return ���ط����������û��������Ҳ����򷵻�null��
     */
    public User getByExtenralId(String extenralId, String[] fetchProps);
    
    /**
     * �޸��û����
     * 
     * @param userUuid
     *            ָ�����޸��û���uuid��������null��
     * @param oca
     * @param oldPassword
     *            ָ��ԭ���������null��
     * @param newPassword
     *            ָ���¿��������null��
     */
    public void changePassword(String userUuid, long oca, String oldPassword, String newPassword);
    
    /**
     * �����û�ָ���Ľ�ɫ��
     * 
     * @param userUuid
     *            ָ�����޸ĵ��û���uuid��������null��
     * @param userOca
     * @param roleUuids
     *            ָ��������Ľ�ɫ��uuid��������null��
     */
    public void assignRoles(String userUuid, long userOca, Collection<String> roleUuids);
    
    /**
     * ���û���ɾ���ѱ�����Ľ�ɫ��
     * 
     * @param userUuid
     *            ָ�����޸ĵ��û���uuid��������null��
     * @param userOca
     * @param roleUuids
     *            ָ����ɾ���Ľ�ɫ��uuid��������null��
     */
    public void removeRoles(String userUuid, long userOca, Collection<String> roleUuids);
    
    /**
     * ȡ���û���ӵ�е����н�ɫ��
     * 
     * @param userUuid
     *            ָ���û���uuid��
     * @return �������з��������Ľ�ɫ���ϣ���û�з��������򷵻ؿռ��ϡ�
     * @throws BusinessException
     */
    public List<Role> getRoles(String userUuid, String[] fetchProps);
    
    /**
     * �����û�ָ����Ȩ�޼��ϡ�<br>
     * 
     * @param userUuid
     *            ָ�����޸ĵ��û���uuid��������null��
     * @param userOca
     * @param permCodes
     *            ָ���������Ȩ�޴��뼯�ϡ�������null��
     * @param operCtx
     * @throws BusinessException
     */
    public void assignPermissions(String userUuid, long userOca, Collection<String> permCodes);
    
    /**
     * ɾ���û�ָ����Ȩ�޼��ϡ�<br>
     * 
     * @param userUuid
     *            ָ�����޸ĵ��û���uuid��������null��
     * @param userOca
     * @param permCodes
     *            ָ������ɾ����Ȩ�޴��뼯�ϡ�������null����ָ����Ȩ�޴��벻���ڣ��������ԡ�
     */
    public void removePermissions(String userUuid, long userOca, Collection<String> permCodes);
}
