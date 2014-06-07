/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.wechat.message.EventMessage;
import com.prax.wechat.message.Message;
import com.prax.wechat.message.TextMessage;

/**
 * @author Huanan
 * 
 */
public class UserClick extends AbstractMessageListener<EventMessage> {

	public UserClick() {
		this.matchers.put("event", EventMessage.CLICK);
	}
	
	public Message onMessage(EventMessage msg) {
		TextMessage reMsg = new TextMessage();
		reMsg.setFromUserName(msg.getToUserName());
		reMsg.setToUserName(msg.getFromUserName());
		reMsg.setCreateTime(msg.getCreateTime());
		reMsg.setContent("¿ª·¢ÖÐ");
		return reMsg;
	}

}
