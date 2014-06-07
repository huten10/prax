/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.wechat.message.EventMessage;
import com.prax.wechat.message.Message;

/**
 * @author Huanan
 * 
 */
public class UserUnSubscribe extends AbstractMessageListener<EventMessage> {

	public UserUnSubscribe() {
		this.matchers.put("event", EventMessage.UNSUBSCRIBE);
	}
	
	public Message onMessage(EventMessage message) {
		System.out.println("È¡Ïû¹Ø×¢£º" + message.getFromUserName());
		return null;
	}

}
