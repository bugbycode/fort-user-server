package com.bugbycode.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.BaseDao;
import com.bugbycode.dao.user.UserDao;
import com.bugbycode.module.user.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao {

	private final Logger logger = LogManager.getLogger(UserDaoImpl.class);
	
	@Override
	public List<User> query(Map<String, Object> param, RowBounds rb) {
		return getSqlSession().selectList("user.query", param, rb);
	}

	@Override
	public int count(Map<String, Object> param) {
		return getSqlSession().selectOne("user.count", param);
	}

	@Override
	public List<User> query(Map<String, Object> param) {
		return getSqlSession().selectList("user.query", param);
	}

	@Override
	public User queryByUserName(String userName) {
		return getSqlSession().selectOne("user.queryByUserName", userName);
	}

	@Override
	public User queryByUserId(int userId) {
		return getSqlSession().selectOne("user.queryByUserId", userId);
	}

	@Override
	public int insert(User user) {
		return getSqlSession().insert("user.insert", user);
	}

	@Override
	public void update(User user) {
		getSqlSession().update("user.update", user);
	}

	@Override
	public void delete(int userId) {
		getSqlSession().delete("user.delete", userId);
	}

	@Override
	public void insertRelRole(int userId, int roleId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("roleId", roleId);
		getSqlSession().insert("user.insertRelRole", param);
	}

	@Override
	public void deleteRelRoleByUserId(int userId) {
		getSqlSession().delete("user.deleteRelRoleByUserId", userId);
	}

	@Override
	public void insertRelGroup(int userId, int groupId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("groupId", groupId);
		getSqlSession().insert("user.insertRelGroup", param);
	}

	@Override
	public void deleteRelGroupByUserId(int userId) {
		getSqlSession().delete("user.deleteRelGroupByUserId", userId);
	}

	@Override
	public int checkRelRole(int userId, int roleId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("roleId", roleId);
		return getSqlSession().selectOne("user.checkRelRole", param);
	}

	@Override
	public int checkRelGroup(int userId, int groupId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("groupId", groupId);
		return getSqlSession().selectOne("user.checkRelGroup", param);
	}
}
