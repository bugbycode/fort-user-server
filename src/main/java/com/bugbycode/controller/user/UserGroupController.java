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
import com.bugbycode.module.user.UserGroup;
import com.bugbycode.service.user.UserGroupService;
import com.util.StringUtil;
import com.util.page.SearchResult;

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
		int groupId = userGroupService.insert(group);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("groupId", groupId);
		json.put("code", 0);
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
		userGroupService.update(group);
		JSONObject json = new JSONObject();
		json.put("msg", "修改成功");
		json.put("code", 0);
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
		userGroupService.delete(groupId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
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
		userGroupService.insertRelRole(groupId, roleId);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("code", 0);
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
		userGroupService.deleteRelRoleByGroupId(groupId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
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
		userGroupService.insertRelUser(groupId, userId);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("code", 0);
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
		userGroupService.deleteRelUserByGroupId(groupId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toString();
	}
}
