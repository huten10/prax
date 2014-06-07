package com.prax.core.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.prax.core.user.entity.Profile;
import com.prax.core.user.entity.User;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailsService {

	private static User admin = new User();
	static {
		admin.setLogin("ADMIN");
		admin.setPassword("123456");
		admin.setName("管理员");
		admin.setProfile(new Profile());
		admin.getProfile().setNickName("管理员");
	}

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		if (!StringUtils.equalsIgnoreCase(admin.getLogin(), username)) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}

		return admin;
	}

}
