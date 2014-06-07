/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.wechat.message.Message;
import com.prax.wechat.message.TextMessage;
import com.prax.wechat.message.VoiceMessage;

/**
 * @author Huanan
 * 
 */
public class UserVoice extends AbstractMessageListener<VoiceMessage> {

	public Message onMessage(VoiceMessage msg) {
		TextMessage reMsg = new TextMessage();
		reMsg.setFromUserName(msg.getToUserName());
		reMsg.setToUserName(msg.getFromUserName());
		reMsg.setCreateTime(msg.getCreateTime());
		reMsg.setContent("识别结果: " +  msg.getRecognition());
		return reMsg;
	}

}
