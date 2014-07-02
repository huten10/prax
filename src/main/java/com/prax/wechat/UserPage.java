/**
 * 
 */
package com.prax.wechat;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Huanan
 * 
 */
@JsonIgnoreProperties
public class UserPage implements Serializable {

	private static final long serialVersionUID = 8613402808212130404L;

	private long total;

	private long count;

	private HashMap data;

	private String next_openid;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public HashMap getData() {
		return data;
	}

	public void setData(HashMap data) {
		this.data = data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	public void setNext_openid(String nextOpenid) {
		next_openid = nextOpenid;
	}

	public List<String> getUsers() {
		if (data != null) {
			List<String> users = (List<String>) data.get("openid");
			if (users != null) {
				return users;
			}
		}
		return Collections.emptyList();
	}

}
