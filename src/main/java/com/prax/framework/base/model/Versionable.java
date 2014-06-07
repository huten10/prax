/**
 * 
 */
package com.prax.framework.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Version;

/**
 * @author Huanan
 * 
 */
public abstract class Versionable extends Persistent {
	private static final long serialVersionUID = 883154778151587920L;

	private long version;
	private Date lastModified;
	private String lastModifier;
	private Date created;
	private String creator;

	/** Version */
	@Version
	@Column(name = "version", nullable = false)
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	/** 最后修改时间 */
	@Column(name = "lastModified", nullable = false)
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Column(name = "lastModifier", nullable = false, length = 40)
	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	/** 创建时间 */
	@Column(name = "created", nullable = true)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/** 创建人 */
	@Column(name = "creator", nullable = true, length = 40)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
