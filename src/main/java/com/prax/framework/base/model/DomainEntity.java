/**
 * 
 */
package com.prax.framework.base.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Huanan
 * 
 */
@MappedSuperclass
public abstract class DomainEntity extends Versionable {

	private static final long serialVersionUID = 8728028321115639196L;

	private String domainUuid;

	/** À˘ Ù”Ú */
	@Column(name = "domainUuid", nullable = false, length = 38)
	public String getDomainUuid() {
		return domainUuid;
	}

	public void setDomainUuid(String domainUuid) {
		this.domainUuid = domainUuid;
	}
}
