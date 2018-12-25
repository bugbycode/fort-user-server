package com.bugbycode.service.role.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.dao.role.RoleDao;
import com.bugbycode.module.role.Role;
import com.bugbycode.service.role.RoleService;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public SearchResult<Role> query(Map<String, Object> param, int startIndex, int pageSize) {
		SearchResult<Role> sr = new SearchResult<Role>();
		Page page = new Page(pageSize, startIndex);
		int totalCount = roleDao.count(param);
		List<Role> list = new ArrayList<Role>();
		if(totalCount > 0) {
			page.setTotalCount(totalCount);
			RowBounds rb = new RowBounds(page.getStartIndex(), page.getPageSize());
			list = roleDao.query(param, rb);
		}
		sr.setList(list);
		sr.setPage(page);
		return sr;
	}

	@Override
	public List<Role> query(Map<String, Object> param) {
		return roleDao.query(param);
	}

	@Override
	public List<Role> queryByUserId(int userId) {
		return roleDao.queryByUserId(userId);
	}

	@Override
	public List<Role> queryByGroupId(int groupId) {
		return roleDao.queryByGroupId(groupId);
	}

	@Override
	public Role queryByRoleId(int roleId) {
		return roleDao.queryByRoleId(roleId);
	}

	@Override
	public Role queryByRoleName(String roleName) {
		return roleDao.queryByRoleName(roleName);
	}

	@Override
	public int insert(Role role) {
		int row = roleDao.insert(role);
		if(row > 0) {
			return role.getId();
		}
		return 0;
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public void delete(int roleId) {
		roleDao.delete(roleId);
	}

	@Override
	public void deleteRelUserByRoleId(int roleId) {
		roleDao.deleteRelUserByRoleId(roleId);
	}

	@Override
	public void deleteRelGroupByRoleId(int roleId) {
		roleDao.deleteRelGroupByRoleId(roleId);
	}

}
