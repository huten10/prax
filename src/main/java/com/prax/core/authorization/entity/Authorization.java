/**
 * 
 */
package com.prax.core.authorization.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.prax.framework.base.model.Versionable;

/**
 * @author Huanan
 * 
 */
@Entity
@Table(name = "PXAuthorization")
public class Authorization extends Versionable {

	private static final long serialVersionUID = -5677647693622859254L;

	private String code;
	private String name;
	private String note;

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
