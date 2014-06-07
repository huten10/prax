/**
 * 
 */
package com.prax.sample.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.prax.framework.base.action.BaseAction;
import com.prax.framework.base.action.BaseActionMapping;
import com.prax.framework.base.action.BindingActionForm;
import com.prax.framework.base.search.Page;
import com.prax.sample.user.entity.User;
import com.prax.sample.user.service.UserService;

/**
 * @author Huanan
 * 
 */
public class UserAction extends BaseAction {

    private UserService userService;

    @Autowired
    @Qualifier("SampleUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    protected boolean isHttpMethodAllowed(String httpMethod, String methodId) {
        if ("doDefault".equalsIgnoreCase(methodId)) {
            return true;
        } else if ("doList".equalsIgnoreCase(methodId)) {
            return true;
        } else if ("doEdit".equalsIgnoreCase(methodId)) {
            return true;
        } else if ("doDelete".equalsIgnoreCase(methodId)) {
            return true;
        } else if ("doSave".equalsIgnoreCase(methodId)) {
            return true;
        } else if ("doAdd".equalsIgnoreCase(methodId)) {
            return true;
        }
        return super.isHttpMethodAllowed(httpMethod, methodId);
    }

    public Object doDefault(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User user = new User();
        user.setLoginName("loginName_" + System.currentTimeMillis());
        userService.add(user);

        return SUCCESS;
    }

    public Object doList(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Page<User> page = new Page<User>(10);
        getBinder(page, bam, request);

        page = userService.findAll(page);
        request.setAttribute(bam.getDataAttribute(), page);

        return SUCCESS;
    }

    public Object doEdit(BaseActionMapping bam, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        User user = userService.get(getUuid(request), new String[] {
                "addresses", "profile.subProfile" });
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
            user = userService.get(getUuid(request), new String[] {
                    "addresses", "profile.subProfile" });

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
