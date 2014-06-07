package com.prax.wechat.message;

import org.w3c.dom.Document;


/**
 * ����λ����Ϣ��ֻ�ܽ��գ�
 * @author marker
 * @date 2013-8-25 ����8:53:57
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class LocationMessage extends Message {
	// ����λ��γ��
	private String location_X;
	// ����λ�þ���
	private String location_Y;
	// ��ͼ���Ŵ�С
	private String scale;
	// ����λ����Ϣ
	private String label;
	//��Ϣid��64λ����
	private String msgId;
	
	
	/**
	 * �����ߵ���
	 * */
	public LocationMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_LOCATION);
	}
	
	/**
	 * �����ڲ�����
	 * */
	public LocationMessage(MessageHead head) {
		this.head = head;
	}
	
	
	@Override
	public void write(Document document) { }
	
	
	@Override
	public void read(Document document) {
		this.location_X = document.getElementsByTagName(Elements.LOCATION_X).item(0).getTextContent();
		this.location_Y = document.getElementsByTagName(Elements.LOCATION_Y).item(0).getTextContent();
		this.scale = document.getElementsByTagName(Elements.SCALE).item(0).getTextContent();
		this.label = document.getElementsByTagName(Elements.LABEL).item(0).getTextContent();
		this.msgId = document.getElementsByTagName(Elements.MSG_ID).item(0).getTextContent();
	}
	
	
	
	
	
	public String getLocation_X() {
		return location_X;
	}
	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}
	public String getLocation_Y() {
		return location_Y;
	}
	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	
	 
}
