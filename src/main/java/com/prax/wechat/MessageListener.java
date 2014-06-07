package com.prax.wechat;

import com.prax.wechat.message.Message;

/**
 * Handle wechat message
 * */
public interface MessageListener<T extends Message> {

	/**
	 * 收到文本消息
	 */
	Message onMessage(T message);

	/**
	 * 是否能处理该消息
	 * @param message
	 * @return
	 */
	boolean support(Message message);

}
