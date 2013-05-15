/*
 * AbstractWorkflowService.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jramoyo.flowee.common.util.StopWatchLogger;
import com.jramoyo.flowee.core.Workflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.factory.WorkflowFactory;
import com.jramoyo.flowee.core.task.Task;

/**
 * AbstractWorkflowService
 * 
 * @param <W>
 *            the type of workflow used by this service
 * @param <T>
 *            the type of task associated to the used workflow
 * @param <R>
 *            the type of workflow request accepted by this service
 * @param <C>
 *            the type of workflow context returned by this service
 * @author amoyojan
 */
public abstract class AbstractWorkflowService<W extends Workflow<T, R, C>, T extends Task<R, C>, R, C extends WorkflowContext> implements
		WorkflowService<R, C> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private WorkflowFactory<W, T, R, C> factory;

	@Override
	public C process(R request) throws WorkflowException {
		return process(request, createContext());
	}

	@Override
	public C process(R request, C context) throws WorkflowException {
		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();
			for (W workflow : factory.createWorkflows(request, context)) {
				try {
					workflow.execute(request, context);
				} catch (WorkflowException ex) {
					logger.error("An error occurred while " + "executing Workflow [" + workflow.getName() + "]!", ex);
					throw ex;
				}
			}
		} finally {
			stopWatchLogger.debug("Processed request");
			stopWatchLogger.stop();
		}

		return context;
	}

	public WorkflowFactory<W, T, R, C> getFactory() {
		return factory;
	}

	public void setFactory(WorkflowFactory<W, T, R, C> factory) {
		this.factory = factory;
	}

	/**
	 * <p>
	 * Abstract factory method for creating a workflow context instance
	 * </p>
	 * 
	 * @return a workflow context instance
	 */
	protected abstract C createContext();
}