package junit;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.brent.demo.mybatis.dal.UserMapper;
import cn.brent.demo.mybatis.model.User;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestDal {

	private static final Logger logger = Logger.getLogger(TestMybatis.class);

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Test
	public void testGetAll(){
		System.out.println(userMapper.getAllForMap());
	}

	@Test
//	@Ignore
	public void testInsert() {
	
		User muser = new User();
		muser.setId("0001");
		muser.setName("aaaa");
		muser.setAge(1234);
		muser.setAddress("ABCD");
		int i = userMapper.insert(muser);
		logger.info(JSON.toJSONStringWithDateFormat("add "+i, "yyyy-MM-dd HH:mm:ss"));
	}
	
}
