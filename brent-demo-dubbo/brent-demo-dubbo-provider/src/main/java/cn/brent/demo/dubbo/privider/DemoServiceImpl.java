package cn.brent.demo.dubbo.privider;

import cn.brent.demo.dubbo.api.DemoServiceApi;
import cn.brent.demo.dubbo.commons.Result;

public class DemoServiceImpl implements DemoServiceApi {

	@Override
	public Result<String> getHelloMsg(String name) {
		Result<String> result=new Result<String>();
		result.setResult("hello,"+name);
		return result;
	}

}
