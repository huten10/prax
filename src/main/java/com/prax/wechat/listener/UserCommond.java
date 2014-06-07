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
		if ("1".equals(msg.getContent())) {// �˵�ѡ��1
			TextMessage reMsg = new TextMessage();
			reMsg.setFromUserName(msg.getToUserName());
			reMsg.setToUserName(msg.getFromUserName());
			reMsg.setCreateTime(msg.getCreateTime());
			reMsg.setContent("���˵���\n" + "1. ���ܲ˵�\n" + "2. ͼ����Ϣ����\n" + "3. ͼƬ��Ϣ����\n");
			operations.getUserInfo(msg.getFromUserName());
			return reMsg;
		}
		else if ("2".equals(msg.getContent())) {
			// �ظ�һ����Ϣ
			MessageItem d1 = new MessageItem("��½", "", null, "http://huten10.vicp.cc/prax/welcome.do");

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

			reMsg.setContent("��Ϣ�������лл����֧�֣�@wuweiit");

			return reMsg;
		}
	}

}
