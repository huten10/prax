/**
 * 
 */
package com.prax.framework.base.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ForwardConfig;

import com.prax.framework.base.constants.StatusConstants;

/**
 * @author Huanan
 *
 */
public class BaseActionMapping extends ActionMapping implements
		StatusConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 885249398059410762L;

	private static final Pattern actionPattern = Pattern
			.compile(".*?(\\w+?)(Action)?$");

	private static final String LISTPAGEID_PREFIX = "MODULE_";

	private static final String SERVICEBEANNAME_SUFFIX = "Service";

	private static final String BASEPAGENAME_SUFFIX = "_";

	private static final String LISTPAGEATTRIBUTE_SUFFIX = "ListPage";

	private static final String DATAATTRIBUTE_SUFFIX = "Data";

	/**
	 * Base name used to guess other properties, guessed or injected by struts
	 * in struts-config.xml file.
	 */
	private String baseName;

	/**
	 * Name of the service bean, guessed or injected by struts in
	 * struts-config.xml file.
	 */
	private String serviceBeanName;

	/**
	 * Name of the list page ID, guessed or injected by struts in
	 * struts-config.xml file.
	 */
	private String listPageId;

	/**
	 * Name of the page, used for dictionary tags, if not injected by struts in
	 * struts-config.xml file, it is defined in the BaseAction's execute method
	 * by concatenating the basePageName and the methodId.
	 * 
	 * @see BaseAction#execute
	 */
	private String pageName;

	/**
	 * Base name used to generate the page name by concatenating the
	 * basePageName and the methodId in BaseAction, guessed or injected by
	 * struts in struts-config.xml file.
	 * 
	 * @see BaseAction#execute
	 */
	private String basePageName;

	/**
	 * Name of the request attribute which will contain the list page data and
	 * metadata, guessed or injected by struts in struts-config.xml file.
	 */
	private String listPageAttribute;

	/**
	 * Name of the request attribute that will contain the data, guessed or
	 * injected by struts in struts-config.xml file.
	 */
	private String dataAttribute;

	/**
	 * 
	 */
	public BaseActionMapping() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method will set the type property of this ActionMapping. It is
	 * called when the struts-config.xml file is loaded and that an action
	 * element's "type" attribute is encountered. The type contains the name of
	 * the Action class the mapping is refering to. In addition to this, other
	 * properties of the BaseActionMapping will be guessed based on the first
	 * part of the class name unless the baseName property is set.
	 * 
	 * For example, guesses for class
	 * com.sungard.cs.admin.dictionarytag.DictionaryTagAction: baseName =
	 * "DictionaryTag" serviceBeanName = "DictionaryTagService" listPageId =
	 * "DICTIONARYTAG_MODULE" listPageAttribute = "dictionaryTagListPage"
	 * dataAttribute = "dictionaryTagData" basePageName = DICTIONARYTAG_
	 * 
	 * The PAGE_NAME request attribute will be set in BaseAction by appending
	 * the BaseAction's methodId to basePageName UNLESS pageName is defined in
	 * struts-config.xml file (using set-property tag)
	 * 
	 * @see #actionPattern
	 * @see com.sungard.framework.base.action.BaseAction
	 */
	@Override
	public void setType(String t) {
		super.setType(t);
		// guess some of the fields based on the name of the Action class
		if (baseName == null) {
			Matcher m = actionPattern.matcher(t);
			if (!m.matches()) {
     			throw new IllegalArgumentException(String.format("Invalid type %s for mapping.", t));
			}
			baseName = m.group(1);
		}
		StringBuffer sb = new StringBuffer(baseName);
		sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
		String camelCaseBase = sb.toString();
		if (serviceBeanName == null)
			serviceBeanName = baseName + SERVICEBEANNAME_SUFFIX;
		if (listPageId == null)
			listPageId = LISTPAGEID_PREFIX + baseName.toUpperCase();
		if (basePageName == null)
			basePageName = baseName.toUpperCase() + BASEPAGENAME_SUFFIX;
		if (listPageAttribute == null)
			listPageAttribute = camelCaseBase + LISTPAGEATTRIBUTE_SUFFIX;
		if (dataAttribute == null)
			dataAttribute = camelCaseBase + DATAATTRIBUTE_SUFFIX;
	}

	public String getListPageId() {
		return listPageId;
	}

	public void setListPageId(String listPageId) {
		this.listPageId = listPageId;
	}

	public String getServiceBeanName() {
		return serviceBeanName;
	}

	public void setServiceBeanName(String serviceBeanName) {
		this.serviceBeanName = serviceBeanName;
	}


    /**
     * <p>Overrides the method from ActionMapping.
     * Because ExtendedAction uses this method and that it may call this method twice 
     * because of its "DEFAULT" forward feature, this method will not log a warning 
     * when the first call doesn't find the forward. 
     * The default implementation did log a warning which was filling log files.</p>
     *
     * @param name Logical name of the forwarding instance to be returned
     */
    public ActionForward findForward(String name) {

        ForwardConfig config = findForwardConfig(name);
        if (config == null) {
            config = getModuleConfig().findForwardConfig(name);
        }

        return ((ActionForward) config);

    }

    /**
	 * <p>
	 * Find and return the <code>ForwardConfig</code> instance defining how
	 * forwarding to the specified logical name should be handled. This is
	 * performed by checking local and then global configurations for the
	 * specified forwarding configuration. If no forwarding configuration can be
	 * found, return <code>null</code>.
	 * </p>
	 * 
	 * @param forwardName
	 *            Logical name of the forwarding instance to be returned
	 * @param methodId
	 *            The method id for the current request
	 * @return The local or global forward with the specified name.
	 * @see org.apache.struts.action.ActionMapping#findForward(java.lang.String)
	 */
	public ActionForward findForward(String forwardName, String methodId,
			HttpServletRequest request) {
		// Search in the local ActionForwards
		ForwardConfig config = findForwardConfig(forwardName);

		if (config == null) {
			// Search in the global ActionForwards
			config = getModuleConfig().findForwardConfig(forwardName);
		}

		if (config == null) {
			// try prefixing with the methodId
			String prefixed = new StringBuffer().append(methodId).append(':')
					.append(forwardName).toString();
			config = findForwardConfig(prefixed);
			if (config == null) {
				config = getModuleConfig().findForwardConfig(prefixed);
				if (config == null && !forwardName.equals(SUCCESS)
						&& forwardName.startsWith(SUCCESS)) {
					// if all else failed but the forwardName starts by
					// "SUCCESS"
					// (without being SUCCESS), try the local ActionForwards
					// with "SUCCESS"
					config = findForwardConfig(SUCCESS);
				}
			}
		}

		return ((ActionForward) config);
	}

	/**
	 * @return the dataAttribute
	 */
	public String getDataAttribute() {
		return dataAttribute;
	}

	/**
	 * @param dataAttribute
	 *            the dataAttribute to set
	 */
	public void setDataAttribute(String dataAttribute) {
		this.dataAttribute = dataAttribute;
	}

	/**
	 * @return the baseName
	 */
	public String getBaseName() {
		return baseName;
	}

	/**
	 * @param baseName
	 *            the baseName to set
	 */
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	/**
	 * @return the listPageAttribute
	 */
	public String getListPageAttribute() {
		return listPageAttribute;
	}

	/**
	 * @param listPageAttribute
	 *            the listPageAttribute to set
	 */
	public void setListPageAttribute(String listPageAttribute) {
		this.listPageAttribute = listPageAttribute;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * @return the basePageName
	 */
	public String getBasePageName() {
		return basePageName;
	}

	/**
	 * @param basePageName
	 *            the basePageName to set
	 */
	public void setBasePageName(String basePageName) {
		this.basePageName = basePageName;
	}

}
