package com.prax.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.prax.framework.context.AppContext;
import com.prax.wechat.message.EventMessage;
import com.prax.wechat.message.ImageMessage;
import com.prax.wechat.message.LinkMessage;
import com.prax.wechat.message.LocationMessage;
import com.prax.wechat.message.Message;
import com.prax.wechat.message.MessageHead;
import com.prax.wechat.message.TextMessage;
import com.prax.wechat.message.VideoMessage;
import com.prax.wechat.message.VoiceMessage;

/**
 * ����Ự �˻Ự����������һ��������Ӧ�ڡ� ͨ���̳���ʵ�ָ�����Ϣ�Ĵ�����
 * 
 * @author marker
 * */
public class Session {

	/** ʱ���ʽ�� */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	// ������
	private InputStream is;

	// �����
	private OutputStream os;

	/** Document������ */
	private static DocumentBuilder builder;

	private static TransformerFactory tffactory;

	static {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		// ��ʽ����������
		tffactory = TransformerFactory.newInstance();
	}

	/**
	 * ����΢����Ϣ�������ݸ���Ӧ����
	 * 
	 * @param is ������
	 * @param os �����
	 */
	public void process(InputStream is, OutputStream os) {
		this.os = os;
		try {
			Document document = builder.parse(is);
			MessageHead head = new MessageHead();
			head.read(document);

			Message message = createMessage(head);
			if (message != null) {
				message.read(document);

				Message response = onMessage(message);

				if (response != null) {
					response(response);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Message createMessage(MessageHead head) {
		String type = head.getMsgType();
		if (Message.MSG_TYPE_TEXT.equals(type)) {// �ı���Ϣ
			return new TextMessage(head);
		}
		else if (Message.MSG_TYPE_IMAGE.equals(type)) {// ͼƬ��Ϣ
			return new ImageMessage(head);
		}
		else if (Message.MSG_TYPE_EVENT.equals(type)) {// �¼�����
			return new EventMessage(head);
		}
		else if (Message.MSG_TYPE_LINK.equals(type)) {// ������Ϣ
			return new LinkMessage(head);
		}
		else if (Message.MSG_TYPE_LOCATION.equals(type)) {// ����λ����Ϣ
			return new LocationMessage(head);
		}
		else if (Message.MSG_TYPE_VOICE.equals(type)) {
			return new VoiceMessage(head);
		}
		else if (Message.MSG_TYPE_VIDEO.equals(type)) {
			return new VideoMessage(head);
		}
		return null;
	}

	/**
	 * �ش���Ϣ��΢�ŷ����� ֻ���ٽ��յ�΢�ŷ�������Ϣ�󣬲��ܵ��ô˷���
	 * 
	 * @param msg ��Ϣ����֧�֣��ı������֡�ͼ�ģ�
	 * */
	private void response(Message msg) {
		Document document = builder.newDocument();
		msg.write(document);
		try {
			Transformer transformer = tffactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(os, "utf-8")));
		}
		catch (Exception e) {
			e.printStackTrace();// ����dom��Ŀ�����
		}

		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(os);
	}

	@SuppressWarnings("unchecked")
	private Message onMessage(Message message) {
		Map<String, MessageListener> listeners = AppContext.getApplicationContext().getBeansOfType(
				MessageListener.class);
		for (MessageListener listener : listeners.values()) {
			if (listener.support(message)) {
				return listener.onMessage(message);
			}
		}
		return null;
	}
}
