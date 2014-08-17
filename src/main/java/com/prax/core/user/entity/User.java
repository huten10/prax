/**
 * 
 */
package com.prax.core.user.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Sets;
import com.prax.framework.base.model.BasicState;
import com.prax.framework.base.model.DomainEntity;

/**
 * @author Huanan
 * 
 */
@Entity
@Table(name = "PXUser")
public class User extends DomainEntity implements UserDetails {
	private static final long serialVersionUID = 1076643484085903623L;

	private BasicState state = BasicState.USING;

	private String extenralId;

	private String login;

	private String name;

	private String password;

	private String plainPassword;

	private Boolean admin = Boolean.FALSE;

	private Boolean online = Boolean.TRUE;

	private Date validFrom;

	private Date validTo;

	private String note;

	private Profile profile;

	/** 状态 */
	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	public BasicState getState() {
		return state;
	}

	public void setState(BasicState state) {
		this.state = state;
	}

	/** 外部ID。 */
	@Column(name = "extenralId", length = 50)
	public String getExtenralId() {
		return extenralId;
	}

	public void setExtenralId(String extenralId) {
		this.extenralId = extenralId;
	}

	/** 登录名。大小写不敏感。 */
	@Column(name = "FLogin", nullable = false, length = 50)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	/** 全名 */
	@Column(name = "name", nullable = true, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** 口令 */
	@Column(name = "FPassword", nullable = true, length = 255)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	/** 启用 */
	@Column(name = "admin", nullable = false)
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	/** 启用 */
	@Column(name = "FOnline", nullable = true)
	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	/** 有效期从 */
	@Column(name = "validFrom", nullable = true)
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	/** 有效期到 */
	@Column(name = "validTo", nullable = true)
	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

	/** 备注 */
	@Column(name = "note", nullable = true, length = 200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/** 资料 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profileUuid", nullable = false)
	@Cascade(CascadeType.ALL)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = Sets.newHashSet();
		authorities.add(new SimpleGrantedAuthority("USER"));
		if (!StringUtils.isBlank(extenralId)) {
			authorities.add(new SimpleGrantedAuthority("WECHAT_USER"));
		}
		
		if(getAdmin() != null && getAdmin()){
			authorities.add(new SimpleGrantedAuthority("ADMIN"));
		}
		if ("ADMIN".equalsIgnoreCase(login)) {
			authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN"));
		}
		return authorities;
	}

	@Transient
	public String getUsername() {
		return getLogin();
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	public boolean isEnabled() {
		return getOnline();
	}

}
