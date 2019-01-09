package com.bugbycode.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bugbycode.module.role.Role;
import com.bugbycode.module.user.User;
import com.bugbycode.module.user.UserGroup;
import com.bugbycode.service.role.RoleService;
import com.bugbycode.service.user.UserGroupService;
import com.bugbycode.service.user.UserService;
import com.util.AESUtil;
import com.util.StringUtil;
import com.util.page.SearchResult;
import com.util.reg.RegexUtil;

/**
 * 用户信息管理API
 * 
 * @author zhigongzhang
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserGroupService userGroupService;
	
	/**
	 * 自定义条件查询用户信息
	 * @param keyWord
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public String query(
			@RequestParam(name="keyword",defaultValue="")
			String keyWord,
			@RequestParam(name="startIndex",defaultValue="-1")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="10")
			int pageSize) {
		JSONObject json = new JSONObject();
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(keyWord)) {
			param.put("keyword", keyWord);
		}
		if(startIndex > -1) {
			SearchResult<User> sr = userService.query(param, startIndex, pageSize);
			json.put("data", sr.getList());
			json.put("page", sr.getPage());
		}else {
			json.put("data", userService.query(param));
		}
		return json.toString();
	}
	
	/**
	 * 根据分组名称查询分组信息
	 * @param groupName
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryGroup")
	@ResponseBody
	public String queryGroup(
			@RequestParam(name="groupName",defaultValue="")
			String groupName,
			@RequestParam(name="startIndex",defaultValue="-1")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="10")
			int pageSize) {
		JSONObject json = new JSONObject();
		Map<String,Object> param = new HashMap<String,Object>();
		
		if(StringUtil.isNotBlank(groupName)) {
			param.put("groupName", groupName);
		}
		if(startIndex > -1) {
			SearchResult<UserGroup> sr = userGroupService.query(param, startIndex, pageSize);
			json.put("data", sr.getList());
			json.put("page", sr.getPage());
		}else {
			json.put("data", userGroupService.query(param));
		}
		return json.toString();
	}
	
	/**
	 * 	根据角色名称查询角色
	 * @param roleName
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/queryRole")
	@ResponseBody
	public String queryRole(
			String roleName,
			@RequestParam(name="startIndex",defaultValue="-1")
			int startIndex,
			@RequestParam(name="pageSize",defaultValue="10")
			int pageSize) {
		JSONObject json = new JSONObject();
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(roleName)) {
			param.put("roleName", roleName);
		}
		if(startIndex > -1) {
			SearchResult<Role> sr = roleService.query(param, startIndex, pageSize);
			json.put("data", sr.getList());
			json.put("page", sr.getPage());
		}else {
			json.put("data", roleService.query(param));
		}
		return json.toString();
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryByUserId")
	@ResponseBody
	public String queryByUserId(int userId) {
		JSONObject json = new JSONObject();
		User user = userService.queryByUserId(userId);
		json.put("data", user);
		return json.toString();
	}
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	@RequestMapping("/queryByUserName")
	@ResponseBody
	public String queryByUserName(String userName) {
		JSONObject json = new JSONObject();
		User user = userService.queryByUserName(userName);
		json.put("data", user);
		return json.toString();
	}
	
	/**
	 * 更新用户信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(String jsonStr) {
		User user = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), User.class);
		int userId = user.getId();
		String name = user.getName();
		String email = user.getEmail();
		String password = user.getPassword();
		int code = 0;
		String msg = "修改成功";
		try {
			if(userId <= 0) {
				throw new RuntimeException("用户ID信息错误");
			}
			if(!RegexUtil.check(RegexUtil.USER_NAME_REGEX, name)) {
				throw new RuntimeException("用户姓名格式错误");
			}
			if(StringUtil.isNotBlank(email) && !RegexUtil.check(RegexUtil.EMAIL_REGEX, email)) {
				throw new RuntimeException("电子邮件格式错误");
			}
			User old = userService.queryByUserId(userId);
			if(old == null) {
				throw new RuntimeException("该用户不存在");
			}
			if(StringUtil.isNotEmpty(password)) {
				user.setPassword(AESUtil.encrypt(password));
			}
			userService.update(user);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 新建用户信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(String jsonStr) {
		User user = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), User.class);
		String name = user.getName();
		String username = user.getUsername();
		String password = user.getPassword();
		String email = user.getEmail();
		int code = 0;
		String msg = "新建成功";
		try {
			if(!RegexUtil.check(RegexUtil.USER_NAME_REGEX, name)) {
				throw new RuntimeException("用户姓名格式错误");
			}
			if(StringUtil.isNotBlank(password)) {
				user.setPassword(AESUtil.encrypt(password));
			}
			if(!RegexUtil.check(RegexUtil.USER_LOGIN_NAME_REGEX, username)) {
				throw new RuntimeException("用户名格式错误");
			}
			if(StringUtil.isNotBlank(email) && !RegexUtil.check(RegexUtil.EMAIL_REGEX, email)) {
				throw new RuntimeException("电子邮件格式错误");
			}
			User old = userService.queryByUserName(username);
			if(old != null) {
				throw new RuntimeException("该用户名已被使用");
			}
			user.setType(1);
			userService.insert(user);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		int userId = user.getId();
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("userId", userId);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 删除用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int userId) {
		int code = 0;
		String msg = "删除成功";
		try {
			if(userId <= 0) {
				throw new RuntimeException("用户ID信息错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("该用户信息不存在");
			}
			userService.delete(userId);
		}catch (Exception e) {
			msg = e.getMessage();
			code = 1;
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 添加用户与角色关联信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/insertRelRole")
	@ResponseBody
	public String insertRelRole(int userId,int roleId) {
		int code = 0;
		String msg = "新建成功";
		try {
			if(userId <= 0) {
				throw new RuntimeException("用户ID错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("用户ID错误");
			}
			if(roleId <= 0) {
				throw new RuntimeException("角色ID错误");
			}
			Role role = roleService.queryByRoleId(roleId);
			if(role == null) {
				throw new RuntimeException("角色ID错误");
			}
			userService.insertRelRole(userId, roleId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 根据用户ID删除用户与角色关联信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteRelRoleByUserId")
	@ResponseBody
	public String deleteRelRoleByUserId(int userId) {
		int code = 0;
		String msg = "删除成功";
		try {
			if(userId < 1) {
				throw new RuntimeException("用户ID错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("用户ID错误");
			}
			userService.deleteRelRoleByUserId(userId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 添加用户与分组关联信息
	 * @param userId
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/insertRelGroup")
	@ResponseBody
	public String insertRelGroup(int userId,int groupId) {
		int code = 0;
		String msg = "删除成功";
		try {
			if(userId < 1) {
				throw new RuntimeException("用户ID错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("用户ID错误");
			}
			if(groupId < 1) {
				throw new RuntimeException("用户分组ID错误");
			}
			UserGroup group = userGroupService.queryByGroupId(groupId);
			if(group == null) {
				throw new RuntimeException("用户分组ID错误");
			}
			userService.insertRelGroup(userId, groupId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
	
	/**
	 * 根据用户ID删除用户与分组关联信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteRelGroupByUserId")
	@ResponseBody
	public String deleteRelGroupByUserId(int userId) {
		int code = 0;
		String msg = "删除成功";
		try {
			if(userId < 1) {
				throw new RuntimeException("用户ID错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("用户ID错误");
			}
			userService.deleteRelGroupByUserId(userId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toJSONString();
	}
}
