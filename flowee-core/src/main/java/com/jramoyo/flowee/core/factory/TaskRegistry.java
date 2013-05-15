/*
 * TaskRegistry.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.factory;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * An interface for workflow task registries
 * </p>
 * <p>
 * Workflow tasks are mean to be stateless instances and hence can be
 * implemented as singletons. Because of this, a registry is used instead of a
 * factory
 * </p>
 * 
 * @param T
 *            the type of workflow task returned by this registry
 * @param R
 *            the type of workflow request accepted by this registry
 * @param C
 *            the type of workflow context accepted by this registry
 * @author amoyojan
 */
public interface TaskRegistry<T extends Task<R, C>, R, C extends WorkflowContext> {

	/**
	 * Returns a workflow task given an identifier
	 * 
	 * @param id
	 *            a workflow task identifier
	 * @return a workflow task given an identifier
	 */
	T getTask(String id);
}