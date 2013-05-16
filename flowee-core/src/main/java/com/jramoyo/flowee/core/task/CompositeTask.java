/*
 * CompositeTask.java
 * 16 May 2013
 */
package com.jramoyo.flowee.core.task;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * A composite task is composed of one or more sub-tasks which are treated as a
 * single atomic unit of processing.
 * </p>
 * 
 * @author jramoyo
 */
public class CompositeTask<R, C extends WorkflowContext> extends AbstractTask<R, C> {

	private List<Task<R, C>> tasks;

	public CompositeTask() {
	}

	public CompositeTask(String name) {
		super(name);
	}

	@Override
	protected TaskStatus attemptExecute(R request, C context) throws WorkflowException {
		checkState(tasks != null && !tasks.isEmpty(), "No assigned tasks!");
		logger.debug("Executing " + tasks.size() + " sub-tasks");

		for (Task<R, C> task : tasks) {
			TaskStatus status = task.execute(request, context);
			if (status == TaskStatus.SKIP) {
				logger.debug("Sub-task [" + task.getName() + "] was skipped");
			}
			if (status == TaskStatus.BREAK) {
				logger.debug("Sub-task [" + task.getName() + "] broke the compsite chain");
				break;
			}
		}

		return TaskStatus.CONTINUE;
	}

	public List<Task<R, C>> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task<R, C>> tasks) {
		this.tasks = tasks;
	}
}