/*
 * ContextAwareTaskRegistryTest.java
 * 15 May 2013
 */
package com.jramoyo.flowee.spring;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jramoyo.flowee.core.WorkflowContext;
import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.core.task.AbstractTask;
import com.jramoyo.flowee.core.task.TaskStatus;

/**
 * ContextAwareTaskRegistryTest
 * 
 * @author jramoyo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring-test.xml")
public class ContextAwareTaskRegistryTest {

	@Resource(name = "contextAwareTaskRegistry")
	private ContextAwareTaskRegistry<TestTask, String, WorkflowContext> registry;

	@Test
	public void testGetAction() {
		Assert.assertNotNull("Task is null", registry.getTask("testTask"));
		Assert.assertNull("Task is not null", registry.getTask("unknownAction"));
		Assert.assertNull("Task is not null",
				registry.getTask("contextAwareTaskRegistry"));
	}

	public static class TestTask extends AbstractTask<String, WorkflowContext> {

		@Override
		protected TaskStatus attemptExecute(String request,
				WorkflowContext context) throws WorkflowException {
			return TaskStatus.CONTINUE;
		}
	}
}