package cn.brent.demo.tools.junit;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import cn.brent.demo.tools.junit.CategoTestSuite.FastTests;
import cn.brent.demo.tools.junit.CategoTestSuite.SlowTests;

/**
 * Category或作用于类和方法，当作用于类时，表示此类下的所以方法都属于同一种类
 * @author covito
 */
public class CategoTest {

	public static class A {
		
		@Test
		public void a() {
			
		}

		@Test
		@Category(SlowTests.class)
		public void b() {
			
		}
	}

	@Category({ SlowTests.class, FastTests.class })
	public static class B {
		@Test
		public void c() {

		}
	}


}
