/*
 * TaskStatus.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.task;

/**
 * <p>
 * Represents the status after executing a workflow task
 * </p>
 * 
 * @author jramoyo
 */
public enum TaskStatus {
	/**
	 * A status to indicate that the workflow should continue with the next task
	 */
	CONTINUE,

	/**
	 * A status to indicate that the task was silently skipped
	 */
	SKIP,

	/**
	 * A status to indicate that the workflow should break the task chain
	 */
	BREAK;
}
