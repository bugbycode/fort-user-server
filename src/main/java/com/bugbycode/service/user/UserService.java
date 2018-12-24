package com.bugbycode.service.user;

import java.util.List;
import java.util.Map;

import com.bugbycode.module.user.User;
import com.util.page.SearchResult;

public interface UserService {
	
	public SearchResult<User> query(Map<String,Object> param,int startIndex,int pageSize);
	
	public List<User> query(Map<String,Object> param);
	
	public User queryByUserId(int userId);
	
	public User queryByUserName(String userName);
	
	public int insert(User user);
	
	public void update(User user);
	
	public void delete(int userId);
	
	public void insertRelRole(int userId,int roleId);
	
	public void deleteRelRoleByUserId(int userId);
	
	public void insertRelGroup(int userId,int groupId);
	
	public void deleteRelGroupByUserId(int userId);
}
