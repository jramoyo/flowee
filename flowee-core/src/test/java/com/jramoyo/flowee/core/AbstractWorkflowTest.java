/*
 * AbstractWorkflowTest.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.jramoyo.flowee.core.task.Task;

/**
 * AbstractWorkflowTest
 * 
 * @author jramoyo
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractWorkflowTest {

	@Mock
	private Task<String, WorkflowContext> task;

	@InjectMocks
	private TestWorkflow workflow;

	@Test
	public void testExecute() throws WorkflowException {
		@SuppressWarnings("unchecked")
		List<Task<String, WorkflowContext>> tasks = Lists.newArrayList(task);
		workflow.setTasks(tasks);

		WorkflowContext context = new WorkflowContext();
		workflow.execute("value", context);
		verify(task).execute("value", context);
	}

	@Test
	public void testExecuteException() throws WorkflowException {
		Mockito.doThrow(new WorkflowException("Mock Exception")).when(task).execute(Mockito.anyString(), Mockito.any(WorkflowContext.class));

		@SuppressWarnings("unchecked")
		List<Task<String, WorkflowContext>> tasks = Lists.newArrayList(task);
		workflow.setTasks(tasks);

		try {
			WorkflowContext context = new WorkflowContext();
			workflow.execute("value", context);
			fail("Exception must be thrown!");
		} catch (WorkflowException ex) {
		}
	}

	public static class TestWorkflow extends AbstractWorkflow<Task<String, WorkflowContext>, String, WorkflowContext> {
		public TestWorkflow() {
			super("TEST");
		}
	}
}