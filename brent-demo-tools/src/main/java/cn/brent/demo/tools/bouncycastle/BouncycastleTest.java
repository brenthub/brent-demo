package cn.brent.demo.tools.bouncycastle;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

public class BouncycastleTest {

	@Test
	public void testBase64(){
		String str = Base64.toBase64String(new String("1").getBytes());
		System.out.println(str);
	}
	
	@Test
	public void testHex(){
		String str = Hex.toHexString(new String("1").getBytes());
		System.out.println(str);
	}
	
	@Test
	public void testAES(){
		
	}
}
