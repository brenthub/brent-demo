package cn.brent.demo.tools.hamcrest;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class AssertTest {

	@Test
	public void est(){
		
		MatcherAssert.assertThat("can not be null!", "", CoreMatchers.notNullValue());
		MatcherAssert.assertThat("is not  B!", "A", CoreMatchers.is("A"));
		
		MatcherAssert.assertThat("nullValue", null, CoreMatchers.nullValue());
		
		MatcherAssert.assertThat("should be true", 1==1&&1+1==1);
	}
}
