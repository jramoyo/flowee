/*
 * AbstractConfigurableWorkflowFactory.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.factory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jramoyo.flowee.common.filter.Filter;
import com.jramoyo.flowee.common.util.StopWatchLogger;
import com.jramoyo.flowee.core.Workflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * An abstract class for building configurable workflow factories
 * </p>
 * 
 * @see fetchConfiguration()
 * @see fetchTaskNames(String)
 * @author jramoyo
 */
public abstract class AbstractConfigurableWorkflowFactory<W extends Workflow<T, R, C>, T extends Task<R, C>, R, C extends WorkflowContext>
		implements WorkflowFactory<W, T, R, C> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private TaskRegistry<T, R, C> taskRegistry;
	private Filter<R, C> filter;

	@Override
	public Set<W> createWorkflows(R request, C context) {
		Set<W> workflows = Sets.newHashSet();

		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();

			// Fetch and iterate through the configuration
			for (Entry<String, String> configEntry : fetchConfiguration()
					.entrySet()) {
				String workflowName = configEntry.getKey();
				String workflowCondition = configEntry.getValue();

				// Evaluate the workflow condition
				logger.debug("Evaluating condition for Workflow ["
						+ workflowName + "]: " + workflowCondition);
				if (filter.evaluate(request, context, workflowCondition)) {
					logger.debug("Assembling Workflow [" + workflowName + "]");
					W rule = assembleWorkflow(workflowName);
					workflows.add(rule);
				} else {
					logger.trace("Skipping Workflow [" + workflowName + "]");
				}
			}

		} finally {
			stopWatchLogger.debug("Created workflows");
			stopWatchLogger.stop();
		}

		return workflows;
	}

	public TaskRegistry<T, R, C> getTaskRegistry() {
		return taskRegistry;
	}

	public void setTaskRegistry(TaskRegistry<T, R, C> taskRegistry) {
		this.taskRegistry = taskRegistry;
	}

	public Filter<R, C> getFilter() {
		return filter;
	}

	public void setFilter(Filter<R, C> filter) {
		this.filter = filter;
	}

	/**
	 * Assembles a workflow together with its associated tasks
	 * 
	 * @param name
	 *            a workflow name
	 * @return the assembled worflow
	 */
	protected W assembleWorkflow(String name) {
		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();

			// Create a new instance
			W workflow = createWorkflow(name);

			// Fetch the associated tasks
			List<T> tasks = Lists.newArrayList();
			List<String> taskNames = fetchTaskNames(name);
			logger.debug("Fetched " + taskNames.size()
					+ " tasks for Workflow [" + name + "]");
			for (String taskName : taskNames) {
				logger.debug("Attaching Task [" + taskName + "] to Workflow ["
						+ name + "]");
				tasks.add(taskRegistry.getTask(taskName));
			}
			workflow.setTasks(tasks);

			return workflow;
		} finally {
			stopWatchLogger.debug("Assembled Workflow [" + name + "]");
			stopWatchLogger.stop();
		}
	}

	/**
	 * <p>
	 * Template method for fetching configuration from a source
	 * </p>
	 * <p>
	 * Fetched configuration is represented as a <code>Map</code> whose
	 * <strong>key</strong> is the workflow name and the <strong>value</strong>
	 * is the condition which activates this workflow
	 * </p>
	 * 
	 * @return a configuration represented as a <code>Map</code>
	 */
	protected abstract Map<String, String> fetchConfiguration();

	/**
	 * <p>
	 * Template method for fetching the names of the tasks associated to a
	 * workflow
	 * </p>
	 * 
	 * @param workflowName
	 *            a workflow name
	 * @return the names of the tasks associated to a workflow
	 */
	protected abstract List<String> fetchTaskNames(String workflowName);

	/**
	 * <p>
	 * Abstract factory for creating new workflow instances
	 * </p>
	 * 
	 * @param name
	 *            a workflow name
	 * @return a new workflow instance
	 */
	protected abstract W createWorkflow(String name);
}