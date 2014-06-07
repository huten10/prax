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
	 * 赋权限到指定的角色。
	 * 
	 * @param roleUuid
	 *            指定被修改的角色的uuid。不允许null。
	 * @param version
	 * @param permCode
	 *            指定被加入的权限的代码。不允许null。
	 */
	public void assignPermission(String roleUuid, long version, String permCode);

	/**
	 * 批量赋权限到指定的角色。
	 * 
	 * @param roleUuid
	 *            指定被修改的角色的uuid。不允许null。
	 * @param version
	 * @param permCodes
	 *            指定被加入的权限代码集合。不允许null。若指定的权限代码不存在，将被忽略。
	 * 
	 * @throws BusinessException
	 */
	public void assignPermissions(String roleUuid, long version,
			Collection<String> permCodes);

	/**
	 * 从指定的角色中删除权限。
	 * 
	 * @param roleUuid
	 *            指定被修改的角色的uuid。不允许null。
	 * @param version
	 * @param permCode
	 *            指定被删除的权限代码。不允许null。
	 */
	public void removePermission(String roleUuid, long version, String permCode);

	/**
	 * 批量从指定的角色中删除权限。
	 * 
	 * @param roleUuid
	 *            指定被修改的角色的uuid。不允许null。
	 * @param version
	 * @param permCodes
	 *            指定被删除的权限代码集合。不允许null。若指定的权限代码不存在，将被忽略。
	 */
	public void removePermissions(String roleUuid, long version,
			Collection<String> permCodes);

	/**
	 * 将角色赋予指定用户。
	 * 
	 * @param roleUuid
	 *            指定被添加的角色。
	 * @param userUuid
	 *            指定用户。
	 * @param userVersion
	 *            指定用户的version。
	 */
	public void assignToUser(String roleUuid, String userUuid, long userVersion);

	/**
	 * 删除指定用户所拥有的角色。
	 * 
	 * @param roleUuid
	 *            指定被删除的角色。
	 * @param userUuid
	 *            指定用户。
	 * @param userVersion
	 *            指定用户的version。
	 * @param operCtx
	 * @throws BusinessException
	 */
	public void removeFromUser(String roleUuid, String userUuid,
			long userVersion);

	/**
	 * 根据代码取得可用的角色对象。
	 * 
	 * @param domainUuid
	 *            指定角色所属域的uuid。
	 * @param code
	 *            指定角色的代码。
	 * @param fetchProps
	 * @return 返回符合条件的角色对象，若找不到则返回null。
	 */
	public Role getByCode(String domainUuid, String code, String[] fetchProps);
}
