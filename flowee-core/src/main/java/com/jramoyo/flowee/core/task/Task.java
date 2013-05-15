/*
 * Task.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.task;

import com.jramoyo.flowee.common.api.NamedBean;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * An interface for a workflow task
 * </p>
 * <p>
 * A workflow task encapsulates a unit of processing applied against a request.
 * </p>
 * <p>
 * Tasks are ideally stateless. Each task associated with a workflow can share
 * information using the supplied workflow context.
 * </p>
 * 
 * @param <R>
 *            the type of workflow request accepted by this task
 * @param <C>
 *            the type of workflow context accepted by this task
 * @author jramoyo
 */
public interface Task<R, C extends WorkflowContext> extends NamedBean {

	/**
	 * Executes a request against this task
	 * 
	 * @param request
	 *            a workflow request
	 * @param context
	 *            the shared workflow context
	 * @return the status of the task execution
	 * @throws WorkflowException
	 */
	TaskStatus execute(R request, C context) throws WorkflowException;
}