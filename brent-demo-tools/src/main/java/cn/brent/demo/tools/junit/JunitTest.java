package cn.brent.demo.tools.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import javax.security.auth.login.FailedLoginException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JunitTest {

	/**
	 * 针对所有测试，只执行一次，且必须为static void
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass");
	}

	/**
	 * 针对所有测试，只执行一次，且必须为static void
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass");
	}

	/**
	 * 初始化方法，每个测试方法运行之前都会执行一次
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("setUp");
	}

	/**
	 * 释放资源，每个测试方法运行之后都会执行一次
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.out.println("tearDown");
	}
	
	/**
	 * 对异常的提示信息进行检查
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * 因为expect异常，此方法会成功
	 * @throws Exception
	 */
	@Test
	public void testExceptionWithRule() throws Exception {
		thrown.expect(FailedLoginException.class);
		thrown.expectMessage("用户名不能为空");
		throw new FailedLoginException("用户名不能为空");
	}
	
	/**
	 * 因为expect异常，此方法会成功
	 * @throws Exception
	 */
	@Test(expected = FailedLoginException.class)
	public void testException() throws Exception {
		throw new FailedLoginException("用户名不能为空");
	}
	
	/**
	 * 定义超时时间
	 * 此方法会因为超时通不过java.lang.Exception: test timed out after 1000 milliseconds
	 * @throws Exception
	 */
	@Test(timeout=1000)
	public void testTimeOut() throws Exception {
		Thread.sleep(2000);
	}
	
	/**
	 * 断言
	 * java.lang.AssertionError: 此变量不能为空
	 */
	@Test
	public void assertTest() {
		assertEquals("不一样，调用的是equals方法", "a", "a");
		assertSame("不一样，调用的是==", "a", "a");
		/**
		 * 断言神器CoreMatchers类中有各种姿势，不够可自己扩展实现org.hamcrest.Matcher
		 */
		assertThat("不包含/","a/b", CoreMatchers.containsString("/"));
		assertNotNull("此变量不能为空",null);
		
	}
	
	/**
	 * 假设，假设通过就往下走，不通过也不会失败
	 * java.lang.AssertionError: 此变量不能为空
	 */
	@Test
	public void assumeTest() {
		/**
		 * 假设神器，CoreMatchers类中有各种姿势，不够可自己扩展实现org.hamcrest.Matcher
		 */
		Assume.assumeThat("oab", CoreMatchers.containsString("/"));
		System.out.println("i not runing");
	}
	
	
	/**
	 * 忽略的测试方法
	 */
	@Ignore
	public void testIgnore() {
		Assert.assertNotNull(null);
	}
	
	

}
