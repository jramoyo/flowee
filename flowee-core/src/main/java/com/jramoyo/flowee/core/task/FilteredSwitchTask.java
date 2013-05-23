/*
 * FilteredSwitchTask.java
 * 20 May 2013
 */
package com.jramoyo.flowee.core.task;

import java.util.Map;
import java.util.Map.Entry;

import com.jramoyo.flowee.common.filter.Filter;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * A workflow task which serves as a "switch" for executing other tasks.
 * </p>
 * <p>
 * The switch works by evaluating conditions set against one or more sub-tasks.
 * If the condition holds true, the associated sub-task gets executed. A
 * <code>Filter</code> is used to evaluate the conditions.
 * </p>
 * <p>
 * This task refers to a <code>Map</code> whose <strong>key</strong> is a
 * condition which executes the task and whose <strong>value</strong> is a
 * reference to the task to execute.
 * </p>
 * <p>
 * Because multiple sub-tasks may get executed, the return status of each
 * executed sub-task are ignored. The parent switch task always returns
 * <code>TaskStatus.CONTINUE</code>.
 * </p>
 * 
 * @author jramoyo
 */
public class FilteredSwitchTask<R, C extends WorkflowContext> extends AbstractTask<R, C> {

	private Map<String, Task<R, C>> tasks;
	private Filter<R, C> filter;

	public FilteredSwitchTask() {
	}

	public FilteredSwitchTask(String name) {
		super(name);
	}

	public void setTasks(Map<String, Task<R, C>> tasks) {
		this.tasks = tasks;
	}

	public void setFilter(Filter<R, C> filter) {
		this.filter = filter;
	}

	@Override
	protected TaskStatus attemptExecute(R request, C context) throws WorkflowException {
		for (Entry<String, Task<R, C>> entry : tasks.entrySet()) {
			String condition = entry.getKey();
			Task<R, C> task = entry.getValue();
			if (filter.evaluate(request, context, condition)) {
				task.execute(request, context);
			}
		}

		return TaskStatus.CONTINUE;
	}
}