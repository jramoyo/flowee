/*
 * AbstractJexlFilter.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.filter;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.ReadonlyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jramoyo.flowee.common.context.StringContext;
import com.jramoyo.flowee.common.util.StopWatchLogger;

/**
 * <p>
 * Abstract class for building a JEXL based filter
 * </p>
 * <p>
 * Subclasses should override a template method to populate the JEXL context
 * with application specific objects
 * </p>
 * 
 * @see populateJexlContext(R, C)
 * @author jramoyo
 */
public abstract class AbstractJexlFilter<R, C extends StringContext> implements
		Filter<R, C> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private JexlEngine jexlEngine = new JexlEngine();

	/**
	 * <p>
	 * The condition can be any JEXL boolean expression:
	 * </p>
	 * <ul>
	 * <li>obj.field == 'value'</>
	 * <li>obj.field != null && obj.field == 'value'</>
	 * </ul>
	 */
	@Override
	public boolean evaluate(R request, C context, String condition) {
		StopWatchLogger stopWatchLogger = new StopWatchLogger(logger);
		try {
			stopWatchLogger.start();

			String booleanCondition = "if (" + condition
					+ ") {return true;} else {return false;}";

			logger.debug("Evaluating JEXL condition: " + booleanCondition);
			Expression expression = jexlEngine
					.createExpression(booleanCondition);

			Boolean result = (Boolean) expression.evaluate(populateJexlContext(
					request, context));
			return result.booleanValue();
		} finally {
			stopWatchLogger.debug("Evaluated JEXL expression");
			stopWatchLogger.stop();
		}
	}

	/**
	 * Template method for creating and populating the JEXL context
	 * 
	 * @param request
	 *            a request
	 * @param context
	 *            a context
	 * @return the populated JEXL context (read-only)
	 */
	protected abstract ReadonlyContext populateJexlContext(R request, C context);
}
