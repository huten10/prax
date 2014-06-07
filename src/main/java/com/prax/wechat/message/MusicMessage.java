package com.prax.wechat.message;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * ������Ϣ(ֻ�ܻظ�)
 * @author marker
 * @date 2013-8-25 ����8:54:03
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class MusicMessage extends Message{
	// ����
	private String title;
	// ����
	private String description;
	// ��������
	private String musicUrl;
	// �������������ӣ�WIFI��������ʹ�ø����Ӳ�������
	private String hQMusicUrl;
	// ����ͼ��ý��id��ͨ���ϴ���ý���ļ����õ���id
	private String thumbMediaId;
	
	
	/**
	 * �����ߵ���
	 * */
	public MusicMessage() {
		this.head = new MessageHead();
		this.head.setMsgType(Message.MSG_TYPE_MUSIC);
	}
	
	
	
	@Override
	public void write(Document document) {
		Element root = document.createElement(Elements.ROOT);
		head.write(root, document);
		Element musicElement = document.createElement(Elements.MUSIC); 
		Element titleElement = document.createElement(Elements.TITLE);
		titleElement.setTextContent(this.title);
		Element descriptionElement = document.createElement(Elements.DESCRITION);
		descriptionElement.setTextContent(this.description);
		Element musicUrlElement = document.createElement(Elements.MUSIC_URL);
		musicUrlElement.setTextContent(this.musicUrl);
		Element hQMusicUrlElement = document.createElement(Elements.HQ_MUSIC_URL);
		hQMusicUrlElement.setTextContent(this.hQMusicUrl);
		Element thumbMediaIdElement = document.createElement(Elements.THUMBMEDIAID);
		thumbMediaIdElement.setTextContent(this.thumbMediaId);

		musicElement.appendChild(titleElement);
		musicElement.appendChild(descriptionElement);
		musicElement.appendChild(musicUrlElement);
		musicElement.appendChild(hQMusicUrlElement);
		musicElement.appendChild(thumbMediaIdElement);
		root.appendChild(musicElement);
		
		document.appendChild(root);
	}
	
	
	// ��Ϊ�û����ܷ���������Ϣ�����ǣ����û��ʵ��
	@Override
	public void read(Document document) { }
	
	
	
	
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
	public String getMusicUrl() {
		return musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public String getHQMusicUrl() {
		return hQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
	}



	/**
	 * @return the hQMusicUrl
	 */
	public String gethQMusicUrl() {
		return hQMusicUrl;
	}



	/**
	 * @param hQMusicUrl the hQMusicUrl to set
	 */
	public void sethQMusicUrl(String hQMusicUrl) {
		this.hQMusicUrl = hQMusicUrl;
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
	 
}
