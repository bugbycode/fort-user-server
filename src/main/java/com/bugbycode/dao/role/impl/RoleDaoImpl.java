package com.bugbycode.dao.role.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.bugbycode.dao.base.BaseDao;
import com.bugbycode.dao.role.RoleDao;
import com.bugbycode.module.role.Role;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDao implements RoleDao {

	@Override
	public List<Role> query(Map<String, Object> param, RowBounds rb) {
		return getSqlSession().selectList("role.query", param, rb);
	}

	@Override
	public List<Role> query(Map<String, Object> param) {
		return getSqlSession().selectList("role.query", param);
	}

	@Override
	public int count(Map<String, Object> param) {
		return getSqlSession().selectOne("role.count", param);
	}

	@Override
	public List<Role> queryByUserId(int userId) {
		return getSqlSession().selectList("role.queryByUserId", userId);
	}

	@Override
	public List<Role> queryByGroupId(int groupId) {
		return getSqlSession().selectList("role.queryByGroupId", groupId);
	}

	@Override
	public Role queryByRoleId(int roleId) {
		return getSqlSession().selectOne("role.queryByRoleId", roleId);
	}

	@Override
	public Role queryByRoleName(String roleName) {
		return getSqlSession().selectOne("role.queryByRoleName", roleName);
	}

	@Override
	public int insert(Role role) {
		return getSqlSession().insert("role.insert", role);
	}

	@Override
	public void update(Role role) {
		getSqlSession().update("role.update", role);
	}

	@Override
	public void delete(int roleId) {
		getSqlSession().delete("role.delete", roleId);
	}

	@Override
	public void deleteRelUserByRoleId(int userId) {
		getSqlSession().delete("role.deleteRelUserByRoleId", userId);
	}

	@Override
	public void deleteRelGroupByRoleId(int roleId) {
		getSqlSession().delete("role.deleteRelGroupByRoleId", roleId);
	}

}
