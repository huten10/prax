package com.prax.wechat.message;

/**
 * ͼ����Ϣ���� Ϊͼ����Ϣ�����ṩ����֧��
 */
public class MessageItem {

	// ͼ����Ϣ����
	private String title;

	// ͼ����Ϣ����
	private String description;

	// ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80��
	private String picUrl;

	// ���ͼ����Ϣ��ת����
	private String url;

	public MessageItem() {
	}

	public MessageItem(String title, String description, String picUrl, String url) {
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
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

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
