/*
 * FilteredIfElseTask.java
 * 16 May 2013
 */
package com.jramoyo.flowee.core.task;

import com.jramoyo.flowee.common.filter.Filter;
import com.jramoyo.flowee.core.WorkflowContext;

/**
 * <p>
 * A conditional workflow task making use of <code>Filter</code>s
 * </p>
 * 
 * @see com.jramoyo.flowee.common.filter.Filter
 * @author jramoyo
 */
public class FilteredIfElseTask<R, C extends WorkflowContext> extends AbstractIfElseTask<R, C> {

	private Filter<R, C> filter;

	private String condition;

	public Filter<R, C> getFilter() {
		return filter;
	}

	public void setFilter(Filter<R, C> filter) {
		this.filter = filter;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	protected boolean isTrue(R request, C context) {
		return filter.evaluate(request, context, condition);
	}
}