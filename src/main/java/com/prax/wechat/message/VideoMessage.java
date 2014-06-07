package com.prax.wechat.message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * 视频消息
 * @author marker
 * @date 2013-8-25 上午8:54:03
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class VideoMessage extends Message{
	// 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String mediaId;
	// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String thumbMediaId;
	// 消息id，64位整型
	private String msgId;
 
	
	/**
	 * 开发者调用
	 * */
	public VideoMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_VIDEO);
	}
	
	
	
	/**
	 * @param head
	 */
	public VideoMessage(MessageHead head) {
		this.head = head;
	}



	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element videoElement = document.createElement(Elements.VIDEO);
		Element mediaIdElement = document.createElement(Elements.MEDIAID);
		Element thumbMediaIdElement = document.createElement(Elements.THUMBMEDIAID);
		videoElement.appendChild(mediaIdElement);
		videoElement.appendChild(thumbMediaIdElement);
		root.appendChild(videoElement); 
		document.appendChild(root);
	}
	
	
	// 因为用户不能发送音乐消息给我们，因此没有实现
	@Override
	public void read(Document document) {
		this.mediaId = getElementContent(document, Elements.MEDIAID);
		this.thumbMediaId = getElementContent(document, Elements.THUMBMEDIAID);
		this.msgId = getElementContent(document, Elements.MSG_ID);
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



	/**
	 * @return the thumbMediaId
	 */
	public String getThumbMediaId() {
		return thumbMediaId;
	}



	/**
	 * @param thumbMediaId the thumbMediaId to set
	 */
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}



	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}



	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
}