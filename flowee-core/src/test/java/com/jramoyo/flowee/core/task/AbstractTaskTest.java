/*
 * AbstractTaskTest.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * AbstractTaskTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractTaskTest {

	private TestTask testTask = new TestTask();
	private ExceptionTask exceptionTask = new ExceptionTask();

	@Test
	public void testExecute() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();
		assertEquals("Incorrect Status", TaskStatus.CONTINUE, testTask.execute("supported", context));
		assertEquals("Incorrect Status", TaskStatus.SKIP, testTask.execute("not supported", context));
	}

	@Test
	public void testExecuteException() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();
		try {
			exceptionTask.setMaxAttempts(5);
			exceptionTask.execute("value", context);
			fail("Exception must be thrown!");
		} catch (WorkflowException ex) {
			assertEquals(5, exceptionTask.attempts);
		}

		exceptionTask.setMaxAttempts(1);
		exceptionTask.setIsSkipOnException(true);
		assertEquals("Incorrect Status", TaskStatus.SKIP, exceptionTask.execute("value", context));
	}

	private static class TestTask extends AbstractTask<String, WorkflowContext> {

		public TestTask() {
			super("TEST");
		}

		@Override
		protected boolean isInputSupported(String request, WorkflowContext context) {
			return request.equals("supported");
		}

		@Override
		protected TaskStatus attemptExecute(String request, WorkflowContext context) throws WorkflowException {
			return TaskStatus.CONTINUE;
		}
	}

	private static class ExceptionTask extends AbstractTask<String, WorkflowContext> {
		private int attempts = 0;

		public ExceptionTask() {
			super("EXCEPTION");
		}

		@Override
		protected TaskStatus attemptExecute(String request, WorkflowContext context) throws WorkflowException {
			attempts++;
			throw new WorkflowException("Mocked Exception!");
		}
	}
}