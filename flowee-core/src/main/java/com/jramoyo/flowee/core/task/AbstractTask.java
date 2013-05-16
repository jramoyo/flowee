/*
 * AbstractTask.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jramoyo.flowee.common.util.StopWatchLogger;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * An abstract class for building <code>Task</code>s.
 * </p>
 * 
 * @see isInputSupported(R, C)
 * @see setMaxAttempts(int)
 * @see setIsSkipOnException(boolean)
 * @author jramoyo
 */
public abstract class AbstractTask<R, C extends WorkflowContext> implements Task<R, C> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private String name;
	private int maxAttempts = 1;
	private boolean isSkipOnException = false;

	public AbstractTask() {
	}

	public AbstractTask(String name) {
		this.name = name;
	}

	@Override
	public TaskStatus execute(R request, C context) throws WorkflowException {
		TaskStatus status;

		logger.debug("Executing Task [" + getName() + "]");
		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();
			if (isInputSupported(request, context)) {
				try {
					status = retryExecute(request, context, 1);
				} catch (WorkflowException ex) {
					if (isSkipOnException) {
						logger.warn("Skipping Task [" + getName() + "] due to an exception!", ex);
						status = TaskStatus.SKIP;
					} else {
						throw ex;
					}
				}
			} else {
				status = TaskStatus.SKIP;
			}
		} finally {
			stopWatchLogger.debug("Executed Task [" + getName() + "]");
			stopWatchLogger.stop();
		}

		return status;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this task
	 * 
	 * @param name
	 *            the name of this task
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * <p>
	 * Returns the maximum number of attempts to execute this task
	 * </p>
	 * <p>
	 * Defaults to 1
	 * </p>
	 * 
	 * @return the maximum number of attempts to execute this task
	 */
	public int getMaxAttempts() {
		return maxAttempts;
	}

	/**
	 * Sets the maximum number of attempts to execute this task
	 * 
	 * @param maxAttempts
	 *            the maximum number of attempts to execute this task
	 */
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	/**
	 * Returns whether this task should be skipped if an exception occurs
	 * 
	 * @return whether this task should be skipped if an exception occurs
	 */
	public boolean isSkipOnException() {
		return isSkipOnException;
	}

	/**
	 * Sets whether this task should be skipped if an exception occurs
	 * 
	 * @param isSkipOnException
	 *            whether this task should be skipped if an exception occurs
	 */
	public void setIsSkipOnException(boolean isSkipOnException) {
		this.isSkipOnException = isSkipOnException;
	}

	/**
	 * <p>
	 * Attempts to execute a request against this task
	 * </p>
	 * <p>
	 * Depending on the <code>maxAttempts</code> setting, logic encapsulated by
	 * this method will be retried if a <code>WorkflowException</code> occurs.
	 * </p>
	 * 
	 * @param request
	 * @param context
	 * @return
	 * @throws WorkflowException
	 */
	protected abstract TaskStatus attemptExecute(R request, C context) throws WorkflowException;

	/**
	 * <p>
	 * Returns whether this task supports the given request
	 * </p>
	 * <p>
	 * Defaults to <strong>true</strong> (supported). Subclasses should override
	 * this method to implement a different behaviour.
	 * </p>
	 * 
	 * @param request
	 *            a workflow request
	 * @param context
	 *            the shared workflow context
	 * @return whether this task supports the given request
	 */
	protected boolean isInputSupported(R request, C context) {
		return true;
	}

	/*
	 * Executes this task and retries whenever a WorkflowException occurs
	 */
	private TaskStatus retryExecute(R request, C context, int attempts) throws WorkflowException {
		try {
			return attemptExecute(request, context);
		} catch (WorkflowException ex) {
			if (attempts < maxAttempts) {
				attempts++;
				logger.warn("An exception occured: " + ex.getMessage());
				logger.warn("Retrying Task [" + getName() + "], attempts: " + attempts + " of " + maxAttempts);
				return retryExecute(request, context, attempts);
			} else {
				throw ex;
			}
		}
	}
}