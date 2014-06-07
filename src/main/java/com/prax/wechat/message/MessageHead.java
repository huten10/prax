package com.prax.wechat.message;
 
 

import java.util.Date; 
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.prax.wechat.Session;

/**
 * 微信消息头
 * 
 * @author marker
 * @version 1.0
 * */
public class MessageHead {
	// 开发者微信号
	private String toUserName;
	// 发送方帐号（一个OpenID）
	private String fromUserName;
	// 消息创建时间 （整型）
	private String createTime;
	// 消息类型：text\image\
	private String msgType;
	

	
	/**
	 * 一般由程序内部调用，开发者不用调用
	 * */
	public MessageHead() { 
		this.createTime = Session.DATE_FORMAT.format(new Date());//初始化创建时间
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
