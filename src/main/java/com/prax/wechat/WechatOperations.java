/**
 * 
 */
package com.prax.wechat;

import org.springframework.web.client.RestTemplate;

/**
 * @author Huanan
 * 
 */
public class WechatOperations {

	private static String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token";
	private static String USER_INFO_URI = "https://api.weixin.qq.com/cgi-bin/user/info?lang=zh_CN";

	private AccessToken accessToken;
	
	private String appId;
	private String secret;
	private String grantType = "client_credential";

	private RestTemplate restTemplate = new RestTemplate();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private AccessToken getAccessToken() {
		if (accessToken != null && !accessToken.isExpired()) {
			return accessToken;
		}
		String accessTokenUrl = ACCESS_TOKEN_URI;
		accessTokenUrl = accessTokenUrl + "?grant_type=" + grantType;
		accessTokenUrl = accessTokenUrl + "&appid=" + appId;
		accessTokenUrl = accessTokenUrl + "&secret=" + secret;

		accessToken = restTemplate.getForObject(accessTokenUrl, AccessToken.class);
		return accessToken;
	}

	public UserInfo getUserInfo(String openId) {
		String userInfoUri = USER_INFO_URI;
		userInfoUri = userInfoUri + "&access_token=" + getAccessToken().getValue();
		userInfoUri = userInfoUri + "&openid=" + openId;
		return restTemplate.getForObject(userInfoUri, UserInfo.class);
	}

}
