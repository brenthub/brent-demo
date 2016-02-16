package cn.brent.demo.tools.junit;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import static org.junit.Assume.*;
import static org.junit.Assert.*;

/**
 * 理论测试
 * @author covito
 */
@RunWith(Theories.class)
public class TheoriesTest {

	/**
	 * DataPoint:准备用来测试的数据,必须要为static
	 */
	@DataPoint
	public static String GOOD_USERNAME = "optimus";
	
	@DataPoint
	public static String USERNAME_WITH_SLASH = "optimu/sprime";

	/**
	 * 至少有一个DataPoint通过为测试成功，全部不符合为失败
	 * @param username
	 */
	@Theory
	public void filenameIncludesUsername(String username) {
		System.out.println(username);
		//对username进行检查，如果通过就往下走，不通过直接忽略返回
		assumeTrue(!username.contains("/"));
		
		System.out.println("begin assert...");
		//断言
		assertTrue(!username.contains("/"));
	}
}
