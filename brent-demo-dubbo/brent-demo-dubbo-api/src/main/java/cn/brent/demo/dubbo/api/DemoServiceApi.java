package cn.brent.demo.dubbo.api;

import cn.brent.demo.dubbo.commons.Result;

public interface DemoServiceApi {

	public Result<String> getHelloMsg(String name);
}
