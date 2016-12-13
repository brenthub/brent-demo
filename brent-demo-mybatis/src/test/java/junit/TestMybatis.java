package junit;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.brent.demo.mybatis.model.User;
import cn.brent.demo.mybatis.service.IUserService;

import com.alibaba.fastjson.JSON;

@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestMybatis {

	private static final Logger logger = Logger.getLogger(TestMybatis.class);

	private IUserService muserService;

	@Autowired
	public void setMuserService(IUserService muserService) {
		this.muserService = muserService;
	}
	
	@Test
	public void test1() {
		
		List<User> list = muserService.getAll();
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void test2() {
	
		User muser = new User();
		muser.setId("0000");
		muser.setName("aaaa");
		muser.setAge(1234);
		muser.setAddress("ABCD");
		int i = muserService.insert(muser);
		logger.info(JSON.toJSONStringWithDateFormat("add "+i, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void test3() {
		
		User muser = new User();
		muser.setId("0000");
		muser.setName("bbbb");
		muser.setAge(1234);
		muser.setAddress("ABCD");
		int i = muserService.update(muser);
		logger.info(JSON.toJSONStringWithDateFormat("update " +i, "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void test4() {
		
		User muser = new User();
		muser.setId("0000");
		muser.setName("bbbb");
		muser.setAge(1234);
		muser.setAddress("ABCD");
		int i = muserService.delete("0000");
		logger.info(JSON.toJSONStringWithDateFormat("delete "+i, "yyyy-MM-dd HH:mm:ss"));
	}
	
}
