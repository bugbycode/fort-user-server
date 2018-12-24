package com.bugbycode.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.BaseDao;
import com.bugbycode.dao.user.UserGroupDao;
import com.bugbycode.module.user.UserGroup;

@Repository("userGroupDao")
public class UserGroupDaoImpl extends BaseDao implements UserGroupDao {

	@Override
	public List<UserGroup> query(Map<String, Object> param, RowBounds rb) {
		return getSqlSession().selectList("userGroup.query", param, rb);
	}

	@Override
	public List<UserGroup> query(Map<String, Object> param) {
		return getSqlSession().selectList("userGroup.query", param);
	}

	@Override
	public List<UserGroup> queryByUserId(int userId){
		return getSqlSession().selectList("userGroup.queryByUserId", userId);
	}
	
	@Override
	public int count(Map<String, Object> param) {
		return getSqlSession().selectOne("userGroup.count", param);
	}

	@Override
	public UserGroup queryByGroupId(int groupId) {
		return getSqlSession().selectOne("userGroup.queryByGroupId", groupId);
	}

	@Override
	public UserGroup queryByGroupName(String groupName) {
		return getSqlSession().selectOne("userGroup.queryByGroupName", groupName);
	}

	@Override
	public int insert(UserGroup group) {
		return getSqlSession().insert("userGroup.insert", group);
	}

	@Override
	public void update(UserGroup group) {
		getSqlSession().update("userGroup.update", group);
	}

	@Override
	public void delete(int groupId) {
		getSqlSession().delete("userGroup.delete", groupId);
	}

	@Override
	public void insertRelRole(int groupId, int roleId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("groupId", groupId);
		param.put("roleId", roleId);
		getSqlSession().insert("userGroup.insertRelRole", param);
	}

	@Override
	public void deleteRelRoleByGroupId(int groupId) {
		getSqlSession().delete("userGroup.deleteRelRoleByGroupId", groupId);
	}

	@Override
	public void insertRelUser(int groupId, int userId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("groupId", groupId);
		param.put("userId", userId);
		getSqlSession().insert("userGroup.insertRelUser", param);
	}

	@Override
	public void deleteRelUserByGroupId(int groupId) {
		getSqlSession().delete("userGroup.deleteRelUserByGroupId", groupId);
	}

}
