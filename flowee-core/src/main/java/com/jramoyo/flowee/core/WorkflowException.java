/*
 * WorkflowException.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core;

/**
 * <p>
 * An exception which encapsulates workflow errors
 * </p>
 * 
 * @author jramoyo
 */
public class WorkflowException extends Exception {

	private static final long serialVersionUID = 2521958535314405658L;

	public WorkflowException(String message) {
		super(message);
	}

	public WorkflowException(Throwable clause) {
		super(clause);
	}

	public WorkflowException(String message, Throwable clause) {
		super(message, clause);
	}
}