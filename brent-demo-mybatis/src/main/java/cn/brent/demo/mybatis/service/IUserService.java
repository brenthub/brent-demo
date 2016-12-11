package cn.brent.demo.mybatis.service;

import java.util.List;

import cn.brent.demo.mybatis.model.User;

public interface IUserService {

	List<User> getAll();
	
	User selectByPrimaryKey(String id);
	
    int insert(User muser);
    
    int update(User muser);
    
    int delete(String id);
}
