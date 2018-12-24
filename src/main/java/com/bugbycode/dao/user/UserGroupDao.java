package com.bugbycode.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.user.UserGroup;

public interface UserGroupDao {
	
	public List<UserGroup> query(Map<String,Object> param,RowBounds rb);
	
	public List<UserGroup> query(Map<String,Object> param);
	
	public List<UserGroup> queryByUserId(int userId);
	
	public int count(Map<String,Object> param);
	
	public UserGroup queryByGroupId(int groupId);
	
	public UserGroup queryByGroupName(String groupName);
	
	public int insert(UserGroup group);
	
	public void update(UserGroup group);
	
	public void delete(int groupId);
	
	public void insertRelRole(int groupId,int roleId);
	
	public void deleteRelRoleByGroupId(int groupId);
	
	public void insertRelUser(int groupId,int userId);
	
	public void deleteRelUserByGroupId(int groupId);
}
