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
package com.prax.framework.base.model;

import java.io.Serializable;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 *
 */
public interface UCN extends Serializable{
    
    public String getUuid();
    
    public void setUuid(String uuid);
    
    public String getCode();
    
    public void setCode(String code);
    
    public String getName();
    
    public void setName(String name);
    
}
