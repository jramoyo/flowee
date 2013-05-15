/*
 * LoginContext.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import com.jramoyo.flowee.core.WorkflowContext;

/**
 * LoginContext
 * 
 * @author amoyojan
 */
public class LoginContext extends WorkflowContext {
	private static final String KEY_IS_AUTHENTICATED = "isAuthenticated";

	public void setIsAuthenticated(Boolean isAuthenticated) {
		put(KEY_IS_AUTHENTICATED, isAuthenticated);
	}

	public Boolean getIsAuthenticated() {
		return (Boolean) get(KEY_IS_AUTHENTICATED);
	}
}
