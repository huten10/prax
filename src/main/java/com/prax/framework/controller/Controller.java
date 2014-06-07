/**
 * 
 */
package com.prax.framework.controller;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;

import com.prax.framework.boostrap.config.ConfigUtils;
import com.prax.framework.boostrap.constants.ConfigConstants;
import com.prax.framework.util.FileUtils;

/**
 * @author Huanan
 *
 */
public class Controller extends ActionServlet {
  /**
   * Comment for <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = 3617576007117058608L;

  /**
   * This method will add following non-core CSA configurations at run-time.
   * struts-config files, spring-configuration
   * These configuration will then get loaded for the non-core CSA services.
   * 
   * @throws ServletException
   * 
   */
  protected void initOther() throws ServletException {
    super.initOther();

    // add non-core configuration files to the path, so that these can be loaded at servlet init.
    try {
      String childDirectoriesForConfig[] = FileUtils.findDirectories(
          ConfigConstants.CONFIG_DIRECTORY, false);

      // find out the non-core struts configuration and spring configuration
      String nonCoreStrutsConfig = ConfigUtils.getNonCoreStrutsConfig(childDirectoriesForConfig);

      // append the non-core struts configuration and spring configuration found
      if ((null != nonCoreStrutsConfig) && (nonCoreStrutsConfig.trim().length() > 0)) {
        super.config = super.config + "," + nonCoreStrutsConfig;
      }
    } catch (Exception unknownException) {
      throw new ServletException(unknownException);
    }
  }
}
