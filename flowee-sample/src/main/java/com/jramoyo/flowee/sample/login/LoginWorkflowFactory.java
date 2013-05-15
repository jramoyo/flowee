/*
 * LoginWorkflowFactory.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import com.jramoyo.flowee.core.factory.AbstractPropertiesWorkflowFactory;

/**
 * LoginWorkflowFactory
 * 
 * @author amoyojan
 */
public class LoginWorkflowFactory
		extends
		AbstractPropertiesWorkflowFactory<LoginWorkflow, LoginTask, LoginRequest, LoginContext> {

	@Override
	protected LoginWorkflow createWorkflow(String name) {
		return new LoginWorkflow(name);
	}
}