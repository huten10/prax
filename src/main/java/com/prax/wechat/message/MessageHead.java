package com.prax.wechat.message;
 
 

import java.util.Date; 
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.prax.wechat.Session;

/**
 * ΢����Ϣͷ
 * 
 * @author marker
 * @version 1.0
 * */
public class MessageHead {
	// ������΢�ź�
	private String toUserName;
	// ���ͷ��ʺţ�һ��OpenID��
	private String fromUserName;
	// ��Ϣ����ʱ�� �����ͣ�
	private String createTime;
	// ��Ϣ���ͣ�text\image\
	private String msgType;
	

	
	/**
	 * һ���ɳ����ڲ����ã������߲��õ���
	 * */
	public MessageHead() { 
		this.createTime = Session.DATE_FORMAT.format(new Date());//��ʼ������ʱ��
	}

	public void write(Element root, Document document) {
		Element toUserNameElement = document
				.createElement(Elements.TO_USER_NAME);
		toUserNameElement.setTextContent(this.toUserName);
		Element fromUserNameElement = document
				.createElement(Elements.FROM_USER_NAME);
		fromUserNameElement.setTextContent(this.fromUserName);
		Element createTimeElement = document
				.createElement(Elements.CREATE_TIME);
		createTimeElement.setTextContent(this.createTime);
		Element msgTypeElement = document
				.createElement(Elements.MSG_TYPE);
		msgTypeElement.setTextContent(this.msgType);

		root.appendChild(toUserNameElement);
		root.appendChild(fromUserNameElement);
		root.appendChild(createTimeElement);
		root.appendChild(msgTypeElement);
	}

	public void read(Document document) {
		this.toUserName = document
				.getElementsByTagName(Elements.TO_USER_NAME).item(0)
				.getTextContent();
		this.fromUserName = document
				.getElementsByTagName(Elements.FROM_USER_NAME).item(0)
				.getTextContent();
		this.createTime = document
				.getElementsByTagName(Elements.CREATE_TIME).item(0)
				.getTextContent();
		this.msgType = document.getElementsByTagName(Elements.MSG_TYPE)
				.item(0).getTextContent();
		
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

 
	
}
