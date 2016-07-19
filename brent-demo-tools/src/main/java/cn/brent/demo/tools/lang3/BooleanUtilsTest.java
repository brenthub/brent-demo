package cn.brent.demo.tools.lang3;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.BooleanUtils.or;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.BooleanUtils.toStringOnOff;

import org.apache.commons.lang3.BooleanUtils;
import org.junit.Test;

public class BooleanUtilsTest {

	@Test
	public void test(){
		
		System.out.println(isFalse(true));
		
		System.out.println(or(new Boolean[]{Boolean.TRUE,Boolean.FALSE}));
		
		System.out.println(toBoolean("on"));//true
		System.out.println(toBoolean("true"));//true
		System.out.println(toBoolean("yes"));//true
		System.out.println(toBoolean("no"));//false
		
		System.out.println(toBoolean(0));//false
		System.out.println(toBoolean(2));//true
		
		System.out.println(BooleanUtils.toString(true, "is true", "is false"));//is true
		
		System.out.println(toStringOnOff(true));
	}

}
