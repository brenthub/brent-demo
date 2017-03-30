package cn.brent.demo.tools.bouncycastle;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

public class PBETest {
	
	private static int jdField_int = 1000;
	private static String jdField_goto = "salt";
	private static String jdField_try = "##Brent demo Co.,Ltd##^2003^";
	private static String PBE = "PBEWithSHAAndTwofish-CBC";

	@Test
	public void test() throws Exception {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String str = "这是一个待加密串";

		byte[] bs = encrypt(str.getBytes("gbk"), "lala");
		String bsts = new String(Base64.encode(bs));
		System.out.println(bsts);
		
		String data = bsts;
		byte[] b = Base64.decode(data);
		System.out.println(new String(decrypt(b, "lala"), "gbk"));

		
		bs = encrypt(str.getBytes("utf-8"), "lala");
		bsts = new String(Base64.encode(bs));
		System.out.println(bsts);

	}

	private static byte[] encrypt(byte[] bytes, String data) throws Exception {
		byte[] salt = Base64.decode(jdField_goto);
		PBEParameterSpec pram = new PBEParameterSpec(salt, jdField_int);
		char[] array = (jdField_try + data).toCharArray();
		PBEKeySpec key = new PBEKeySpec(array);
		SecretKey secreKey = SecretKeyFactory.getInstance(PBE).generateSecret(key);
		Cipher cipher = Cipher.getInstance(PBE);
		cipher.init(Cipher.ENCRYPT_MODE, secreKey, pram);
		return cipher.doFinal(bytes);
	}

	private static byte[] decrypt(byte[] bytes, String data) throws Exception {
		byte[] salt = Base64.decode(jdField_goto);
		PBEParameterSpec pram = new PBEParameterSpec(salt, jdField_int);
		char[] array = (jdField_try + data).toCharArray();
		PBEKeySpec key = new PBEKeySpec(array);
		SecretKey secreKey = SecretKeyFactory.getInstance(PBE).generateSecret(key);
		Cipher cipher = Cipher.getInstance(PBE);
		cipher.init(Cipher.DECRYPT_MODE, secreKey, pram);
		return cipher.doFinal(bytes);
	}
}
