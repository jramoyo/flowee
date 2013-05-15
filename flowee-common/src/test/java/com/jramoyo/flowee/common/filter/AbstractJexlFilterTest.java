/*
 * AbstractJexlFilterTest.java
 * 14 May 2013
 */
package com.jramoyo.flowee.common.filter;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlException;
import org.apache.commons.jexl2.MapContext;
import org.apache.commons.jexl2.ReadonlyContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.jramoyo.flowee.common.context.StringContext;

/**
 * AbstractJexlFilterTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractJexlFilterTest {

	private TestJexlFilter filter = new TestJexlFilter();

	@Test
	public void testEvaluate() {
		Assert.assertTrue("Incorrect evaluation", filter.evaluate("value", new StringContext(), "text == 'value'"));
		Assert.assertTrue("Incorrect evaluation", filter.evaluate("value", new StringContext(), "text != 'incorrect'"));

		try {
			filter.evaluate("value", new StringContext(), "text = 'incorrect'");
			Assert.fail("Exception must be thrown!");
		} catch (JexlException ex) {
		}
	}

	private static class TestJexlFilter extends AbstractJexlFilter<String, StringContext> {
		@Override
		protected ReadonlyContext populateJexlContext(String model, StringContext context) {
			JexlContext jexlContext = new MapContext();
			jexlContext.set("text", model);

			return new ReadonlyContext(jexlContext);
		}
	}
}
