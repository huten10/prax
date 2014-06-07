/*
 * Project EBam
 * Copyright Information
 * (C) 2009-2012, SunGard Inc. All rights reserved.
 * 3F, No.210 Liangjing Road, Zhangjiang High-Tech Park, Shanghai, 201203, China.
 * 
 * This document is protected by copyright. No part of this
 * document may be reproduced in any form by any means without
 * prior written authorization of SunGard.
 * 
 */
package com.prax.framework.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.prax.framework.base.model.UCN;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 *
 */
public abstract class Context<R, S> {
    /**
     * ThreadLocal 持有当前Context实例
     */
    private static ThreadLocal<Context<?, ?>> currentInstance = new ThreadLocal<Context<?, ?>>();
    
    private R request;
    
    /**
     * Once called, variables defining the CSA app context can't be modified anymore
     */
    public Context(R request) {
        setRequest(request);
        setCurrentInstance(this);
        setSpringSecurityToken();
    }
    
    private void setSpringSecurityToken() {
        Authentication springToken = SecurityContextHolder.getContext().getAuthentication();
        if (springToken == null || springToken.isAuthenticated() == false) {
            Scope sessionScope = Context.getSessionScope();
            Object token = sessionScope != null ? sessionScope.get(Scope.SPRING_AUTH_TOKEN) : null;
            if (token != null && token instanceof Authentication) {
                SecurityContextHolder.getContext().setAuthentication((Authentication) token);
            }
        }
    }
    
    /**
     * Returns the current request object
     * @return
     */
    public R getRequest() {
        return request;
    }
    
    /**
     * Sets the request object to be used internally.
     * Made public in case someone would want to wrap the request
     * @param request
     */
    public void setRequest(R request) {
        this.request = request;
    }
    
    /**
     * Returns the current session object (and creates it if specified)
     * @param create
     * @return null if no current session & create is false 
     */
    public abstract S getSession(boolean create);
    
    public abstract Scope getLocalSessionScope();
    
    public abstract void invalidateSession();
    
    /*========================== Static Methods ===========================*/

    private static <R, S> void setCurrentInstance(Context<R, S> ctx) {
        currentInstance.set(ctx);
    }
    
    /**
     * Returns a SessionScope instance that can be used to put/get/remove attributes to the current session
     * @return null if there's no Context instance for the current thread
     */
    public static Scope getSessionScope() {
        return getCurrentInstance() != null ? getCurrentInstance().getLocalSessionScope() : null;
    }
    
    /**
     * Returns the Context instance for the current thread
     * @param <R>
     * @param <S>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R, S> Context<R, S> getCurrentInstance() {
        return (Context<R, S>) currentInstance.get();
    }
    
    /**
     * Method to retrieve the domain of the current session
     * @return null if not exists
     */
    public static UCN getCurrentDomain() {
        return Context.getSessionScope() != null ? (UCN) Context.getSessionScope().get(Scope.CURRENT_DOMAIN)
                : null;
    }
    
    /**
     * Method to retrieve the user of the current session
     * @return null if not exists
     */
    public static UCN getCurrentUser() {
        return Context.getSessionScope() != null ? (UCN) Context.getSessionScope().get(Scope.CURRENT_USER)
                : null;
    }
    
}
