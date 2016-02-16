package cn.brent.demo.tools.lang3;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.CharSetUtils;
import org.apache.commons.lang3.CharUtils;
import org.junit.Test;

public class CharUtilsTest {

	@Test
	public void test(){
		System.out.println(CharUtils.toChar("AB"));//A
		
		CharSet set=CharSet.getInstance("ABDCDDE","34523452");
		
		System.out.println(set.toString());//[D, B, 3, 5, E, C, A, 2, 4]
		
		//统计出现的次数
		System.out.println(CharSetUtils.count("keep", "e"));//2
		
		//删除某个字符
		System.out.println(CharSetUtils.delete("keep", "e"));//kp
		
		//挤压 多个只保留 一个
		System.out.println(CharSetUtils.squeeze("keepe", "e"));//kepe
		
		//除指定字符，把其它的都干掉
		System.out.println(CharSetUtils.keep("keepe", "e"));//eee
		
		
		//是否支持此编码名字
		System.out.println(CharEncoding.isSupported("UTF-16"));//true
		System.out.println(CharEncoding.isSupported("UTF"));//false
	}

}
