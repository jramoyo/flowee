/*
 * LoginWorkflow.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import com.jramoyo.flowee.core.AbstractWorkflow;

/**
 * LoginWorkflow
 * 
 * @author amoyojan
 */
public class LoginWorkflow extends
		AbstractWorkflow<LoginTask, LoginRequest, LoginContext> {

	public LoginWorkflow(String name) {
		super(name);
	}
}
