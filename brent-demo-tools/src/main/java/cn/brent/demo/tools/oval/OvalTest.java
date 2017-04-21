package cn.brent.demo.tools.oval;

import org.junit.Test;

import net.sf.oval.Validator;

public class OvalTest {

	@Test
	public void testCheckWith(){
		OvalMo mo=new OvalMo();
		mo.setId("aa");
		mo.setName("Name");
		mo.setInfo("Info");
		
		Validator vali=new Validator();
		vali.assertValid(mo);
	}
}
