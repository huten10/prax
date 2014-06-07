/**
 * 
 */
package com.prax.wechat.listener;

import com.prax.wechat.message.ImageMessage;
import com.prax.wechat.message.ImageTextMessage;
import com.prax.wechat.message.Message;
import com.prax.wechat.message.MessageItem;
import com.prax.wechat.message.TextMessage;

/**
 * @author Huanan
 * 
 */
public class UserCommond extends AbstractMessageListener<TextMessage> {

	public Message onMessage(TextMessage msg) {
		if ("1".equals(msg.getContent())) {// 菜单选项1
			TextMessage reMsg = new TextMessage();
			reMsg.setFromUserName(msg.getToUserName());
			reMsg.setToUserName(msg.getFromUserName());
			reMsg.setCreateTime(msg.getCreateTime());
			reMsg.setContent("【菜单】\n" + "1. 功能菜单\n" + "2. 图文消息测试\n" + "3. 图片消息测试\n");
			operations.getUserInfo(msg.getFromUserName());
			return reMsg;
		}
		else if ("2".equals(msg.getContent())) {
			// 回复一条消息
			MessageItem d1 = new MessageItem("登陆", "", null, "http://huten10.vicp.cc/prax/welcome.do");

			ImageTextMessage mit = new ImageTextMessage();
			mit.setFromUserName(msg.getToUserName());
			mit.setToUserName(msg.getFromUserName());
			mit.setCreateTime(msg.getCreateTime());
			mit.addItem(d1);

			return mit;
		}
		else if ("3".equals(msg.getContent())) {
			ImageMessage rmsg = new ImageMessage();
			rmsg.setFromUserName(msg.getToUserName());
			rmsg.setToUserName(msg.getFromUserName());
			rmsg.setPicUrl("http://www.yl-blog.com/template/ylblog/images/logo.png");

			return rmsg;
		}
		else {
			TextMessage reMsg = new TextMessage();
			reMsg.setFromUserName(msg.getToUserName());
			reMsg.setToUserName(msg.getFromUserName());
			reMsg.setCreateTime(msg.getCreateTime());

			reMsg.setContent("消息命令错误，谢谢您的支持！@wuweiit");

			return reMsg;
		}
	}

}
