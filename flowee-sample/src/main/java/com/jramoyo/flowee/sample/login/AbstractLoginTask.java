/*
 * AbstractLoginTask.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import org.springframework.beans.factory.BeanNameAware;

import com.jramoyo.flowee.core.task.AbstractTask;

/**
 * AbstractLoginTask
 * 
 * @author amoyojan
 */
public abstract class AbstractLoginTask extends
		AbstractTask<LoginRequest, LoginContext> implements LoginTask,
		BeanNameAware {

	@Override
	public void setBeanName(String name) {
		setName(name);
	}
}
