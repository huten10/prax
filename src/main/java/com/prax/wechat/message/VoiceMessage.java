/**
 *  
 *  ��ΰ ��Ȩ����
 */
package com.prax.wechat.message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author marker
 * @date 2013-11-8 ����9:33:46
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class VoiceMessage extends Message{

	// ������Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ��ý��
	private String mediaId;
	// ������ʽ��amr
	private String format;
	// ����ʶ������UTF8����
	private String recognition;
	// ��Ϣid��64λ����
	private String msgId;
	
	
	/**
	 * Ĭ�Ϲ���
	 */
	public VoiceMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_VOICE);
	}
	
	
	public VoiceMessage(MessageHead head){
		this.head = head;
	}
	
	
	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element voiceElement = document.createElement(Elements.VOICE);
		Element mediaIdElement = document.createElement(Elements.MEDIAID);
		voiceElement.appendChild(mediaIdElement);
		root.appendChild(voiceElement); 
		document.appendChild(root); 
	}

	
	@Override
	public void read(Document document) {
		this.mediaId = getElementContent(document, Elements.MEDIAID);
		this.format = getElementContent(document, Elements.FORMAT);
		this.recognition = getElementContent(document, Elements.RECOGNITION);
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
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}


	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}


	/**
	 * @return the recognition
	 */
	public String getRecognition() {
		return recognition;
	}


	/**
	 * @param recognition the recognition to set
	 */
	public void setRecognition(String recognition) {
		this.recognition = recognition;
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
