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
 * @param <R>
 *            the type of request accepted by this filter
 * @param <C>
 *            the type of context accepted by this filter
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