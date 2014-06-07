package com.prax.wechat.message;

import org.w3c.dom.Document;


/**
 * �¼���Ϣ
 * ע�⣺ ����Ϣֻ����΢�ŷ��������͹���
 * @author marker
 * @version 1.0
 * */
public class EventMessage extends Message {
	
	/** ���� */
	public static final String SUBSCRIBE = "subscribe";
	/** ȡ������ */
	public static final String UNSUBSCRIBE = "unsubscribe";
	
	// �Զ���˵�����¼�
	
	public static final String CLICK = "CLICK";
	public static final String SCAN = "scan";// �û��ѹ�עʱ���¼�����
	public static final String LOCATION = "LOCATION";// �ϱ�����λ���¼�
	
	// �¼�����subscribe(����)��unsubscribe(ȡ������)��CLICK(�Զ���˵�����¼�)
	private String event;
	//�¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	private String eventKey;
	// ��ά���ticket����������ȡ��ά��ͼƬ
	private String ticket;
	
	private String latitude;//����λ��γ��
	private String longitude;//����λ�þ���
	private String precision;//����λ�þ���
	/**
	 * �����ڲ�����
	 * */
	public EventMessage(MessageHead head) {
		this.head = head;
	}


	/**
	 * ��Ϊ����Ϣ������΢�ŷ��������͸����ǵģ����ǲ��÷���΢�ŷ���������˲���ʵ��write
	 * */
	@Override
	public void write(Document document) { }
	
	
	@Override
	public void read(Document document) {
		this.event = getElementContent(document, Elements.EVENT);
		//�û�δ��עʱ�����й�ע����¼�����
		//�û��ѹ�עʱ���¼�����
		if(SUBSCRIBE.equals(this.event) || UNSUBSCRIBE.equals(this.event) || SCAN.equals(this.event)){
			this.eventKey = getElementContent(document, Elements.EVENT_KEY);
			this.ticket = getElementContent(document, Elements.TICKET);
		}else if(LOCATION.equals(this.event)){// �ϱ�����λ���¼�
			this.latitude = getElementContent(document, Elements.LATITUDE);
			this.longitude = getElementContent(document, Elements.LONGITUDE); 
			this.precision = getElementContent(document, Elements.PRECISION);
		}else if(CLICK.equals(this.event)){// �Զ���˵��¼�
			this.eventKey = getElementContent(document, Elements.EVENT_KEY);
		}
	}


	
	
	
	
	public String getEvent() {
		return event;
	}


	public void setEvent(String event) {
		this.event = event;
	}


	public String getEventKey() {
		return eventKey;
	}


	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}


	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}


	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}


	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}


	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	/**
	 * @return the precision
	 */
	public String getPrecision() {
		return precision;
	}


	/**
	 * @param precision the precision to set
	 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	
	
	
	
}
