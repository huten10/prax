package com.prax.wechat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WechatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String SHA_1 = "SHA-1";

	public static final String MD5 = "MD5";

	// ��΢��ƽ̨����ģʽ������
	private String token = "Q1w2e3r4";

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * ����΢�ŷ�������֤
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String echostr = request.getParameter("echostr");// ����ַ���
		boolean isValid = checkSignature(request);
		Writer out = response.getWriter();
		if (isValid) {
			out.write(echostr);// ������֤�ɹ������������
		}
		else {
			out.write("");
		}
		out.flush();
		out.close();
	}

	@SuppressWarnings("serial")
	private boolean checkSignature(HttpServletRequest request) {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");// ʱ���
		String nonce = request.getParameter("nonce");// �����
		if (signature == null || timestamp == null || nonce == null) {
			return false;
		}
		List<String> list = new ArrayList<String>(3) {
			public String toString() {
				return this.get(0) + this.get(1) + this.get(2);
			}
		};
		list.add(token);
		list.add(timestamp);
		list.add(nonce);
		Collections.sort(list);// ����

		String security = encode(list.toString(), SHA_1);// SHA-1����

		return signature.equals(security);
	}

	private String encode(String strSrc, String encodeType) {
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			if (encodeType == null || "".equals(encodeType))
				encodeType = MD5;// Ĭ��ʹ��MD5
			md = MessageDigest.getInstance(encodeType);
			md.update(bt);
			strDes = bytes2Hex(md.digest());
		}
		catch (NoSuchAlgorithmException e) {
			return strSrc;
		}
		return strDes;
	}

	private String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * ����΢�ŷ������������ĸ�����Ϣ���������ı���ͼƬ������λ�á����ֵȵ�
	 * 
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		boolean isValid = checkSignature(request);
		if (!isValid) {
			response.sendError(400);
			return;
		}

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();

		Session session = new Session();
		session.process(is, os);
	}

}
