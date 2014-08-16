/**
 * 
 */
package com.prax.framework.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author Huanan
 * 
 */
public class Digests {
	public static String md5(String raw) {
		return new Md5PasswordEncoder().encodePassword(raw, null);
	}
}
