/*
 * ContextAwareTaskRegistry.java
 * 15 May 2013
 */
package com.jramoyo.flowee.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.factory.TaskRegistry;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * TaskRegistry which looks-up tasks from the Spring context
 * </p>
 * 
 * @author jramoyo
 */
public class ContextAwareTaskRegistry<T extends Task<R, C>, R, C extends WorkflowContext>
		implements TaskRegistry<T, R, C>, ApplicationContextAware {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	@Override
	public T getTask(String id) {
		try {
			return (T) applicationContext.getBean(id);
		} catch (NoSuchBeanDefinitionException ex) {
			logger.error("Unable to lookup Task [" + id + "]!", ex);
			return null;
		} catch (ClassCastException ex) {
			logger.error("Bean [" + id + "] is not" + " an instance of Task !",
					ex);
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
}