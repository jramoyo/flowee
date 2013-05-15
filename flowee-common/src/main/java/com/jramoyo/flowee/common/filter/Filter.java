/*
 * Filter.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.filter;

import com.jramoyo.flowee.common.context.StringContext;

/**
 * <p>
 * An interface for a condition filter
 * </p>
 * 
 * 
 * @author jramoyo
 */
public interface Filter<R, C extends StringContext> {

	/**
	 * Returns whether a condition evaluates against the request as true or
	 * false
	 * 
	 * @param request
	 *            a request
	 * @param context
	 *            a context
	 * @param condition
	 *            the condition to evaluate
	 * @return whether a condition evaluates against the request as true or
	 *         false
	 */
	boolean evaluate(R request, C context, String condition);
}