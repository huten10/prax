package com.prax.wechat.message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 图片消息
 * @author marker
 * @date 2013-8-25 上午8:53:37
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class ImageMessage extends Message{

	// 图片链接
	private String picUrl;
	// 消息id，64位整型
	private String msgId; 
	// 图片消息媒体id
	private String mediaId;
	
	
	/**
	 * 开发者调用
	 * */
	public ImageMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_IMAGE);//设置消息类型
	}
	
	
	/**
	 * 程序内部调用
	 * */
	public ImageMessage(MessageHead head) {
		this.head = head;
	}


	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element imageElement = document.createElement(Elements.IMAGE); 
		Element mediaIdElement = document.createElement(Elements.MEDIAID);
		imageElement.appendChild(mediaIdElement);
		root.appendChild(imageElement);
		document.appendChild(root);
	}
	
	
	@Override
	public void read(Document document) {
		this.picUrl = document.getElementsByTagName(Elements.PIC_URL).item(0).getTextContent();
		this.mediaId = getElementContent(document, Elements.MEDIAID);
		this.msgId   = document.getElementsByTagName(Elements.MSG_ID).item(0).getTextContent();
	}


	public String getPicUrl() {
		return picUrl;
	}


	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return mediaId;
	}


	/**
	 * @param mediaId the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	
}
