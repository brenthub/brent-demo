package cn.brent.demo.mybatis.dal;

import java.util.List;
import java.util.Map;

import cn.brent.demo.mybatis.model.User;

public interface UserMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getAll();
    
    List<Map> getAllForMap();
}