package com.prax.wechat.message;


/**
 * ΢��XML�ڵ�����
 * (����΢�ŵ���Ϣ��ʽ���ģ��ʲ��ýӿ�������ʽ�������Լ��Ӧ���)
 * @author marker
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 * */
public interface Elements {

	/*       ͨ�ù����ڵ�              */
	String ROOT = "xml";
	String TO_USER_NAME = "ToUserName";
	String FROM_USER_NAME = "FromUserName";
	String CREATE_TIME = "CreateTime";
	String MSG_TYPE = "MsgType";
	
	
	/* ��ϢID */
	String MSG_ID = "MsgId";
	
	
	/*       �ı���Ϣ�ڵ�              */
	String CONTENT = "Content";
	String FUNC_FLAG = "FuncFlag";
	
	
	
	String PIC_URL = "PicUrl";
	
	
	String TITLE = "Title";
	String DESCRITION = "Description";
	String URL = "Url";
	String MUSIC_URL = "MusicUrl";
	String HQ_MUSIC_URL = "HQMusicUrl";
	String MUSIC = "Music";
	
	/*       �¼���Ϣ�ڵ�              */
	String EVENT = "Event";
	String EVENT_KEY = "EventKey";
	String TICKET = "Ticket";
	String LATITUDE = "Latitude";// ����λ��γ��
	String LONGITUDE = "Longitude";// ����λ�þ���
	String PRECISION = "Precision";// ����λ�þ���
	
	
	/* ����λ����Ϣ */
	String LOCATION_X = "Location_X";
	String LOCATION_Y = "Location_Y";
	String SCALE = "Scale";
	String LABEL = "Label";
	
	
	
	String ARTICLE_COUNT = "ArticleCount";
	
	String ARTICLES = "Articles";
	
	String ITEM = "item";
	
	/* ����ʶ����Ϣ */
	String MEDIAID = "MediaId";
	String FORMAT = "Format";
	String RECOGNITION = "Recognition";
	
	// ��Ƶ����ͼ
	String THUMBMEDIAID = "ThumbMediaId";
	
	
	String IMAGE = "Image";
	
	String VOICE = "Voice";
	String VIDEO = "Video";
	
	
}
