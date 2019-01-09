package com.bugbycode.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.dao.user.UserGroupDao;
import com.bugbycode.module.user.UserGroup;
import com.bugbycode.service.user.UserGroupService;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("userGroupService")
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupDao userGroupDao;
	
	@Override
	public SearchResult<UserGroup> query(Map<String, Object> param, int startIndex, int pageSize) {
		SearchResult<UserGroup> sr = new SearchResult<UserGroup>();
		Page page = new Page(pageSize, startIndex);
		int totalCount = userGroupDao.count(param);
		List<UserGroup> list = new ArrayList<UserGroup>();
		if(totalCount > 0) {
			page.setTotalCount(totalCount);
			RowBounds rb = new RowBounds(page.getStartIndex(), page.getPageSize());
			list = userGroupDao.query(param, rb);
		}
		sr.setList(list);
		sr.setPage(page);
		return sr;
	}

	@Override
	public List<UserGroup> query(Map<String, Object> param) {
		return userGroupDao.query(param);
	}

	@Override
	public List<UserGroup> queryByUserId(int userId) {
		return userGroupDao.queryByUserId(userId);
	}

	@Override
	public UserGroup queryByGroupId(int groupId) {
		return userGroupDao.queryByGroupId(groupId);
	}

	@Override
	public UserGroup queryByGroupName(String groupName) {
		return userGroupDao.queryByGroupName(groupName);
	}

	@Override
	public int insert(UserGroup group) {
		group.setCreateTime(new Date());
		return userGroupDao.insert(group);
	}

	@Override
	public void update(UserGroup group) {
		group.setUpdateTime(new Date());
		userGroupDao.update(group);
	}

	@Override
	public void delete(int groupId) {
		userGroupDao.delete(groupId);
	}

	@Override
	public void insertRelRole(int groupId, int roleId) {
		if(userGroupDao.checkRelRole(groupId, roleId) == 0) {
			userGroupDao.insertRelRole(groupId, roleId);
		}
	}

	@Override
	public void deleteRelRoleByGroupId(int groupId) {
		userGroupDao.deleteRelRoleByGroupId(groupId);
	}

	@Override
	public void insertRelUser(int groupId, int userId) {
		if(userGroupDao.checkRelUser(groupId, userId) == 0) {
			userGroupDao.insertRelUser(groupId, userId);
		}
	}

	@Override
	public void deleteRelUserByGroupId(int groupId) {
		userGroupDao.deleteRelUserByGroupId(groupId);
	}

}
