package cn.brent.demo.dubbo.consumer;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.brent.demo.dubbo.api.DemoServiceApi;

@Service("helloForName")
public class HelloForName {

	@Resource()
	private DemoServiceApi demoService;
	
	public void sayHello(String name){
		System.out.println(demoService.getHelloMsg(name).getResult());
	}

	public void setDemoService(DemoServiceApi demoService) {
		this.demoService = demoService;
	}
	
}
