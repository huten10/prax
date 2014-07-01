/**
 * 
 */
package com.prax.core.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.prax.core.user.entity.Profile;
import com.prax.core.user.entity.User;
import com.prax.core.user.service.UserService;
import com.prax.framework.base.action.BaseAction;
import com.prax.framework.base.action.BaseActionMapping;
import com.prax.framework.base.action.BindingActionForm;
import com.prax.framework.base.model.UCN;
import com.prax.framework.base.search.Page;
import com.prax.framework.base.search.PropertyFilter;
import com.prax.framework.context.Context;
import com.prax.wechat.UserInfo;
import com.prax.wechat.UserPage;
import com.prax.wechat.WechatOperations;

/**
 * @author Huanan
 * 
 */
public class UserAction extends BaseAction {

	private UserService userService;

	private WechatOperations operations;

	@Autowired
	@Qualifier("UserService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	@Qualifier("wechatOperations")
	public void setOperations(WechatOperations operations) {
		this.operations = operations;
	}

	protected boolean isHttpMethodAllowed(String httpMethod, String methodId) {
		if ("doSaveProfile".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doWelcome".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doLogin".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doRegister".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doProfile".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doList".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doEdit".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doDelete".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doSave".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doAdd".equalsIgnoreCase(methodId)) {
			return true;
		}
		else if ("doSyncUser".equalsIgnoreCase(methodId)) {
			return true;
		}
		return super.isHttpMethodAllowed(httpMethod, methodId);
	}

	public Object doLogin(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return SUCCESS;
	}

	public Object doRegister(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return SUCCESS;
	}

	public Object doWelcome(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		if (user.getExtenralId() != null
				&& (user.getProfile().getName() == null || user.getProfile().getName().length() == 0)) {
			response.sendRedirect("./register.do");
			return null;
		}
		return SUCCESS;
	}

	public Object doSaveProfile(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		User user = userService.get(currentUser.getUuid(), new String[] { "profile" });
		getBinder(user, bam, request);
		userService.update(user);

		currentUser.setProfile(user.getProfile());

		return SUCCESS;
	}

	public Object doProfile(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) auth.getPrincipal();
		User user = null;
		if (currentUser.getLogin().equalsIgnoreCase("admin")) {
			user = currentUser;
		}
		else {
			user = userService.get(currentUser.getUuid(), new String[] { "profile" });
		}
		request.setAttribute(bam.getDataAttribute(), user);

		getBinder(user, bam, request, (BindingActionForm) form);

		return SUCCESS;
	}

	public Object doList(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Page<User> page = new Page<User>(10);
		getBinder(page, bam, request);

		page.setFetchProps(new String[] { "profile" });
		page = userService.findPage(page, PropertyFilter.buildFilers(request));
		request.setAttribute(bam.getDataAttribute(), page);

		return SUCCESS;
	}

	public Object doSyncUser(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserPage data = operations.getUsers();
		UCN domain = Context.getCurrentDomain();
		for (String operId : data.getUsers()) {
			User user = userService.getByExtenralId(operId, new String[] { "profile" });
			if (user == null) {
				UserInfo userInfo = operations.getUserInfo(operId);
				user = new User();
				user.setDomainUuid(domain.getUuid());
				user.setExtenralId(userInfo.getOpenid());
				user.setLogin("WX_" + System.currentTimeMillis());
				user.setName(userInfo.getNickname());

				Profile profile = new Profile();
				user.setProfile(profile);
				profile.setDomainUuid(domain.getUuid());
				profile.setCity(userInfo.getCity());
				profile.setCountry(userInfo.getCountry());
				profile.setHeadImgUrl(userInfo.getHeadimgurl());
				profile.setLanguage(userInfo.getLanguage());
				profile.setNickName(userInfo.getNickname());
				profile.setOpenId(userInfo.getOpenid());
				profile.setProvince(userInfo.getProvince());
				profile.setSex(userInfo.getSex());
				profile.setSubscribeTime(userInfo.getSubscribe_time());
				profile.setSubscribe(userInfo.isSubscribe());

				user.getProfile().setSubscribe(Boolean.TRUE);
				userService.add(user);
			}
		}
		return SUCCESS;
	}

	public Object doEdit(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = userService.get(getUuid(request), new String[] { "profile" });
		request.setAttribute(bam.getDataAttribute(), user);

		getBinder(user, bam, request, (BindingActionForm) form);

		return SUCCESS;
	}

	public Object doAdd(BaseActionMapping bam, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		User user = new User();
		request.setAttribute(bam.getDataAttribute(), user);

		getBinder(user, bam, request, (BindingActionForm) form);

		return SUCCESS;
	}

	public Object doDelete(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		userService.delete(getUuid(request));
		return SUCCESS;
	}

	public Object doSave(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String uuid = getUuid(request);
		boolean isNew = uuid == null;
		User user = null;
		if (isNew)
			user = new User();
		else
			user = userService.get(getUuid(request), new String[] { "profile" });

		getBinder(user, bam, request);
		userService.add(user);

		return SUCCESS;
	}

	private String getUuid(HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		if (uuid == null || uuid.length() == 0)
			return null;
		else
			return uuid;
	}
}
