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

import java.io.Serializable;

import javax.servlet.http.HttpSession;

import com.prax.framework.context.Scope;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 *
 */
public class HttpSessionScope implements Scope {
    private HttpSession session;
    
    public HttpSessionScope(HttpSession session) {
        this.session = session;
    }
    
    public Serializable get(String key) {
        return (Serializable) session.getAttribute(key);
    }
    
    public void put(String key, Serializable value) {
        session.setAttribute(key, value);
    }
    
    public void remove(String key) {
        session.removeAttribute(key);
    }
    
}
