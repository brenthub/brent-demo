package cn.brent.demo.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.brent.demo.mybatis.dal.UserMapper;
import cn.brent.demo.mybatis.model.User;

@Service("userService")
public class UserServiceImpl implements IUserService{

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Override
	public List<User> getAll() {
		
		return userMapper.getAll();
	}

	@Override
	public int insert(User muser) {
		
		return userMapper.insert(muser);
	}

	@Override
	public int update(User muser) {
		
		return userMapper.updateByPrimaryKey(muser);
	}

	@Override
	public int delete(String id) {
	
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User selectByPrimaryKey(String id) {
		
		return userMapper.selectByPrimaryKey(id);
	}

}
