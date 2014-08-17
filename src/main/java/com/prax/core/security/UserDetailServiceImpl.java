package com.prax.core.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.prax.core.user.entity.Profile;
import com.prax.core.user.entity.User;
import com.prax.core.user.service.UserService;
import com.prax.framework.util.Digests;

/**
 * 实现SpringSecurity的UserDetailsService接口,实现获取用户Detail信息的回调函数.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailsService {

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private static User admin = new User();
	static {
		admin.setLogin("ADMIN");
		admin.setPassword(Digests.md5("123!@#"));
		admin.setName("超级管理员");
		admin.setProfile(new Profile());
		admin.getProfile().setNickName("超级管理员");
		admin.setAdmin(true);
	}

	/**
	 * 获取用户Details信息的回调函数.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = userService.getByLogin(username, new String[] { "profile" });
		if (user != null)
			return user;
		if (!StringUtils.equalsIgnoreCase(admin.getLogin(), username)) {
			throw new UsernameNotFoundException("用户" + username + " 不存在");
		}

		return admin;
	}

}
