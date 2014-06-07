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
    /** 系统用户的uuid。 */
    public static final String SYSUSER_UUID = "SYSUSER_UUID";
    /** 系统用户的登录名。 */
    public static final String SYSUSER_LOGIN = "**SYSUSER**";
    /** 系统用户的全名。 */
    public static final String SYSUSER_NAME = "系统用户";
    
    /** 根域管理员用户的登录名。 */
    public static final String ROOTADMIN_LOGIN = "admin";
    
    /**
     * 取得系统用户。
     * 
     * @return
     */
    public User getSysUser();
    
    /**
     * 判断指定的用户是否是根域的管理员用户。
     * 
     * @param role
     *            指定用户。允许null，将始终返回false。
     * @return
     */
    public boolean isRootAdmin(User user);
    
    /**
     * 根据登录名取得可用的用户。
     * 
     * @param domainUuid
     *            指定用户所属域的uuid。
     * @param login
     *            指定用户的登录名。
     * @param fetchProps
     * @return 返回符合条件的用户对象，若找不到则返回null。
     */
    public User getByLogin(String domainUuid, String login, String[] fetchProps);
    
    /**
     * 根据登录名取得当前域的可用用户。
     * 
     * @param login
     *            指定用户的登录名。
     * @param fetchProps
     * @return 返回符合条件的用户对象，若找不到则返回null。
     */
    public User getByLogin(String login, String[] fetchProps);
    
    /**
     * 根据外部ID取得当前域的可用用户。
     * 
     * @param login
     *            指定用户的登录名。
     * @param fetchProps
     * @return 返回符合条件的用户对象，若找不到则返回null。
     */
    public User getByExtenralId(String extenralId, String[] fetchProps);
    
    /**
     * 修改用户口令。
     * 
     * @param userUuid
     *            指定被修改用户的uuid。不允许null。
     * @param oca
     * @param oldPassword
     *            指定原口令。不允许null。
     * @param newPassword
     *            指定新口令。不允许null。
     */
    public void changePassword(String userUuid, long oca, String oldPassword, String newPassword);
    
    /**
     * 赋予用户指定的角色。
     * 
     * @param userUuid
     *            指定被修改的用户的uuid。不允许null。
     * @param userOca
     * @param roleUuids
     *            指定被赋予的角色的uuid。不允许null。
     */
    public void assignRoles(String userUuid, long userOca, Collection<String> roleUuids);
    
    /**
     * 从用户中删除已被赋予的角色。
     * 
     * @param userUuid
     *            指定被修改的用户的uuid。不允许null。
     * @param userOca
     * @param roleUuids
     *            指定被删除的角色的uuid。不允许null。
     */
    public void removeRoles(String userUuid, long userOca, Collection<String> roleUuids);
    
    /**
     * 取得用户所拥有的所有角色。
     * 
     * @param userUuid
     *            指定用户的uuid。
     * @return 返回所有符合条件的角色集合，若没有符合条件则返回空集合。
     * @throws BusinessException
     */
    public List<Role> getRoles(String userUuid, String[] fetchProps);
    
    /**
     * 赋予用户指定的权限集合。<br>
     * 
     * @param userUuid
     *            指定被修改的用户的uuid。不允许null。
     * @param userOca
     * @param permCodes
     *            指定被赋予的权限代码集合。不允许null。
     * @param operCtx
     * @throws BusinessException
     */
    public void assignPermissions(String userUuid, long userOca, Collection<String> permCodes);
    
    /**
     * 删除用户指定的权限集合。<br>
     * 
     * @param userUuid
     *            指定被修改的用户的uuid。不允许null。
     * @param userOca
     * @param permCodes
     *            指定将被删除的权限代码集合。不允许null。若指定的权限代码不存在，将被忽略。
     */
    public void removePermissions(String userUuid, long userOca, Collection<String> permCodes);
}
