/*
 * AbstractConfigurableWorkflowFactoryTest.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.factory;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.google.common.collect.Maps;
import com.jramoyo.flowee.common.filter.Filter;
import com.jramoyo.flowee.core.AbstractWorkflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.task.AbstractTask;
import com.jramoyo.flowee.core.task.TaskStatus;

/**
 * AbstractConfigurableWorkflowFactoryTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractConfigurableWorkflowFactoryTest {

	@Mock
	private Filter<String, WorkflowContext> filter;

	@Mock
	private TaskRegistry<TestTask, String, WorkflowContext> registry;

	@InjectMocks
	private TestWorkflowFactory factory = new TestWorkflowFactory();

	@Test
	public void testCreateWorkflows() {
		WorkflowContext context = new WorkflowContext();

		Mockito.when(filter.evaluate("workflow1", context, "text=='workflow1'")).thenReturn(Boolean.TRUE);
		Mockito.when(filter.evaluate("workflow2", context, "text=='workflow2'")).thenReturn(Boolean.TRUE);
		Mockito.when(registry.getTask("task1")).thenReturn(new TestTask("task1"));
		Mockito.when(registry.getTask("task1")).thenReturn(new TestTask("task2"));

		Set<TestWorkflow> workflow1 = factory.createWorkflows("workflow1", context);
		Assert.assertNotNull("Workflows are null", workflow1);
		Assert.assertFalse("Workflows are empty", workflow1.isEmpty());
		Assert.assertEquals("Incorrect workflows size", 1, workflow1.size());

		Set<TestWorkflow> workflow2 = factory.createWorkflows("workflow2", context);
		Assert.assertNotNull("Workflows are null", workflow2);
		Assert.assertFalse("Workflows are empty", workflow2.isEmpty());
		Assert.assertEquals("Incorrect workflows size", 1, workflow2.size());
	}

	@Test
	public void testAssembleWorkflow() {
		Mockito.when(registry.getTask("task1")).thenReturn(new TestTask("task1"));
		Mockito.when(registry.getTask("task2")).thenReturn(new TestTask("task1"));

		TestWorkflow workflow1 = factory.assembleWorkflow("workflow1");
		Assert.assertNotNull("Rule is null", workflow1);
		Assert.assertEquals("Incorrect name", "workflow1", workflow1.getName());
		Assert.assertNotNull("Tasks are null", workflow1.getTasks());
		Assert.assertFalse("Tasks are empty", workflow1.getTasks().isEmpty());
		Assert.assertEquals("Incorrect actions size", 1, workflow1.getTasks().size());

		TestWorkflow workflow2 = factory.assembleWorkflow("workflow2");
		Assert.assertNotNull("Rule is null", workflow2);
		Assert.assertEquals("Incorrect name", "workflow2", workflow2.getName());
		Assert.assertNotNull("Tasks are null", workflow2.getTasks());
		Assert.assertFalse("Tasks are empty", workflow2.getTasks().isEmpty());
		Assert.assertEquals("Incorrect actions size", 2, workflow2.getTasks().size());

		TestWorkflow workflow3 = factory.assembleWorkflow("workflow3");
		Assert.assertNotNull("Rule is null", workflow3);
		Assert.assertEquals("Incorrect name", "workflow3", workflow3.getName());
		Assert.assertNotNull("Tasks are null", workflow3.getTasks());
		Assert.assertTrue("Tasks are not empty", workflow3.getTasks().isEmpty());
	}

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	private static class TestWorkflowFactory extends AbstractConfigurableWorkflowFactory<TestWorkflow, TestTask, String, WorkflowContext> {

		@Override
		protected Map<String, String> fetchConfiguration() {
			Map<String, String> configuration = Maps.newHashMap();

			configuration.put("workflow1", "text=='workflow1'");
			configuration.put("workflow2", "text=='workflow2'");

			return configuration;
		}

		@Override
		protected List<String> fetchTaskNames(String workflowName) {
			if ("workflow1".equals(workflowName)) {
				return Lists.newArrayList("task1");
			}

			else if ("workflow2".equals(workflowName)) {
				return Lists.newArrayList("task1", "task2");
			}

			else {
				return Lists.newArrayList();
			}
		}

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

		public TestTask(String name) {
			super(name);
		}

		@Override
		protected TaskStatus attemptExecute(String request, WorkflowContext context) throws WorkflowException {
			return TaskStatus.CONTINUE;
		}
	}
}