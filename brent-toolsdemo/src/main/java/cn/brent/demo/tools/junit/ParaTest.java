package cn.brent.demo.tools.junit;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * 参数化测试
 * 
 * @author covito
 *
 */
@RunWith(Parameterized.class)
public class ParaTest {

	private String name;
	private int age;

	public ParaTest(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Test
	public void isPass() {
		Assert.assertEquals("name is not Ko", name, "Ko");
	}

	@Test
	public void ageTest() {
		Assert.assertTrue("age must bigger than 20", age > 20);
	}

	/**
	 * Parameters name属性只是junit显示用如[0:ParaTest(KO,19)]
	 * 
	 * @return
	 */
	@Parameters(name = "{index}: ParaTest({0},{1})")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { { "Ko", 19 }, { "Ko", 21 } });
	}

}
