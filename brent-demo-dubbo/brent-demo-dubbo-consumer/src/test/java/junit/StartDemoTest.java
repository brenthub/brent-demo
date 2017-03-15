package junit;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.brent.demo.dubbo.consumer.HelloForName;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-*.xml")
public class StartDemoTest {
	
	@Resource(name="helloForName")
	private HelloForName hn;

	@Test
	public void testSayHello(){
		
		hn.sayHello("brent");	
	}
}
