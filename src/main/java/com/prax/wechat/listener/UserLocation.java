/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.wechat.message.LocationMessage;
import com.prax.wechat.message.Message;

/**
 * @author Huanan
 * 
 */
public class UserLocation extends AbstractMessageListener<LocationMessage> {

	public Message onMessage(LocationMessage msg) {
		System.out.println("收到地理位置消息：");
		System.out.println("X:" + msg.getLocation_X());
		System.out.println("Y:" + msg.getLocation_Y());
		System.out.println("Scale:" + msg.getScale());
		return null;
	}

}
