/*
 * FilteredSwitchTaskTest.java
 * 20 May 2013
 */
package com.jramoyo.flowee.core.task;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Maps;
import com.jramoyo.flowee.common.filter.Filter;
import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;

/**
 * FilteredSwitchTaskTest
 * 
 * @author jramoyo
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class FilteredSwitchTaskTest {

	@Mock
	private Task<String, WorkflowContext> task1;

	@Mock
	private Task<String, WorkflowContext> task2;

	@Mock
	private Filter<String, WorkflowContext> filter;

	@InjectMocks
	private FilteredSwitchTask<String, WorkflowContext> switchTask = new FilteredSwitchTask<String, WorkflowContext>(
			"SWITCH");

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSwitch() throws WorkflowException {
		WorkflowContext context = new WorkflowContext();

		Mockito.when(filter.evaluate("task1", context, "task1")).thenReturn(
				true);
		Mockito.when(filter.evaluate("task1", context, "task2")).thenReturn(
				false);
		Mockito.when(filter.evaluate("task2", context, "task1")).thenReturn(
				false);
		Mockito.when(filter.evaluate("task2", context, "task2")).thenReturn(
				true);

		Map<String, Task<String, WorkflowContext>> tasks = Maps.newHashMap();
		tasks.put("task1", task1);
		tasks.put("task2", task2);
		switchTask.setTasks(tasks);

		switchTask.execute("task1", context);
		Mockito.verify(task1).execute("task1", context);

		switchTask.execute("task2", context);
		Mockito.verify(task2).execute("task2", context);
	}
}