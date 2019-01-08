package com.bugbycode.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugbycode.dao.user.UserDao;
import com.bugbycode.module.user.User;
import com.bugbycode.service.user.UserService;
import com.util.page.Page;
import com.util.page.SearchResult;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public SearchResult<User> query(Map<String, Object> param, int startIndex, int pageSize) {
		SearchResult<User> sr = new SearchResult<User>();
		Page page = new Page(pageSize, startIndex);
		int totalCount = userDao.count(param);
		List<User> list = new ArrayList<User>();
		if(totalCount > 0) {
			page.setTotalCount(totalCount);
			RowBounds rb = new RowBounds(page.getStartIndex(), page.getPageSize());
			list = userDao.query(param, rb);
		}
		sr.setList(list);
		sr.setPage(page);
		return sr;
	}

	@Override
	public List<User> query(Map<String, Object> param) {
		return userDao.query(param);
	}

	@Override
	public User queryByUserId(int userId) {
		return userDao.queryByUserId(userId);
	}

	@Override
	public User queryByUserName(String userName) {
		return userDao.queryByUserName(userName);
	}

	@Override
	public int insert(User user) {
		user.setCreateTime(new Date());
		int row = userDao.insert(user);
		if(row > 0) {
			return user.getId();
		}
		return 0;
	}

	@Override
	public void update(User user) {
		user.setUpdateTime(new Date());
		userDao.update(user);
	}

	@Override
	public void delete(int userId) {
		userDao.delete(userId);
	}

	@Override
	public void insertRelRole(int userId, int roleId) {
		if(userDao.checkRelRole(userId, roleId) == 0) {
			userDao.insertRelRole(userId, roleId);
		}
	}

	@Override
	public void deleteRelRoleByUserId(int userId) {
		userDao.deleteRelRoleByUserId(userId);
	}

	@Override
	public void insertRelGroup(int userId, int groupId) {
		if(userDao.checkRelGroup(userId, groupId) == 0) {
			userDao.insertRelGroup(userId, groupId);
		}
	}

	@Override
	public void deleteRelGroupByUserId(int userId) {
		userDao.deleteRelGroupByUserId(userId);
	}

}
