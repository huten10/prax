/**
 * 
 */
package com.prax.wechat.oauth2;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.auth.ClientAuthenticationHandler;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * @author Huanan
 * 
 */
public class WechatClientAuthenticateHandler implements
		ClientAuthenticationHandler {

	public void authenticateTokenRequest(
			OAuth2ProtectedResourceDetails resource,
			MultiValueMap<String, String> form, HttpHeaders headers) {
		if (resource.isAuthenticationRequired()) {

			String clientSecret = resource.getClientSecret();
			clientSecret = clientSecret == null ? "" : clientSecret;
			form.set("appid", resource.getClientId());
			if (StringUtils.hasText(clientSecret)) {
				form.set("secret", clientSecret);
			}
		}
	}
}