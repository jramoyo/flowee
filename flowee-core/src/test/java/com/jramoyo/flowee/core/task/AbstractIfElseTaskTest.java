/*
 * AbstractIfElseTaskTest.java
 * May 16, 2013 
 */
package com.jramoyo.flowee.core.task;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * AbstractIfElseTaskTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractIfElseTaskTest {

	@Mock
	private Task<String, WorkflowContext> ifTask;

	@Mock
	private Task<String, WorkflowContext> elseTask;

	private TestIfElseTask ifElseTask = new TestIfElseTask("TEST_IF_ELSE");

	@Before
	public void before() {
		initMocks(this);
		ifElseTask.setIfTask(ifTask);
		ifElseTask.setElseTask(elseTask);
	}

	@Test
	public void testTrue() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();
		ifElseTask.execute("true", context);

		verify(ifTask).execute("true", context);
	}

	@Test
	public void testFalse() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();
		ifElseTask.execute("false", context);

		verify(elseTask).execute("false", context);
	}

	private static class TestIfElseTask extends AbstractIfElseTask<String, WorkflowContext> {

		public TestIfElseTask(String name) {
			super(name);
		}

		@Override
		protected boolean isTrue(String request, WorkflowContext context) {
			return "true".equals(request);
		}
	}
}
