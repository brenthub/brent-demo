package cn.brent.demo.tools.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 测试套件——逻辑上将测试分组并将这这些测试作为一个单元测试来运行
 * @author covito
 */
@RunWith(Suite.class)
@SuiteClasses({JunitTest.class,ParaTest.class})
public class SuiteTest {

	/**
	 * 此方法不会运行
	 */
	@Test
	public void test(){
		System.out.println("can i run ?");
	}
	
}
