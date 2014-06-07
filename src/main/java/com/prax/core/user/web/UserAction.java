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

import com.prax.core.user.entity.User;
import com.prax.core.user.service.UserService;
import com.prax.framework.base.action.BaseAction;
import com.prax.framework.base.action.BaseActionMapping;
import com.prax.framework.base.action.BindingActionForm;

/**
 * @author Huanan
 * 
 */
public class UserAction extends BaseAction {

	private UserService userService;

	@Autowired
	@Qualifier("UserService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected boolean isHttpMethodAllowed(String httpMethod, String methodId) {
		if ("doSave".equalsIgnoreCase(methodId)) {
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

	public Object doSave(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
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
		User user = userService.get(currentUser.getUuid(), new String[] { "profile" });
        request.setAttribute(bam.getDataAttribute(), user);

        getBinder(user, bam, request, (BindingActionForm) form);

        return SUCCESS;
    }
}
