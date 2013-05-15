/*
 * Workflow.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core;

import java.util.List;

import com.jramoyo.flowee.common.api.NamedBean;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * An interface for a workflow
 * </p>
 * <p>
 * A workflow processes a given request against a sequence of 1 or more
 * configured tasks.
 * </p>
 * 
 * @param <T>
 *            the type of task associated with this workflow
 * @param <R>
 *            the type of request accepted by this workflow
 * @param <C>
 *            the type of context accepted by this workflow
 * @author jramoyo
 */
public interface Workflow<T extends Task<R, C>, R, C extends WorkflowContext> extends NamedBean {

	/**
	 * Executes a request against this workflow
	 * 
	 * @param request
	 *            a request
	 * @param context
	 *            the shared workflow context
	 * @throws WorkflowException
	 */
	void execute(R request, C context) throws WorkflowException;

	/**
	 * Returns the tasks associated to this workflow
	 * 
	 * @return the tasks associated to this workflow
	 */
	List<T> getTasks();

	/**
	 * Sets the tasks associated to this workflow
	 * 
	 * @param tasks
	 *            the tasks associated to this workflow
	 */
	void setTasks(List<T> tasks);
}