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

import org.springframework.context.ApplicationContext;

/*
 * Changed by			Reference Number		Description
 * ----------			----------------		------------------------
 */

/**
 * @author Alvin.Hu
 * 
 */
public final class AppContext {

	private static AppContext instance;

	private ApplicationContext applicationContext;

	private AppContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public synchronized static void init(ApplicationContext applicationContext) {
		if (instance != null) {
			return;
		}
		instance = new AppContext(applicationContext);
	}

	public static ApplicationContext getApplicationContext() {
		return instance.applicationContext;
	}

}
