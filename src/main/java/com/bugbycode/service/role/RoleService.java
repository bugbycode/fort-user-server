package com.bugbycode.service.role;

import java.util.List;
import java.util.Map;

import com.bugbycode.module.role.Role;
import com.util.page.SearchResult;

public interface RoleService {
	
	public SearchResult<Role> query(Map<String,Object> param,int startIndex,int pageSize);
	
	public List<Role> query(Map<String,Object> param);
	
	public List<Role> queryByUserId(int userId);
	
	public List<Role> queryByGroupId(int groupId);
	
	public Role queryByRoleId(int roleId);
	
	public Role queryByRoleName(String roleName);
	
	public int insert(Role role);
	
	public void update(Role role);
	
	public void delete(int roleId);
	
	public void deleteRelUserByRoleId(int roleId);
	
	public void deleteRelGroupByRoleId(int roleId);
}
