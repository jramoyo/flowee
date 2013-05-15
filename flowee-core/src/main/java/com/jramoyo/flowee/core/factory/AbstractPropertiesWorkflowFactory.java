/*
 * AbstractPropertiesWorkflowFactory.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jramoyo.flowee.core.Workflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.task.Task;

/**
 * <p>
 * A generic abstract class for building workflow factories configured from
 * properties files
 * </p>
 * <p>
 * By default, instances of this class will look at the
 * <strong>classpath</strong> for the following properties files:
 * <ul>
 * <li>workflow.properties</li>
 * <li>task.properties</li>
 * </ul>
 * </p>
 * <p>
 * The path to these properties files can be overridden using their respective
 * field setters.
 * </p>
 * 
 * @see setWorkflowConfigFile(String)
 * @see setTaskConfigFile(String)
 * @author amoyojan
 */
public abstract class AbstractPropertiesWorkflowFactory<W extends Workflow<T, R, C>, T extends Task<R, C>, R, C extends WorkflowContext>
		extends AbstractConfigurableWorkflowFactory<W, T, R, C> {

	private Map<String, String> workflowConfig = Maps.newHashMap();
	private Map<String, String> taskConfig = Maps.newHashMap();

	private String workflowConfigFile = "/workflow.properties";
	private String taskConfigFile = "/task.properties";

	@PostConstruct
	public void init() throws IOException {
		loadFromProperties(workflowConfig, workflowConfigFile);
		loadFromProperties(taskConfig, taskConfigFile);
	}

	/**
	 * <p>
	 * Returns the name of the workflow configuration file
	 * </p>
	 * <p>
	 * Defaults to <strong>workflow.properties</stong>
	 * </p>
	 * 
	 * @return the name of the workflow configuration file
	 */
	public String getWorkflowConfigFile() {
		return workflowConfigFile;
	}

	/**
	 * <p>
	 * Sets the name of the workflow configuration file
	 * </p>
	 * <p>
	 * Defaults to <strong>workflow.properties</stong>
	 * </p>
	 * 
	 * @param workflowConfigFile
	 *            the name of the workflow configuration file
	 */
	public void setWorkflowConfigFile(String workflowConfigFile) {
		this.workflowConfigFile = workflowConfigFile;
	}

	/**
	 * <p>
	 * Returns the name of the task configuration file
	 * </p>
	 * <p>
	 * Defaults to <strong>task.properties</stong>
	 * </p>
	 * 
	 * @return the name of the task configuration file
	 */
	public String getTaskConfigFile() {
		return taskConfigFile;
	}

	/**
	 * <p>
	 * Sets the name of the task configuration file
	 * </p>
	 * <p>
	 * Defaults to <strong>task.properties</stong>
	 * </p>
	 * 
	 * @param taskConfigFile
	 *            the name of the task configuration file
	 */
	public void setTaskConfigFile(String taskConfigFile) {
		this.taskConfigFile = taskConfigFile;
	}

	@Override
	protected Map<String, String> fetchConfiguration() {
		return workflowConfig;
	}

	@Override
	protected List<String> fetchTaskNames(String workflowName) {
		List<String> taskNames = Lists.newArrayList();

		String taskNamesString = taskConfig.get(workflowName);
		if (!Strings.isNullOrEmpty(taskNamesString)) {
			String[] tokens = taskNamesString.split(",");
			for (String token : tokens) {
				taskNames.add(token);
			}
		}

		return taskNames;
	}

	private void loadFromProperties(Map<String, String> map, String propertiesFile) throws IOException {
		InputStream inputStream = getClass().getResourceAsStream(propertiesFile);
		if (inputStream != null) {
			Properties properties = new Properties();
			properties.load(inputStream);
			map.putAll(Maps.fromProperties(properties));
		} else {
			logger.warn("Unable to load properties file: " + propertiesFile);
		}
	}
}