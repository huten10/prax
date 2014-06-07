package com.prax.framework.context.impl;

import com.prax.framework.context.Context;
import com.prax.framework.context.Scope;

public class SystemContext extends Context<SystemScope, SystemScope> {

	private SystemScope sessionScope;

	public SystemContext() {
		super(new SystemScope());

	}

	@Override
	public Scope getLocalSessionScope() {
		if (sessionScope == null) {
			sessionScope = new SystemScope();
		}
		return sessionScope;
	}

	@Override
	public SystemScope getSession(boolean create) {
		return (SystemScope) getLocalSessionScope();
	}

	@Override
	public void invalidateSession() {

	}

}
