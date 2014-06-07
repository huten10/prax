package com.prax.wechat.message;

import org.w3c.dom.Document;


/**
 * ������Ϣ
 * @author marker
 * */
public class LinkMessage extends Message {

	//��Ϣ����
	private String title;	 
	//��Ϣ����
	private String description;	 
	//��Ϣ����
	private String url;	 
	//��Ϣid��64λ����
	private String msgId;
	
	
	/**
	 * �����ߵ��ô���ʵ��
	 * */
	public LinkMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_LINK);
	}
	
	/**
	 * ����������Ϣ���ô˹���
	 * @param head
	 */
	public LinkMessage(MessageHead head) {
		this.head = head; 
	}

	@Override
	public void write(Document document) { }
	
	@Override
	public void read(Document document) {
		this.title = document.getElementsByTagName(Elements.TITLE).item(0).getTextContent();
		this.description = document.getElementsByTagName(Elements.DESCRITION).item(0).getTextContent();
		this.url = document.getElementsByTagName(Elements.URL).item(0).getTextContent();
		this.msgId = document.getElementsByTagName(Elements.MSG_ID).item(0).getTextContent();
	} 
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
