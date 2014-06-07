package com.prax.framework.context;

import java.io.Serializable;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 *
 */
public interface Scope {
    
    /**
     * current user in the session attributes.
     */
    public static final String CURRENT_USER = "CURRENT_USER";
    
    /**
     * current domain in the session attributes.
     */
    public static final String CURRENT_DOMAIN = "CURRENT_DOMAIN";
    
    /**
     * "AUTHENTICATION": Used to store the authentication object.
     * This attribute is used to identify whether a session is authenticated or not.
     */
    public static final String AUTHENTICATION = "AUTHENTICATION";
    
    public static final String SPRING_AUTH_TOKEN = "SPRING_SECURITY_TOKEN";
    
    public Serializable get(String key);
    
    public void put(String key, Serializable value);
    
    public void remove(String key);
}
