package cn.brent.boot.demo.test;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class JarVersionTest {

	//@Test
	public void test(){
		Package pkg = StringUtils.class.getPackage();
		System.out.println(pkg.getImplementationTitle());
		System.out.println(pkg.getImplementationVendor());
		System.out.println(pkg.getImplementationVersion());
		System.out.println(pkg.getName());
		System.out.println(pkg.getSpecificationTitle());
		System.out.println(pkg.getSpecificationVendor());
		System.out.println(pkg.getSpecificationVersion());
	}
	
	@Test
	public void TestReg(){
		String str="aaa@aa.comxxx@tt.netxxx@89.cn";
		Pattern p=Pattern.compile("\\w*@\\w*\\.(com|cn|net)");
		Matcher m=p.matcher(str);
		while(m.find()){
			System.out.println(m.group());
		}
		
	}
}
