/*
 * LoginFilter.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample.login;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.ReadonlyContext;

import com.jramoyo.flowee.common.filter.AbstractJexlFilter;

/**
 * LoginFilter
 * 
 * @author amoyojan
 */
public class LoginFilter extends AbstractJexlFilter<LoginRequest, LoginContext> {

	@Override
	protected ReadonlyContext populateJexlContext(LoginRequest request,
			LoginContext context) {
		JexlContext jexlContext = new MapContext();
		jexlContext.set("request", request);
		return new ReadonlyContext(jexlContext);
	}
}