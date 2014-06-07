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
 * ʵ��SpringSecurity��UserDetailsService�ӿ�,ʵ�ֻ�ȡ�û�Detail��Ϣ�Ļص�����.
 * 
 * @author calvin
 */
@Transactional(readOnly = true)
public class UserDetailServiceImpl implements UserDetailsService {

	private static User admin = new User();
	static {
		admin.setLogin("ADMIN");
		admin.setPassword("123456");
		admin.setName("����Ա");
		admin.setProfile(new Profile());
		admin.getProfile().setNickName("����Ա");
	}

	/**
	 * ��ȡ�û�Details��Ϣ�Ļص�����.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		if (!StringUtils.equalsIgnoreCase(admin.getLogin(), username)) {
			throw new UsernameNotFoundException("�û�" + username + " ������");
		}

		return admin;
	}

}
