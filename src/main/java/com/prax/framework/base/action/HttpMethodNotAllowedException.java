package com.prax.framework.base.action;

public class HttpMethodNotAllowedException extends Exception {

	private static final long serialVersionUID = -8154466551412419075L;

	public HttpMethodNotAllowedException(String message) {
		super(message);
	}
}
