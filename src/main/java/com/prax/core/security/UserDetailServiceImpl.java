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
 * ʵ��SpringSecurity��UserDetailsService�ӿ�,ʵ�ֻ�ȡ�û�Detail��Ϣ�Ļص�����.
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
		admin.setName("��������Ա");
		admin.setProfile(new Profile());
		admin.getProfile().setNickName("��������Ա");
		admin.setAdmin(true);
	}

	/**
	 * ��ȡ�û�Details��Ϣ�Ļص�����.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		User user = userService.getByLogin(username, new String[] { "profile" });
		if (user != null)
			return user;
		if (!StringUtils.equalsIgnoreCase(admin.getLogin(), username)) {
			throw new UsernameNotFoundException("�û�" + username + " ������");
		}

		return admin;
	}

}
