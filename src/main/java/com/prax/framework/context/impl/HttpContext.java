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
package com.prax.framework.context.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.prax.framework.context.Context;
import com.prax.framework.context.Scope;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 *
 */
public class HttpContext extends Context<HttpServletRequest, HttpSession> {
    
    private HttpSessionScope sessionScope;
    
    /**
     * @param containerContext
     * @param request
     */
    public HttpContext(ServletContext containerContext, HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public Scope getLocalSessionScope() {
        HttpSession session = getSession(false);
        if (session == null) {
            sessionScope = null;
            return null;
        }
        if (sessionScope != null)
            return sessionScope;
        sessionScope = new HttpSessionScope(session);
        return sessionScope;
    }
    
    @Override
    public HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }
    
    @Override
    public void invalidateSession() {
        HttpSession session = getSession(false);
        if (session != null) {
            session.invalidate();
            sessionScope = null;
        }
    }
    
}
