/**
 * 
 */
package com.prax.framework.boostrap.constants;

/**
 * @author Huanan
 *
 */
public interface ConfigConstants {
  public final String CONFIG_DIRECTORY = "WEB-INF/config";

  public static final String SpringNonCorefilePattern = "*.xml";
  public static final String SpringNonCorefileTypes = "xml";
  public static final String SPRING_NONCORE_DIRECTORY = "/spring";

  public static final String StrutsNonCorefilePattern = "struts-config*";
  public static final String StrutsNonCorefileTypes = "xml";
  public static final String STRUTS_NONCORE_DIRECTORY = "/struts";

  public static final String ValidatorNonCorefilePattern = "valid*";
  public static final String ValidatorNonCorefileTypes = "xml";
  public static final String VALIDATOR_NONCORE_DIRECTORY = "/validator";

  public static final String TilesNonCorefilePattern = "tiles-def*";
  public static final String TilesNonCorefileTypes = "xml";
  public static final String TILES_NONCORE_DIRECTORY = "/tiles";
}
