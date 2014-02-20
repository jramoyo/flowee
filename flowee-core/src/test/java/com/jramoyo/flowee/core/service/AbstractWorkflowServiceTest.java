/*
 * AbstractWorkflowServiceTest.java
 * 15 May 2013
 */
package com.jramoyo.flowee.core.service;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jramoyo.flowee.core.AbstractWorkflow;
import com.jramoyo.flowee.core.Workflow;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.factory.WorkflowFactory;
import com.jramoyo.flowee.core.task.Task;

/**
 * AbstractWorkflowServiceTest
 * 
 * @author amoyojan
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractWorkflowServiceTest {

	@Mock
	private WorkflowFactory<Workflow<Task<String, WorkflowContext>, String, WorkflowContext>, Task<String, WorkflowContext>, String, WorkflowContext> factory;

	@Mock
	private Task<String, WorkflowContext> task;

	@InjectMocks
	private TestWorkflowService service;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testProcess() throws WorkflowException {
		Set<Workflow<Task<String, WorkflowContext>, String, WorkflowContext>> workflows = Sets.newHashSet();
		workflows.add(createWorkflow());
		Mockito.when(factory.createWorkflows(Mockito.anyString(), Mockito.any(WorkflowContext.class))).thenReturn(workflows);

		service.process("request");
		Mockito.verify(task).execute(Mockito.anyString(), Mockito.any(WorkflowContext.class));
	}

	@Test
	public void testProcessException() throws WorkflowException {
		Set<Workflow<Task<String, WorkflowContext>, String, WorkflowContext>> workflows = Sets.newHashSet();
		workflows.add(createWorkflow());
		Mockito.when(factory.createWorkflows(Mockito.anyString(), Mockito.any(WorkflowContext.class))).thenReturn(workflows);
		Mockito.doThrow(new WorkflowException("Mock Exception")).when(task).execute(Mockito.anyString(), Mockito.any(WorkflowContext.class));

		try {
			service.process("request");
			Assert.fail("Exception must be thrown!");
		} catch (WorkflowException ex) {
		}
	}

	private static class TestWorkflowService extends
			AbstractWorkflowService<Workflow<Task<String, WorkflowContext>, String, WorkflowContext>, Task<String, WorkflowContext>, String, WorkflowContext> {
		@Override
		protected WorkflowContext createContext() {
			return new WorkflowContext();
		}
	}

	private Workflow<Task<String, WorkflowContext>, String, WorkflowContext> createWorkflow() {
		Workflow<Task<String, WorkflowContext>, String, WorkflowContext> workflow = new AbstractWorkflow<Task<String, WorkflowContext>, String, WorkflowContext>() {
			@Override
			public String getName() {
				return "TEST";
			}
		};

		@SuppressWarnings("unchecked")
		List<Task<String, WorkflowContext>> tasks = Lists.newArrayList(task);
		workflow.setTasks(tasks);

		return workflow;
	}
}
