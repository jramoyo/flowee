/*
 * LoginServiceTest.java
 * 15 May 2013
 */
package com.jramoyo.flowee.sample;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jramoyo.flowee.core.WorkflowException;
import com.jramoyo.flowee.sample.login.LoginContext;
import com.jramoyo.flowee.sample.login.LoginRequest;
import com.jramoyo.flowee.sample.login.LoginService;

/**
 * LoginServiceTest
 * 
 * @author amoyojan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/spring.xml")
public class LoginServiceTest {

	@Resource(name = "workflowService")
	private LoginService service;

	@Test
	public void testAdminLogin() throws WorkflowException {
		LoginRequest request = new LoginRequest();
		request.setUsername("admin");
		request.setPassword("p@ssw0rd");
		request.setType("admin");

		LoginContext context = service.process(request);
		Assert.assertTrue("Incorrect result", context.getIsAuthenticated());

		request.setPassword("wrong");
		context = service.process(request);
		Assert.assertFalse("Incorrect result", context.getIsAuthenticated());
	}

	@Test
	public void testUserLogin() throws WorkflowException {
		LoginRequest request = new LoginRequest();
		request.setUsername("user");
		request.setPassword("p@ssw0rd");
		request.setType("user");

		LoginContext context = service.process(request);
		Assert.assertTrue("Incorrect result", context.getIsAuthenticated());

		request.setPassword("wrong");
		context = service.process(request);
		Assert.assertFalse("Incorrect result", context.getIsAuthenticated());
	}
}
