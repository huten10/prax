/**
 * 
 */
package com.prax.framework.boostrap;

import java.io.File;
import java.net.MalformedURLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.prax.framework.context.AppContext;
import com.prax.framework.util.ServletUtility;

/**
 * @author Huanan
 * 
 */
public class WebAppRootContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		// Nothing todo

	}

	public void contextInitialized(ServletContextEvent sce) {

		String webAppRoot = sce.getServletContext().getRealPath("/");
		// for archived war on JBoss and weblogic
		if (webAppRoot == null) {
			try {
				webAppRoot = sce.getServletContext().getResource("/").getPath();
			} catch (MalformedURLException e) {
				// Silently discarded
			}
		}

		if (webAppRoot != null
				&& webAppRoot.charAt(webAppRoot.length() - 1) != File.separator
						.charAt(0)) {
			webAppRoot = webAppRoot + File.separator;
		}

		(new ServletUtility()).setWebAppRoot(webAppRoot);
		
		AppContext.init(WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()));

	}
}