package com.prax.wechat.message;

import org.w3c.dom.Document;

/**
 * ������Ϣ�� �ṩ������Ϣ�����ֶΡ�ͷ����Ϣ�����Լ�д��Ͷ�ȡ���󷽷�
 * 
 * */
public abstract class Message {

	/** �ı���Ϣ */
	public static final String MSG_TYPE_TEXT = "text";
	/** ͼƬ��Ϣ */
	public static final String MSG_TYPE_IMAGE = "image";
	/** ������Ϣ */
	public static final String MSG_TYPE_MUSIC = "music";
	/** ����λ����Ϣ */
	public static final String MSG_TYPE_LOCATION = "location";
	/** ������Ϣ */
	public static final String MSG_TYPE_LINK = "link";
	/** ͼ����Ϣ */
	public static final String MSG_TYPE_IMAGE_TEXT = "news";
	/** �¼���Ϣ */
	public static final String MSG_TYPE_EVENT = "event";
	/** ����ʶ����Ϣ */
	public static final String MSG_TYPE_VOICE = "voice";
	/** ��Ƶ��Ϣ */
	public static final String MSG_TYPE_VIDEO = "video";
	/** ��Ϣͷ */
	protected MessageHead head;

	/**
	 * д����Ϣ����
	 * 
	 * @param document
	 */
	public abstract void write(Document document);

	/**
	 * ��ȡ��Ϣ����
	 * 
	 * @param document
	 */
	public abstract void read(Document document);

	/**
	 * ��ȡ�ڵ��ı�����
	 * 
	 * @param document
	 *            �ĵ�
	 * @param element
	 *            �ڵ�����
	 * @return ����
	 */
	protected String getElementContent(Document document, String element) {
		if (document.getElementsByTagName(element).getLength() > 0) {// �ж��Ƿ��нڵ�
			return document.getElementsByTagName(element).item(0)
					.getTextContent();
		} else {
			return null;
		}
	}

	public MessageHead getHead() {
		return head;
	}

	public void setHead(MessageHead head) {
		this.head = head;
	}

	public String getToUserName() {
		return head.getToUserName();
	}

	public void setToUserName(String toUserName) {
		head.setToUserName(toUserName);
	}

	public String getFromUserName() {
		return head.getFromUserName();
	}

	public void setFromUserName(String fromUserName) {
		head.setFromUserName(fromUserName);
	}

	public String getCreateTime() {
		return head.getCreateTime();
	}

	public void setCreateTime(String createTime) {
		head.setCreateTime(createTime);
	}

}
