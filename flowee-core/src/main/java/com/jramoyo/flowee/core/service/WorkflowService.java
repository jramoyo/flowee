/*
 * WorkflowService.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.service;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * <p>
 * An interface for workflow services
 * </p>
 * 
 * @param <R>
 *            the type of workflow request accepted by this service
 * @param <C>
 *            the type of workflow context returned by this service
 * @author jramoyo
 */
public interface WorkflowService<R, C extends WorkflowContext> {

	/**
	 * <p>
	 * Processes a request
	 * </p>
	 * <p>
	 * Depending on the implementation, multiple workflows may be executed
	 * against the request, if so, each workflow <u>will share the same context
	 * instance</u>. Caution must be applied in order for the workflows to not
	 * override each other's data.
	 * </p>
	 * 
	 * @param request
	 *            a workflow request
	 * @return the context resulting from processing the request
	 * @throws WorkflowException
	 */
	C process(R request) throws WorkflowException;

	/**
	 * <p>
	 * Processes a request and context
	 * </p>
	 * <p>
	 * Depending on the implementation, multiple workflows may be executed
	 * against the request, if so, each workflow <u>will share the same contex
	 * instance</u>. Caution must be applied in order for the workflows to not
	 * override each other's data.
	 * </p>
	 * 
	 * @param request
	 *            the workflow request
	 * @param context
	 *            the workflow context
	 * @return the context updated with information from processing the request
	 * @throws WorkflowException
	 */
	C process(R request, C context) throws WorkflowException;
}