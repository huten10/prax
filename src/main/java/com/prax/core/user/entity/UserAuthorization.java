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
@Table(name = "PXUserAuthorization")
public class UserAuthorization extends DomainEntity {

	private static final long serialVersionUID = 9113452157937227063L;
	
	private User user;
	private Authorization authorization;

	/** 用户 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userUuid", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/** 权限 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorizationUuid", nullable = false)
	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
}
