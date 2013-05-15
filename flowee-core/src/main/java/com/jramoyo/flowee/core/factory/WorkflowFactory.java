/*
 * WorkflowFactory.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.factory;

import java.util.Set;

import com.jramoyo.flowee.core.Workflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * An interface for creating workflow instances
 * </p>
 * <p>
 * <code>WorkflowFactory</code> identifies one or more workflows which can be
 * executed against the given request
 * </p>
 * 
 * @param W
 *            the type of workflow returned by this factory
 * @param T
 *            the type of task associated to the returned workflow
 * @param R
 *            the type of workflow request accepted by this factory
 * @param C
 *            the type of workflow context accepted by this factory
 * @author jramoyo
 */
public interface WorkflowFactory<W extends Workflow<T, R, C>, T extends Task<R, C>, R, C extends WorkflowContext> {

	/**
	 * Creates a collection of workflows available to be executed against the
	 * request
	 * 
	 * @param request
	 *            a workflow request
	 * @param context
	 *            the shared workflow context
	 * @return a collection of workflows available to be executed against the
	 *         request
	 */
	Set<W> createWorkflows(R request, C context);
}