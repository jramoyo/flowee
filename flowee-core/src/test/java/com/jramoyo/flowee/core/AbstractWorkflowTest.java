/*
 * AbstractWorkflowTest.java
 * 14 May 2013
 */
package com.jramoyo.flowee.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;
import com.jramoyo.flowee.core.task.Task;

/**
 * AbstractWorkflowTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractWorkflowTest {

	@Mock
	private Task<String, WorkflowContext> task;

	@InjectMocks
	private TestWorkflow workflow = new TestWorkflow();

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testExecute() throws WorkflowException {
		@SuppressWarnings("unchecked")
		List<Task<String, WorkflowContext>> tasks = Lists.newArrayList(task);
		workflow.setTasks(tasks);

		WorkflowContext context = new WorkflowContext();
		workflow.execute("value", context);
		Mockito.verify(task).execute("value", context);
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
			Assert.fail("Exception must be thrown!");
		} catch (WorkflowException ex) {
		}
	}

	public static class TestWorkflow extends AbstractWorkflow<Task<String, WorkflowContext>, String, WorkflowContext> {
		public TestWorkflow() {
			super("TEST");
		}
	}
}