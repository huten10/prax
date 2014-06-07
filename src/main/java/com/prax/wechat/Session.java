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
 * 抽象会话 此会话声明周期在一个请求响应内。 通过继承类实现各种消息的处理方法
 * 
 * @author marker
 * */
public class Session {

	/** 时间格式化 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

	// 输入流
	private InputStream is;

	// 输出流
	private OutputStream os;

	/** Document构建类 */
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
		// 格式化工厂对象
		tffactory = TransformerFactory.newInstance();
	}

	/**
	 * 解析微信消息，并传递给对应方法
	 * 
	 * @param is 输入流
	 * @param os 输出流
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
		if (Message.MSG_TYPE_TEXT.equals(type)) {// 文本消息
			return new TextMessage(head);
		}
		else if (Message.MSG_TYPE_IMAGE.equals(type)) {// 图片消息
			return new ImageMessage(head);
		}
		else if (Message.MSG_TYPE_EVENT.equals(type)) {// 事件推送
			return new EventMessage(head);
		}
		else if (Message.MSG_TYPE_LINK.equals(type)) {// 链接消息
			return new LinkMessage(head);
		}
		else if (Message.MSG_TYPE_LOCATION.equals(type)) {// 地理位置消息
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
	 * 回传消息给微信服务器 只能再接收到微信服务器消息后，才能调用此方法
	 * 
	 * @param msg 消息对象（支持：文本、音乐、图文）
	 * */
	private void response(Message msg) {
		Document document = builder.newDocument();
		msg.write(document);
		try {
			Transformer transformer = tffactory.newTransformer();
			transformer.transform(new DOMSource(document), new StreamResult(new OutputStreamWriter(os, "utf-8")));
		}
		catch (Exception e) {
			e.printStackTrace();// 保存dom至目输出流
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
