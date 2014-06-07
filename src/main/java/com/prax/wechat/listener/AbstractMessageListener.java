/**
 * 
 */
package com.prax.wechat.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.prax.core.user.service.UserService;
import com.prax.framework.util.ReflectionUtils;
import com.prax.wechat.MessageListener;
import com.prax.wechat.WechatOperations;
import com.prax.wechat.message.Message;

/**
 * @author Huanan
 * 
 */
public abstract class AbstractMessageListener<T extends Message> implements MessageListener<T> {

	protected WechatOperations operations;

	protected UserService userService;

	public void setOperations(WechatOperations operations) {
		this.operations = operations;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected Map<String, String> matchers = new HashMap<String, String>();

	public boolean support(Message message) {
		if (message == null)
			return false;
		boolean supportClass = message.getClass().equals(ReflectionUtils.getSuperClassGenricType(this.getClass()));
		if (!supportClass)
			return false;
		return match(message);
	}

	public void setMatchers(Map<String, String> matchers) {
		this.matchers = matchers;
	}

	private boolean match(Message message) {
		for (Entry<String, String> entry : matchers.entrySet()) {
			try {
				Object value = BeanUtils.getProperty(message, entry.getKey());
				String strVal = value == null ? "" : value.toString();
				String matcher = entry.getValue();
				if (!StringUtils.equals(matcher, strVal))
					return false;
			}
			catch (Exception e) {
				continue;
			}
		}
		return true;
	}

}
