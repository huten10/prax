package com.prax.core.user.service;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.prax.core.domain.service.DomainService;
import com.prax.core.user.entity.Role;
import com.prax.core.user.entity.User;
import com.prax.framework.base.model.BasicState;
import com.prax.framework.base.model.UCN;
import com.prax.framework.base.service.ServiceImpl;
import com.prax.framework.context.Context;
import com.prax.framework.util.Digests;

/**
 * @author Alvin.Hu
 * 
 */
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements UserService {

	public User getSysUser() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRootAdmin(User user) {
		return UserService.ROOTADMIN_LOGIN.equalsIgnoreCase(user.getLogin())
				&& DomainService.VALUE_ROOT_UUID.equals(user.getDomainUuid());
	}

	public User getByLogin(String domainUuid, String login, String[] fetchProps) {
		String hql = "from " + User.class.getName()
				+ " u where u.domainUuid = :domainUuid and upper(u.login) = :login and u.state <> :deleted";

		Query qry = getHibernateDao().createQuery(hql);
		qry.setParameter("domainUuid", domainUuid);
		qry.setParameter("login", login.toUpperCase());
		qry.setParameter("deleted", BasicState.DELETED);

		User user = (User) qry.uniqueResult();
		getHibernateDao().fetchProperties(user, fetchProps);
		return user;
	}

	public User getByLogin(String login, String[] fetchProps) {
		UCN domain = Context.getCurrentDomain();

		String hql = "from " + User.class.getName()
				+ " u where u.domainUuid = :domainUuid and upper(u.login) = :login and u.state <> :deleted";

		Query qry = getHibernateDao().createQuery(hql);
		qry.setParameter("domainUuid", domain.getUuid());
		qry.setParameter("login", login.toUpperCase());
		qry.setParameter("deleted", BasicState.DELETED);

		User user = (User) qry.uniqueResult();
		getHibernateDao().fetchProperties(user, fetchProps);
		return user;
	}

	public User getByExtenralId(String extenralId, String[] fetchProps) {
		UCN domain = Context.getCurrentDomain();

		String hql = "from " + User.class.getName()
				+ " u where u.domainUuid = :domainUuid and extenralId = :extenralId and u.state <> :deleted";

		Query qry = getHibernateDao().createQuery(hql);
		qry.setParameter("domainUuid", domain.getUuid());
		qry.setParameter("extenralId", extenralId);
		qry.setParameter("deleted", BasicState.DELETED);

		User user = (User) qry.uniqueResult();
		getHibernateDao().fetchProperties(user, fetchProps);
		return user;
	}

	public void add(User user) {
		checkLogin(user);
		entryptPassword(user);
		getHibernateDao().add(user);
	}

	public void update(User user) {
		checkLogin(user);
		entryptPassword(user);
		getHibernateDao().update(user);
	}

	private void entryptPassword(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			user.setPassword(Digests.md5(user.getPlainPassword()));
		}
	}

	private void checkLogin(User user) {
		if ("ADMIN".equalsIgnoreCase(user.getLogin())) {
			throw new RuntimeException("用户登录名(ADMIN)已经存在。");
		}
		if (!BasicState.DELETED.equals(user.getState())) {
			User user2 = getByLogin(user.getDomainUuid(), user.getLogin(), null);
			if (user2 != null && !user2.getUuid().equals(user.getUuid()))
				throw new RuntimeException("用户登录名(" + user.getLogin() + ")已经存在。");
		}
	}

	public void changePassword(String userUuid, String oldPassword, String newPassword) {

		User user = get(userUuid, null);
		if (user == null)
			throw new BadCredentialsException("用户不存在。");
		if (!ObjectUtils.equals(new Md5PasswordEncoder().encodePassword(oldPassword, null), user.getPassword()))
			throw new BadCredentialsException("密码验证不通过。");

		user.setPlainPassword(newPassword);
		update(user);
	}

	public void assignRoles(String userUuid, long userOca, Collection<String> roleUuids) {
		// TODO Auto-generated method stub

	}

	public void removeRoles(String userUuid, long userOca, Collection<String> roleUuids) {
		// TODO Auto-generated method stub

	}

	public List<Role> getRoles(String userUuid, String[] fetchProps) {
		// TODO Auto-generated method stub
		return null;
	}

	public void assignPermissions(String userUuid, long userOca, Collection<String> permCodes) {
		// TODO Auto-generated method stub

	}

	public void removePermissions(String userUuid, long userOca, Collection<String> permCodes) {
		// TODO Auto-generated method stub

	}

}
