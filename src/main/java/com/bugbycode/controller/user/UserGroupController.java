package com.bugbycode.controller.user;

import java.util.HashMap;
import java.util.List;
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
import com.util.StringUtil;
import com.util.page.SearchResult;
import com.util.reg.RegexUtil;

/**
 * 用户分组信息管理API
 * @author zhigongzhang
 *
 */
@Controller
@RequestMapping("/userGroup")
public class UserGroupController {
	
	@Autowired
	private UserGroupService userGroupService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 自定义条件查询分组信息
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
			SearchResult<UserGroup> sr = userGroupService.query(param, startIndex, pageSize);
			json.put("data", sr.getList());
			json.put("page", sr.getPage());
		}else {
			json.put("data", userGroupService.query(param));
		}
		return json.toString();
	}
	
	/**
	 * 根据用户ID查询分组信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryByUserId")
	@ResponseBody
	public String queryByUserId(int userId){
		List<UserGroup> list = userGroupService.queryByUserId(userId);
		JSONObject json = new JSONObject();
		json.put("data", list);
		return json.toString();
	}
	
	/**
	 * 根据分组ID查询分组信息
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/queryByGroupId")
	@ResponseBody
	public String queryByGroupId(int groupId){
		UserGroup group = userGroupService.queryByGroupId(groupId);
		JSONObject json = new JSONObject();
		json.put("data", group);
		return json.toString();
	}
	
	/**
	 * 根据分组名称查询分组信息
	 * @param groupName
	 * @return
	 */
	@RequestMapping("/queryByGroupName")
	@ResponseBody
	public String queryByGroupName(String groupName){
		UserGroup group = userGroupService.queryByGroupName(groupName);
		JSONObject json = new JSONObject();
		json.put("data", group);
		return json.toString();
	}
	
	/**
	 * 新建分组信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(String jsonStr) {
		UserGroup group = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), UserGroup.class);
		String msg = "新建成功";
		int code = 0;
		String name = group.getName();
		try {
			if(!RegexUtil.check(RegexUtil.USER_GROUP_NAME_REGEX, name)) {
				throw new RuntimeException("分组名称格式错误");
			}
			UserGroup old = userGroupService.queryByGroupName(name);
			if(old != null) {
				throw new RuntimeException("该分组名称已被使用");
			}
			userGroupService.insert(group);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		int groupId = group.getId();
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("groupId", groupId);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 更新分组信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(String jsonStr) {
		UserGroup group = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), UserGroup.class);
		int groupId = group.getId();
		String msg = "修改成功";
		int code = 0;
		String name = group.getName();
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			if(!RegexUtil.check(RegexUtil.USER_GROUP_NAME_REGEX, name)) {
				throw new RuntimeException("分组名称格式错误");
			}
			UserGroup old = userGroupService.queryByGroupId(groupId);
			if(old == null) {
				throw new RuntimeException("该分组不存在");
			}
			old = userGroupService.queryByGroupName(name);
			if(!(old == null || old.getId() == groupId)) {
				throw new RuntimeException("该分组名称已被使用");
			}
			userGroupService.update(group);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 根据分组ID删除分组信息
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int groupId) {
		String msg = "删除成功";
		int code = 0;
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			UserGroup old = userGroupService.queryByGroupId(groupId);
			if(old == null) {
				throw new RuntimeException("该分组不存在");
			}
			userGroupService.delete(groupId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 新建分组与角色关联信息
	 * @param groupId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/insertRelRole")
	@ResponseBody
	public String insertRelRole(int groupId,int roleId) {
		String msg = "新建成功";
		int code = 0;
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			UserGroup group = userGroupService.queryByGroupId(groupId);
			if(group == null) {
				throw new RuntimeException("分组ID错误");
			}
			if(roleId < 1) {
				throw new RuntimeException("角色ID错误");
			}
			Role role = roleService.queryByRoleId(roleId);
			if(role == null) {
				throw new RuntimeException("角色ID错误");
			}
			userGroupService.insertRelRole(groupId, roleId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 根据分组ID删除分组与角色关联信息
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/deleteRelRoleByGroupId")
	@ResponseBody
	public String deleteRelRoleByGroupId(int groupId) {
		String msg = "删除成功";
		int code = 0;
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			UserGroup group = userGroupService.queryByGroupId(groupId);
			if(group == null) {
				throw new RuntimeException("分组ID错误");
			}
			userGroupService.deleteRelRoleByGroupId(groupId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 新建用户与分组关联信息
	 * @param groupId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/insertRelUser")
	@ResponseBody
	public String insertRelUser(int groupId,int userId) {
		String msg = "新建成功";
		int code = 0;
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			UserGroup group = userGroupService.queryByGroupId(groupId);
			if(group == null) {
				throw new RuntimeException("分组ID错误");
			}
			if(userId <= 0) {
				throw new RuntimeException("用户ID错误");
			}
			User user = userService.queryByUserId(userId);
			if(user == null) {
				throw new RuntimeException("用户ID错误");
			}
			userGroupService.insertRelUser(groupId, userId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
	
	/**
	 * 根据分组ID删除用户与分组关联信息
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/deleteRelUserByGroupId")
	@ResponseBody
	public String deleteRelUserByGroupId(int groupId) {
		String msg = "删除成功";
		int code = 0;
		try {
			if(groupId < 1) {
				throw new RuntimeException("分组ID错误");
			}
			UserGroup group = userGroupService.queryByGroupId(groupId);
			if(group == null) {
				throw new RuntimeException("分组ID错误");
			}
			userGroupService.deleteRelUserByGroupId(groupId);
		}catch (Exception e) {
			code = 1;
			msg = e.getMessage();
		}
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		json.put("code", code);
		return json.toString();
	}
}
