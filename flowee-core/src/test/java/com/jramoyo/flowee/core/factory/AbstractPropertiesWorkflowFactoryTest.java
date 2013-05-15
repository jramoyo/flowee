/*
 * AbstractPropertiesWorkflowFactoryTest.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.factory;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.jramoyo.flowee.core.AbstractWorkflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.task.AbstractTask;
import com.jramoyo.flowee.core.task.TaskStatus;

/**
 * AbstractPropertiesWorkflowFactoryTest
 * 
 * @author amoyojan
 */
public class AbstractPropertiesWorkflowFactoryTest {

	private TestWorkflowFactory factory = new TestWorkflowFactory();

	@Test
	public void testFetchActionNames() throws IOException {
		factory.init();
		Assert.assertEquals("Incorrect tasks", "task1", factory.fetchTaskNames("workflow1").get(0));
		Assert.assertEquals("Incorrect tasks", "task1", factory.fetchTaskNames("workflow2").get(0));
		Assert.assertEquals("Incorrect tasks", "task2", factory.fetchTaskNames("workflow2").get(1));
	}

	@Test
	public void testFetchConfiguration() throws IOException {
		factory.init();
		Map<String, String> config = factory.fetchConfiguration();
		Assert.assertNotNull("Null config", config);
		Assert.assertFalse("Config is empty", config.isEmpty());
		Assert.assertEquals("Incorrect size", 2, config.size());
		Assert.assertEquals("Incorrect value", "text == 'workflow1'", config.get("workflow1"));
		Assert.assertEquals("Incorrect value", "text == 'workflow2'", config.get("workflow2"));
	}

	private static class TestWorkflowFactory extends AbstractPropertiesWorkflowFactory<TestWorkflow, TestTask, String, WorkflowContext> {
		@Override
		protected TestWorkflow createWorkflow(String name) {
			return new TestWorkflow(name);
		}
	}

	private static class TestWorkflow extends AbstractWorkflow<TestTask, String, WorkflowContext> {

		public TestWorkflow(String name) {
			super(name);
		}
	}

	private static class TestTask extends AbstractTask<String, WorkflowContext> {

		@Override
		protected TaskStatus attemptExecute(String request, WorkflowContext context) throws WorkflowException {
			return TaskStatus.CONTINUE;
		}
	}
}
