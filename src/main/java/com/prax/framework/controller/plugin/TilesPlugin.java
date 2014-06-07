package com.prax.framework.controller.plugin;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts.config.PlugInConfig;

import com.prax.framework.boostrap.constants.ConfigConstants;
import com.prax.framework.util.FileUtils;

/**
 *
 * Customized TilesPlugin class, 
 * will add non-core tile-def configuration files to path at run-time.
 * This would then be loaded at the time of init.
 *
 *@author Alvin.Hu
*/
public class TilesPlugin extends org.apache.struts.tiles.TilesPlugin {

    /* Class level CONSTANTS */
    private static final String TILEDEF_DIRECTORY = "/struts/tiles";  
    private static final String WEBINF_CONFIG_FOLDER = "WEB-INF/config";
    private static final String FILE_SEPARATOR = ",";
    private static final String DEFINITIONS_CONFIG = "definitions-config";
    
    private Logger mlLogger = Logger.getLogger(TilesPlugin.class);
    
    /**
     * Call-back from super class.
     * This method has been over-ridden to add non core common services tiles-configuration at run-time.
     *  
     * @param PlugInConfig - Tiles configuration files as found in WEB-INF/struts-config.xml.
     * @return void.
     *
    */
    
    public void setCurrentPlugInConfigObject(PlugInConfig arg0) {
        /* Load the non-core tile-definition files */
        arg0 = setNonCoreCSTileConfiguration(arg0);
        super.setCurrentPlugInConfigObject(arg0);
    }
    
    /**
     * @param arg0 - PlugInConfig - contains configuration for tile-definitions as specified in the struts-config file.
     * 
     * @return arg0 - PlugInConfig - will return configuration for tile-definitions as specified in the struts-config file +
     * configuration for non-core CS tile-definitions.
     * 
     * 
     */
    
    @SuppressWarnings("unchecked")
	protected PlugInConfig setNonCoreCSTileConfiguration(PlugInConfig arg0) {
        String[] directChildDirs = null;
        String nonCoreTilesConfiguration = null;
        
        try {
            // pick up direct child directories for WEB-INF/config folder
            directChildDirs = FileUtils.findDirectories(WEBINF_CONFIG_FOLDER, false);
            if ((null!=directChildDirs) && (directChildDirs.length>0)) {
                // append TILEDEF_DIRECTORY to each directory found in config folder and search for files in it.
                for (int i=0; i<directChildDirs.length; i++) {
                    directChildDirs[i] = directChildDirs[i]+TILEDEF_DIRECTORY;
                }
                
                nonCoreTilesConfiguration = FileUtils.findFiles(directChildDirs, ConfigConstants.TilesNonCorefilePattern, false, ConfigConstants.TilesNonCorefileTypes);
                
                // pick up the original tile-def configuration loaded and add the non-core tile-def configuration
                Map properties = arg0.getProperties();
                String coreTileConfiguration = (String)properties.get(DEFINITIONS_CONFIG);
                StringBuffer coreAndNonCoreTileConfiguration = new StringBuffer(coreTileConfiguration); 
                if ((null!=nonCoreTilesConfiguration) && (nonCoreTilesConfiguration.trim().length()>0)) {
                    coreAndNonCoreTileConfiguration.append(FILE_SEPARATOR);
                    coreAndNonCoreTileConfiguration.append(nonCoreTilesConfiguration);
                    arg0.addProperty(DEFINITIONS_CONFIG, coreAndNonCoreTileConfiguration.toString());
                }
                
            }
        } catch(Exception fNotFound) {
            // Silent-catch, app loading will not fail if failed to load non-core tile-definition files. Error will be displayed.
            mlLogger.error("Error: Loading Non-Core TileDefinition files.",fNotFound);
        }
        
        return arg0;
    }
}
