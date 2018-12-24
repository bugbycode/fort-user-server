package com.bugbycode.dao.role;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.bugbycode.module.role.Role;

public interface RoleDao {
	
	public List<Role> query(Map<String,Object> param,RowBounds rb);
	
	public List<Role> query(Map<String,Object> param);
	
	public int count(Map<String,Object> param);
	
	public List<Role> queryByUserId(int userId);
	
	public List<Role> queryByGroupId(int groupId);
	
	public Role queryByRoleId(int roleId);
	
	public Role queryByRoleName(String roleName);
	
	public int insert(Role role);
	
	public void update(Role role);
	
	public void delete(int roleId);
	
	public void deleteRelUserByRoleId(int userId);
	
	public void deleteRelGroupByRoleId(int roleId);
}
