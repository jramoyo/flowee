/*
 * AuthenticateAdmin.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login.task;

import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.task.TaskStatus;
import com.jramoyo.flowee.sample.login.AbstractLoginTask;
import com.jramoyo.flowee.sample.login.LoginContext;
import com.jramoyo.flowee.sample.login.LoginRequest;

/**
 * AuthenticateAdmin
 * 
 * @author amoyojan
 */
public class AuthenticateAdmin extends AbstractLoginTask {

	@Override
	protected TaskStatus attemptExecute(LoginRequest request,
			LoginContext context) throws WorkflowException {
		if ("admin".equals(request.getUsername())
				&& "p@ssw0rd".equals(request.getPassword())) {
			System.out.println(String.format(
					"User '%s' has been authenticated as Administrator",
					request.getUsername()));
			context.setIsAuthenticated(Boolean.TRUE);
			return TaskStatus.CONTINUE;
		} else {
			System.err.println(String.format("Cannot authenticate user '%s'!",
					request.getUsername()));
			context.setIsAuthenticated(Boolean.FALSE);
			return TaskStatus.BREAK;
		}
	}
}