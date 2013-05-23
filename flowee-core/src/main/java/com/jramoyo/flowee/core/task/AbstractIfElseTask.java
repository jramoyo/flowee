/*
 * AbstractIfElseTask.java
 * 16 May 2013
 */
package com.jramoyo.flowee.core.task;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * An abstract class for building conditional workflow tasks which executes one
 * sub-task if the request holds true against the defined condition or another
 * sub-task if it holds false
 * </p>
 * <p>
 * The return status of the executed sub-task is returned by the parent task.
 * </p>
 * 
 * @see isTrue(R, C)
 * @see setIfTask(Task<R, C>)
 * @see setElseTask(Task<R, C>)
 * @author jramoyo
 */
public abstract class AbstractIfElseTask<R, C extends WorkflowContext> extends AbstractTask<R, C> {

	private Task<R, C> ifTask;

	private Task<R, C> elseTask;

	public AbstractIfElseTask() {
	}

	public AbstractIfElseTask(String name) {
		super(name);
	}

	@Override
	protected TaskStatus attemptExecute(R request, C context) throws WorkflowException {
		if (isTrue(request, context)) {
			logger.debug("Condition is true");
			return ifTask.execute(request, context);
		} else {
			logger.debug("Condition is false");
			return elseTask.execute(request, context);
		}
	}

	/**
	 * Returns the "if" task
	 * 
	 * @return the "if" task
	 */
	public Task<R, C> getIfTask() {
		return ifTask;
	}

	/**
	 * Sets the "if" task
	 * 
	 * @param ifTask
	 *            the "if" task
	 */
	public void setIfTask(Task<R, C> ifTask) {
		this.ifTask = ifTask;
	}

	/**
	 * Returns the the "else" task
	 * 
	 * @return the "else" task
	 */
	public Task<R, C> getElseTask() {
		return elseTask;
	}

	/**
	 * Sets the "else" task
	 * 
	 * @param elseTask
	 *            the "else" task
	 */
	public void setElseTask(Task<R, C> elseTask) {
		this.elseTask = elseTask;
	}

	/**
	 * Returns whether the supplied request holds true against the underlying
	 * condition
	 * 
	 * @param request
	 *            a workflow request
	 * @param context
	 *            a workflow context
	 * @return whether the supplied request holds true against the underlying
	 *         condition
	 */
	protected abstract boolean isTrue(R request, C context);
}