package com.prax.core.user.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.prax.core.domain.service.DomainService;
import com.prax.core.user.entity.Role;
import com.prax.core.user.entity.User;
import com.prax.framework.base.model.BasicState;
import com.prax.framework.base.model.UCN;
import com.prax.framework.base.service.ServiceImpl;
import com.prax.framework.context.Context;

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

	public void changePassword(String userUuid, long oca, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub

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
