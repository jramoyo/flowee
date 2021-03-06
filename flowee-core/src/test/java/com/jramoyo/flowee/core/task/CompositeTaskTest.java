/*
 * CompositeTaskTest.java
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

import com.google.common.collect.Lists;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * CompositeTaskTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CompositeTaskTest {

	@Mock
	private Task<String, WorkflowContext> task1;

	@Mock
	private Task<String, WorkflowContext> task2;

	private CompositeTask<String, WorkflowContext> compositeTask = new CompositeTask<String, WorkflowContext>("COMPOSITE");

	@Before
	public void before() {
		initMocks(this);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testExecute() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();
		compositeTask.setTasks(Lists.newArrayList(task1, task2));
		compositeTask.execute("request", context);

		verify(task1).execute("request", context);
		verify(task2).execute("request", context);
	}
}
