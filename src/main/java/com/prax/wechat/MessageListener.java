package com.prax.wechat;

import com.prax.wechat.message.Message;

/**
 * Handle wechat message
 * */
public interface MessageListener<T extends Message> {

	/**
	 * �յ��ı���Ϣ
	 */
	Message onMessage(T message);

	/**
	 * �Ƿ��ܴ������Ϣ
	 * @param message
	 * @return
	 */
	boolean support(Message message);

}
