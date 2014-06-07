/**
 * 
 */
package com.prax.framework.boostrap.config;

import javax.servlet.ServletException;

import com.prax.framework.boostrap.constants.ConfigConstants;
import com.prax.framework.util.FileUtils;

/**
 * @author Huanan
 *
 */
public class ConfigUtils {

  /**
   * This method will find out all non-core struts-config files and append them to the config object.
   * These configuration will then get loaded.
   * @param strutsConfigDirectories - direct child directories of web-inf/config directory
   * 
   * @throws ServletException - in case we run into FileNotFoundException
   * 
   */

  public static String getNonCoreStrutsConfig(String[] strutsConfigDirectories) {
    String strutsConfigFilesToAppend = ""; // to return empty if not initialized again.

    if ((null != strutsConfigDirectories) && (strutsConfigDirectories.length > 0)) {
      for (int i = 0; i < strutsConfigDirectories.length; i++) {
        strutsConfigDirectories[i] = strutsConfigDirectories[i]
            + ConfigConstants.STRUTS_NONCORE_DIRECTORY;
        if (strutsConfigDirectories[i].equalsIgnoreCase("WEB-INF/config/core/struts") == true)
          strutsConfigDirectories[i] = "";
      }

      strutsConfigFilesToAppend = FileUtils.findFiles(strutsConfigDirectories,
          ConfigConstants.StrutsNonCorefilePattern, false, ConfigConstants.StrutsNonCorefileTypes);
    }

    return strutsConfigFilesToAppend;
  }

  /**
   * This method will find out all non-core spring-config files and append them to appropriate config object.
   * These configuration should then get loaded for non core common services.
   * @param springConfigDirectories - direct child directories of web-inf/config directory
   * 
   * @throws ServletException - in case we run into FileNotFoundException
   * 
   */

  public static String getNonCoreSpringConfig(String[] springConfigDirectories) {

    String springConfigFilesToAppend = ""; // to return empty if not initialized again.

    if ((null != springConfigDirectories) && (springConfigDirectories.length > 0)) {
      for (int i = 0; i < springConfigDirectories.length; i++) {
        springConfigDirectories[i] = springConfigDirectories[i]
            + ConfigConstants.SPRING_NONCORE_DIRECTORY;
      }
      springConfigFilesToAppend = FileUtils.findFiles(springConfigDirectories,
          ConfigConstants.SpringNonCorefilePattern, false, ConfigConstants.SpringNonCorefileTypes);
    }

    return springConfigFilesToAppend;
  }
}
