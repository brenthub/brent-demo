package cn.brent.demo.tools.junit;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import cn.brent.demo.tools.junit.CategoTest.A;
import cn.brent.demo.tools.junit.CategoTest.B;
import cn.brent.demo.tools.junit.CategoTestSuite.FastTests;
import cn.brent.demo.tools.junit.CategoTestSuite.SlowTests;

/**
 * Suite的一种，对测试分进分类，每次只运行其中的几类
 * @author covito
 */
@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses({ A.class, B.class })
public class CategoTestSuite {

	/**
	 * 分类一
	 * @author covito
	 */
	public interface FastTests {
	}

	/**
	 * 分类二
	 * @author covito
	 */
	public interface SlowTests {
	}
}
