package cn.brent.demo.tools.ini4j;

import org.ini4j.Ini;

public class Ini4JDemo {

	public static void main(String[] args) throws Exception {
		Ini i=new Ini(Ini4JDemo.class.getResourceAsStream("/ini4j/demo.ini"));
		String a=i.get("system", "system.a");
		System.out.println(a);
		
		String b=i.get("system", "system.b");
		System.out.println(b);
		
		System.out.println(i.get("demo1", "url"));
		System.out.println(i.get("demo2", "url"));
		
		System.out.println(i.getAll("demo1"));//[{url=[www.aaa.com], port=[34]}]
		
		System.out.println(i.keySet());//[system, demo1, demo2]
		
		for(String key:i.keySet()){
			//[{system.a=[33], system.b=[34]}]
			//[{url=[www.aaa.com], port=[34]}]
			//[{url=[www.bbb.com], port=[34]}]
			System.out.println(i.getAll(key));
		}
	}
}
