package com.bugbycode.service.user;

import java.util.List;
import java.util.Map;

import com.bugbycode.module.user.UserGroup;
import com.util.page.SearchResult;

public interface UserGroupService {

	public SearchResult<UserGroup> query(Map<String,Object> param,int startIndex,int pageSize);
	
	public List<UserGroup> query(Map<String,Object> param);
	
	public List<UserGroup> queryByUserId(int userId);
	
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
