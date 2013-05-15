/*
 * LoginService.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import com.jramoyo.flowee.core.service.AbstractWorkflowService;

/**
 * LoginService
 * 
 * @author amoyojan
 */
public class LoginService
		extends
		AbstractWorkflowService<LoginWorkflow, LoginTask, LoginRequest, LoginContext> {

	@Override
	protected LoginContext createContext() {
		return new LoginContext();
	}
}
