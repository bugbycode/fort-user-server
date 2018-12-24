package com.bugbycode.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.user.User;

public interface UserDao {
	
	public List<User> query(Map<String,Object> param,RowBounds rb);
	
	public int count(Map<String,Object> param);
	
	public List<User> query(Map<String,Object> param);
	
	public User queryByUserName(String userName);
	
	public User queryByUserId(int userId);
	
	public int insert(User user);
	
	public void update(User user);
	
	public void delete(int userId);
	
	public void insertRelRole(int userId,int roleId);
	
	public void deleteRelRoleByUserId(int userId);
	
	public void insertRelGroup(int userId,int groupId);
	
	public void deleteRelGroupByUserId(int userId);
}
