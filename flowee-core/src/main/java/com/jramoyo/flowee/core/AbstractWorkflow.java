/*
 * AbstractWorkflow.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jramoyo.flowee.common.util.StopWatchLogger;
import com.jramoyo.flowee.core.task.Task;
import com.jramoyo.flowee.core.task.TaskStatus;

/**
 * <p>
 * A generic abstract class for defining a workflow
 * </p>
 * 
 * @author jramoyo
 */
public class AbstractWorkflow<T extends Task<R, C>, R, C extends WorkflowContext>
		implements Workflow<T, R, C> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private String name;
	private List<T> tasks;

	public AbstractWorkflow() {
	}

	public AbstractWorkflow(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void execute(R request, C context) throws WorkflowException {
		checkState(tasks != null && !tasks.isEmpty(), "No assigned tasks!");
		logger.debug("Executing Workflow [" + getName() + "]");
		logger.debug("Executing " + tasks.size() + " tasks");

		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();
			for (T task : tasks) {
				try {
					TaskStatus status = task.execute(request, context);
					if (status == TaskStatus.SKIP) {
						logger.debug("Task [" + task.getName()
								+ "] was skipped");
					}
					if (status == TaskStatus.BREAK) {
						logger.debug("Task [" + task.getName()
								+ "] broke the execution chain");
						break;
					}
				} catch (Exception ex) {
					throw new WorkflowException("An exception occured while"
							+ " executing Task [" + task.getName() + "]!", ex);
				}
			}
		} finally {
			stopWatchLogger.debug("Executed Workflow [" + getName() + "]");
			stopWatchLogger.stop();
		}
	}

	@Override
	public List<T> getTasks() {
		return this.tasks;
	}

	@Override
	public void setTasks(List<T> tasks) {
		this.tasks = tasks;
	}
}