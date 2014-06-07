/**
 * 
 */
package com.prax.wechat.oauth2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.DefaultRedirectStrategy;

/**
 * @author Huanan
 * 
 */
public class WechatRedirectStrategy extends DefaultRedirectStrategy {
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response,
			String url) throws IOException {
		url = url.replace("client_id=", "appid=");
		super.sendRedirect(request, response, url+"#wechat_redirect");
	}
}