/**
 * 
 */
package com.prax.wechat;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author Huanan
 * 
 */
@SuppressWarnings("serial")
public class AccessTokenDeserializer extends StdDeserializer<AccessToken> {

	protected AccessTokenDeserializer() {
		super(AccessToken.class);
	}

	@Override
	public AccessToken deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		String tokenValue = null;
		Long expiresIn = null;

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String name = jp.getCurrentName();
			jp.nextToken();
			if (AccessToken.ACCESS_TOKEN.equals(name)) {
				tokenValue = jp.getText();
			}
			else if (AccessToken.EXPIRES_IN.equals(name)) {
				expiresIn = jp.getLongValue();
			}
		}

		AccessToken accessToken = new AccessToken(tokenValue);
		if (expiresIn != null) {
			accessToken.setExpiration(new Date(System.currentTimeMillis() + (expiresIn * 1000)));
		}

		return accessToken;
	}

}
