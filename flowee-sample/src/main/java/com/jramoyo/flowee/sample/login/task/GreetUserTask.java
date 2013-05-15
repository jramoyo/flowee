/*
 * GreetUserTask.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login.task;

import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.task.TaskStatus;
import com.jramoyo.flowee.sample.login.AbstractLoginTask;
import com.jramoyo.flowee.sample.login.LoginContext;
import com.jramoyo.flowee.sample.login.LoginRequest;

/**
 * GreetUserTask
 * 
 * @author amoyojan
 */
public class GreetUserTask extends AbstractLoginTask {

	@Override
	protected TaskStatus attemptExecute(LoginRequest request,
			LoginContext context) throws WorkflowException {
		System.out
				.println(String.format("Welcome '%s'!", request.getUsername()));
		return TaskStatus.CONTINUE;
	}
}
