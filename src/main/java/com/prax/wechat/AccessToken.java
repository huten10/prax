/**
 * 
 */
package com.prax.wechat;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Huanan
 */
@JsonDeserialize(using = AccessTokenDeserializer.class)
public class AccessToken {

	private static final long serialVersionUID = 914967629530462926L;

	public static String ACCESS_TOKEN = "access_token";

	public static String EXPIRES_IN = "expires_in";

	private String value;

	private Date expiration;

	public AccessToken(String value) {
		this.value = value;
	}

	private AccessToken() {

	}

	public AccessToken setValue(String value) {
		AccessToken result = new AccessToken();
		result.value = value;
		return result;
	}

	/**
	 * The token value.
	 * 
	 * @return The token value.
	 */
	public String getValue() {
		return value;
	}

	public int getExpiresIn() {
		return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
				.intValue() : 0;
	}

	protected void setExpiresIn(int delta) {
		setExpiration(new Date(System.currentTimeMillis() + delta));
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public boolean isExpired() {
		return expiration != null && expiration.before(new Date());
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

}
