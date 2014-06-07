/**
 * 
 */
package com.prax.core.user.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.prax.core.authorization.entity.Authorization;
import com.prax.framework.base.model.DomainEntity;

/**
 * @author Huanan
 * 
 */
@Entity
@Table(name = "PXRoleAuthorization")
public class RoleAuthorization extends DomainEntity {

	private static final long serialVersionUID = 3920839057225406872L;

	private Role role;
	private Authorization authorization;

	/** ╫ги╚ */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleUuid", nullable = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/** х╗оч */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorizationUuid", nullable = false)
	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
}
