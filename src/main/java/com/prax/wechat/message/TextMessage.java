package com.prax.wechat.message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

 


public class TextMessage extends Message {

	// 文本消息内容
	private String content;
	// 消息id，64位整型
	private String msgId;
	
	
	/**
	 * 默认构造
	 * 初始化head对象，主要由开发者调用
	 * */
	public TextMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_TEXT);// 设置消息类型
	}


	/**
	 * 此构造由程序内部接收微信服务器消息调用
	 * */
	public TextMessage(MessageHead head) {
		this.head = head;
	}

	
	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element contentElement = document.createElement(Elements.CONTENT);
		contentElement.setTextContent(this.content); 
		root.appendChild(contentElement); 
		document.appendChild(root);
	}

	
	@Override
	public void read(Document document) {
		this.content = document.getElementsByTagName(Elements.CONTENT).item(0).getTextContent();
		this.msgId = document.getElementsByTagName(Elements.MSG_ID).item(0).getTextContent();
	}
	
	
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
 

 
}
