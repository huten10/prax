/**
 * 
 */
package com.prax.core.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.prax.framework.base.model.BasicState;
import com.prax.framework.base.model.DomainEntity;

/**
 * @author Huanan
 * 
 */
@Entity
@Table(name = "PXRole")
public class Role extends DomainEntity {
	private static final long serialVersionUID = -1973491616893301762L;
	private BasicState state = BasicState.USING;
	private String code;
	private String name;
	private String note;

	/** ×´Ì¬ */
	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	public BasicState getState() {
		return state;
	}

	public void setState(BasicState state) {
		this.state = state;
	}

	/** ´úÂë */
	@Column(name = "code", nullable = false, length = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/** Ãû³Æ */
	@Column(name = "name", nullable = true, length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/** ±¸×¢ */
	@Column(name = "note", nullable = true, length = 200)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
