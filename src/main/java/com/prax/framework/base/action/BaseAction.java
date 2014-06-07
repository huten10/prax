/**
 * 
 */
package com.prax.framework.base.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.MultipartRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.struts.ActionSupport;
import org.springmodules.validation.valang.ValangValidator;
import org.springmodules.validation.valang.javascript.taglib.ValangValidateTag;

import com.prax.framework.base.constants.AttributeConstants;
import com.prax.framework.base.constants.StatusConstants;
import com.prax.framework.base.service.Service;

/**
 * @author Huanan
 * 
 */
@SuppressWarnings( { "deprecation", "unchecked" })
public class BaseAction extends ActionSupport implements AttributeConstants,
		StatusConstants {

	protected Logger mlLogger = LoggerFactory.getLogger(getClass());

	private static final String VALIDATOR_SUFFIX = "Validator";

	public static final String HTTP_METHOD_POST = "POST";

	public static final String HTTP_METHOD_GET = "GET";

	/**
	 * Value used as the method part of the path when it's not specified. This
	 * ends up calling the doDefault method of the BaseAction class.
	 * 
	 * @see com.sungard.framework.controller.CSARequestProcessor#processPath
	 */
	public static final String DEFAULT_PATH_METHOD = "default";

	/**
	 * The set of argument type classes for the reflected method call. These are
	 * the same for all calls, so calculate them only once.
	 */
	protected Class[] types = { BaseActionMapping.class, ActionForm.class,
			HttpServletRequest.class, HttpServletResponse.class };

	/**
	 * The set of Method objects we have introspected for this class, keyed by
	 * method name. This collection is populated as different methods are
	 * called, so that introspection needs to occur only once per method name.
	 */
	private HashMap<String, Method> methods = new HashMap<String, Method>();

	/**
	 * <p>
	 * Process the specified HTTP request, and create the corresponding HTTP
	 * response (or forward to another web component that will create it).
	 * Return an <code>ActionForward</code> instance describing where and how
	 * control should be forwarded, or <code>null</code> if the response has
	 * already been completed.
	 * </p>
	 * 
	 * <p>
	 * The ActionMapping passed must be of type BaseActionMapping or a
	 * ClassCastException will be thrown. This can be configured in the
	 * struts-config.xml file. You can change all ActionMapping type by using
	 * the "type" attribute of the "global-mappings" element or individually by
	 * using the "className" attribute of the "action element.
	 * </p>
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred, or
	 *         <code>null</code> if the response has been completed.
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BaseActionMapping bam = (BaseActionMapping) mapping;
		Object result;

		String methodId = getMethodId(bam, form, request, response);

		// Check if the requested methodId can be executed with the HTTP method
		// used in the request.
		// This method will usually be overridden by a subclass.
		if (!isHttpMethodAllowed(request.getMethod(), methodId)) {
			throw new HttpMethodNotAllowedException(String.format(
					"http method not allowed, path: %s", bam.getPath()));
		}

		exposeValidator(bam, methodId, request);

		// call the doXXX method for the method id received.
		result = dispatchMethod(mapping, form, request, response, methodId);

		// set the page name
		request.setAttribute(PAGE_NAME, getPageName(result, methodId, bam,
				form, request));
		ActionForward forward = findForwardOrRender(result, bam, request,
				response, methodId);

		if (forward == null && !response.isCommitted()) {
			throw new ServletException(String.format(
					"forward null, nothing written to outputstream, path: %s",
					bam.getPath()));

		}

		return forward;
	}

	/**
	 * Exposes the validator object to the JSP. Default implementation uses
	 * ValangValidator.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 */
	protected void exposeValidator(BaseActionMapping bam, String methodId,
			HttpServletRequest request) {
		try {
			ValangValidator validator = (ValangValidator) getBean(bam
					.getBaseName()
					+ VALIDATOR_SUFFIX);
			request.setAttribute(ValangValidateTag.VALANG_RULES_KEY_PREFIX
					+ methodId, validator.getRules());
		} catch (NoSuchBeanDefinitionException e) {
			// swallow; it means no validation is defined for this action which
			// can be acceptable
		}
	}

	/**
	 * Override this method if there is special processing necessary to define
	 * the page name that will be used for the dictionary tags (i18n). This
	 * method will throw a ClassCastException if the ActionMapping type isn't
	 * set to BaseActionMapping in the struts-config.xml file.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 */
	protected String getPageName(Object result, String methodId,
			BaseActionMapping bam, ActionForm form, HttpServletRequest request) {
		return bam.getPageName() != null ? bam.getPageName() : bam
				.getBasePageName()
				+ methodId.toUpperCase();
	}

	/**
	 * Checks if the HTTP method is allowed for the given action method id.
	 * 
	 * @param httpMethod
	 *            HTTP method as returned by HttpServletRequest's getMethod
	 * @param methodId
	 *            The action method id as returned by getMethodId
	 * @return true if allowed, false if not
	 * @see #getMethodId(ActionMapping, ActionForm, HttpServletRequest,
	 *      HttpServletResponse)
	 * @see javax.servlet.http.HttpServletRequest#getMethod()
	 */
	protected boolean isHttpMethodAllowed(String httpMethod, String methodId) {
		// be defensive, force subclasses to decide what method is allowed
		if (httpMethod.equals(HTTP_METHOD_POST))
			return true;
		else if (methodId.equals(DEFAULT_PATH_METHOD)
				&& httpMethod.equals(HTTP_METHOD_GET))
			return true;

		// PUT & DELETE not supported by BaseAction.
		return false;
	}

	/**
	 * Method which is dispatched to when there is no value for the
	 * ACTION_METHOD request attribute OR if the subclass doesn't implement
	 * CommandEnabled. Subclasses of <code>BaseAction</code> should override
	 * this method if they wish to provide default behavior different than
	 * throwing a ServletException.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this action instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred, or
	 *         <code>null</code> if the response has been completed. It can
	 *         either be an ActionForward or any other object for which the
	 *         toString() method will return the name of a forward.
	 * @throws Exception
	 *             if the application business logic throws an exception. The
	 *             default implementation returns a ServletException as it is an
	 *             application error to call this method without having
	 *             implemented it.
	 * @since CSA 2.4
	 */
	protected Object doDefault(BaseActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = "action needs default method";
		mlLogger.error(message);
		throw new ServletException(message);
	}

	/**
	 * Find forward associated to the specified result object
	 * 
	 * @param result
	 *            The result object. Normally returned by an action method. If
	 *            null, the method will also return null as this means something
	 *            as already been written in the response's output stream. If
	 *            not an ActionForward object, the object's toString method is
	 *            used to identify the forward to find.
	 * @param mapping
	 *            The ActionMapping used to select this Action instance.
	 * @param methodId
	 *            The method ID of the method that was called.
	 * @return The forward to which control should be transferred, or
	 *         <code>null</code> if the response has been completed.
	 * @throws IOException
	 * @since CSA 2.4
	 */
	protected ActionForward findForwardOrRender(Object result,
			BaseActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, String methodId) {
		if (result == null)
			return null;
		if (result instanceof ActionForward)
			return (ActionForward) result;
		// use request to get the "id" parameter if required
		ActionForward forward = mapping.findForward(result.toString(),
				methodId, request);
		if (forward == null)
			forward = getDefaultForward(mapping);

		return forward;

		// call renderer class passed and if error, return forward, if ok,
		// return null;
	}

	/**
	 * Extend this method if you which to provide a default ActionForward if
	 * none is found.
	 * 
	 * @param mapping
	 * @return Default ActionForward for this class
	 */
	protected ActionForward getDefaultForward(BaseActionMapping mapping) {
		return mapping.findForward(DEFAULT);
	}

	/**
	 * Dispatch to the specified method.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The non-HTTP request we are processing
	 * @param response
	 *            The non-HTTP response we are creating
	 * @param id
	 *            The name of the method to invoke
	 * @return The forward to which control should be transferred, or
	 *         <code>null</code> if the response has been completed.
	 * @throws Exception
	 *             if the application business logic throws an exception.
	 */
	protected Object dispatchMethod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response, String id)
			throws Exception {
		// Make sure we have a valid method name to call.
		// This may be null if the user hacks the query string.
		if (id == null) {
			return this.doDefault((BaseActionMapping) mapping, form, request,
					response);
		}

		// Identify the method object to be dispatched to
		Method method = null;

		method = getMethod(id);

		Object forward = null;

		try {
			Object[] args = { mapping, form, request, response };

			forward = method.invoke(this, args);
		} catch (InvocationTargetException e) {
			// Rethrow the target exception if possible so that the
			// exception handling machinery can deal with it
			Throwable t = e.getTargetException();

			if (t.getCause() instanceof ObjectRetrievalFailureException) {
				return SUCCESS_NOENTITYDATA;
			} else if (t instanceof TransactionSystemException) {
				if (((TransactionSystemException) t).getApplicationException() != null) {
					throw (RuntimeException) ((TransactionSystemException) t)
							.getApplicationException();
				}
			}
			if (t instanceof Exception) {
				throw ((Exception) t);
			} else {

				throw new ServletException(t);
			}
		}

		return forward;
	}

	/**
	 * Introspect the current class to identify a method for the specified name
	 * that accepts the same parameter types as the <code>execute</code> method
	 * does.
	 * 
	 * @param methodId
	 *            Id of the method to be introspected
	 * @return The method that matches the specified id (do[ID] method).
	 * @throws NoSuchMethodException
	 *             if no such method can be found
	 */
	private Method getMethod(String methodId) throws NoSuchMethodException {
		synchronized (methods) {
			Method method = (Method) methods.get(methodId);

			if (method == null) {
				method = getClass().getMethod(methodId, types);
				methods.put(methodId, method);
			}

			return (method);
		}
	}

	protected String getMethodId(BaseActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String path = mapping.getPath();
		String method = path.substring(path.lastIndexOf('/') + 1);
		method = method != null ? method : DEFAULT_PATH_METHOD;
		// convert method to doXXXX
		StringBuffer sb = new StringBuffer();
		sb.append("do").append(method).setCharAt(2,
				Character.toUpperCase(sb.charAt(2)));
		return sb.toString();
	}

	/**
	 * Delegate method.
	 * 
	 * @see org.springframework.web.context.WebApplicationContext#getBean(String)
	 */
	protected Object getBean(String name) {
		return getWebApplicationContext().getBean(name);
	}

	/**
	 * Delegate method.
	 * 
	 * @see org.springframework.web.context.WebApplicationContext#getBean(String,Class)
	 */
	protected Object getBean(String name, Class c) {
		return getWebApplicationContext().getBean(name, c);
	}

	/**
	 * Method that uses the serviceBeanName property of the BaseActionMapping to
	 * get the service bean from spring. A ClassCastException will be thrown if
	 * the action mapping isn't a BaseActionMapping
	 * 
	 * @param mapping
	 * @return Service implementation or null is no service is defined in the
	 *         mapping.
	 */
	protected Service getServiceFromMapping(BaseActionMapping bam) {
		return (Service) getBean(bam.getServiceBeanName(), Service.class);
	}

	private final CustomNumberEditor integerEditor = new CustomNumberEditor(
			Integer.class, true);
	private final CustomNumberEditor longEditor = new CustomNumberEditor(
			Long.class, true);
	private final CustomNumberEditor doubleEditor = new CustomNumberEditor(
			Double.class, true);
	private final StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(
			true);
	private final CustomDateEditor customDateEditor = new CustomDateEditor(
			new SimpleDateFormat("yyyy-MM-dd"), true);

	/**
	 * Allows setting up the DataBinder before binding occurs.
	 * 
	 * @param binder
	 *            The DataBinder instance to setup
	 */
	protected void setupBinder(DataBinder binder) {
		binder.registerCustomEditor(Integer.class, integerEditor);
		binder.registerCustomEditor(Long.class, longEditor);
		binder.registerCustomEditor(Double.class, doubleEditor);
		binder.registerCustomEditor(String.class, stringTrimmerEditor);
		binder.registerCustomEditor(Date.class, customDateEditor);
	}

	/**
	 * Method that returns a DataBinder for a bean and optionally binds with an
	 * HttpServletRequest object.
	 * 
	 * @param bean
	 *            Bean to base DataBinder on.
	 * @param bam
	 *            BaseActionMapping to allow configuration based on current
	 *            mapping.
	 * @param request
	 *            Optional request to bind with
	 * @return a new DataBinder
	 * @throws ServiceValidationException
	 *             if errors are found while binding.
	 */
	protected DataBinder getBinder(Object bean, BaseActionMapping bam,
			HttpServletRequest request) throws BindException {
		ServletRequestDataBinder binder = new ServletRequestDataBinder(bean,
				bam.getBaseName());
		setupBinder(binder);

		// Nothing to bind with, do not bind
		if (request != null) {
			if (request instanceof MultipartRequestWrapper) {
				binder.bind((((MultipartRequestWrapper) request).getRequest()));
			} else
				binder.bind(request);

			if (binder.getBindingResult().hasErrors()) {
				throw new BindException(binder.getBindingResult());
			}
		}

		return binder;
	}

	/**
	 * Method that returns a DataBinder for a bean.
	 * 
	 * @param bean
	 *            Bean to base DataBinder on.
	 * @param bam
	 *            BaseActionMapping to allow configuration based on current
	 *            mapping.
	 * @return a new DataBinder
	 * @throws ServiceValidationException
	 *             if errors are found while binding.
	 */
	protected DataBinder getBinder(Object bean, BaseActionMapping bam)
			throws BindException {
		return getBinder(bean, bam, null);
	}

	/**
	 * Method that returns a DataBinder for a bean, optionally binds with an
	 * HttpServletRequest object and exposes the errors to struts.
	 * 
	 * @param bean
	 *            Bean to base DataBinder on.
	 * @param bam
	 *            BaseActionMapping to allow configuration based on current
	 *            mapping.
	 * @param request
	 *            Request to bind with
	 * @param form
	 *            CSABindingActionForm to allow exposing errors to struts.
	 * @return a new DataBinder
	 * @throws ServiceValidationException
	 *             if errors are found while binding.
	 */
	protected DataBinder getBinder(Object bean, BaseActionMapping bam,
			HttpServletRequest request, BindingActionForm form)
			throws BindException {
		DataBinder binder = getBinder(bean, bam, request);
		form.expose(binder.getBindingResult(), request);
		return binder;
	}

	/**
	 * Utility method to get a cookie by name. It is possible to receive more
	 * than one cookie with the same name when the cookies were originally set
	 * with different paths. Be aware of this when using this method.
	 * 
	 * @param request
	 * @param name
	 * @return the first cookie with that name, null if no cookie is found.
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals(name))
				return cookies[i];
		}
		return null;
	}

	/**
	 * Utility method to get a cookie's value by name. It is possible to receive
	 * more than one cookie with the same name when the cookies were originally
	 * set with different paths. Be aware of this when using this method.
	 * 
	 * @param request
	 * @param name
	 * @return the value of the first cookie with that name, null if no cookie
	 *         is found.
	 */
	final public static String getCookieValue(HttpServletRequest request,
			String name) {
		Cookie found = getCookie(request, name);
		return found != null ? found.getValue() : null;
	}
}
