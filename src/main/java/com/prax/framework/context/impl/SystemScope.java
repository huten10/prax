package com.prax.framework.context.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

import com.prax.framework.context.Scope;

public class SystemScope implements Scope {

	private HashMap<String, Object> session = new HashMap<String, Object>();

	public Serializable get(String s) {
		return (Serializable) session.get(s);
	}

	public Locale getLocale() {
		return new Locale(Locale.getDefault().getCountry(), Locale.getDefault().getLanguage(), "");
	}

	public void put(String arg0, Serializable arg1) {
		session.put(arg0, arg1);
	}

	public void remove(String s) {
		session.remove(s);
	}

}
