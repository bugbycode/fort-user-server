package com.bugbycode.controller.role;

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
import com.bugbycode.service.role.RoleService;
import com.util.StringUtil;
import com.util.page.SearchResult;

/**
 * 角色信息管理API
 * @author zhigongzhang
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 自定义条件查询角色信息
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
			SearchResult<Role> sr = roleService.query(param, startIndex, pageSize);
			json.put("data", sr.getList());
			json.put("page", sr.getPage());
		}else {
			json.put("data", roleService.query(param));
		}
		return json.toString();
	}
	
	/**
	 * 根据用户ID查询角色信息
	 * @param userId
	 * @return
	 */
	@RequestMapping("/queryByUserId")
	@ResponseBody
	public String queryByUserId(int userId) {
		List<Role> list = roleService.queryByUserId(userId);
		JSONObject json = new JSONObject();
		json.put("data", list);
		return json.toString();
	}
	
	/**
	 * 根据用户分组ID查询角色信息
	 * @param groupId
	 * @return
	 */
	@RequestMapping("/queryByGroupId")
	@ResponseBody
	public String queryByGroupId(int groupId) {
		List<Role> list = roleService.queryByGroupId(groupId);
		JSONObject json = new JSONObject();
		json.put("data", list);
		return json.toString();
	}
	
	/**
	 * 根据角色ID查询角色信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/queryByRoleId")
	@ResponseBody
	public String queryByRoleId(int roleId) {
		Role role = roleService.queryByRoleId(roleId);
		JSONObject json = new JSONObject();
		json.put("data", role);
		return json.toString();
	}
	
	/**
	 * 根据角色名称查询角色信息
	 * @param roleName
	 * @return
	 */
	@RequestMapping("/queryByRoleName")
	@ResponseBody
	public String queryByRoleName(String roleName) {
		Role role = roleService.queryByRoleName(roleName);
		JSONObject json = new JSONObject();
		json.put("data", role);
		return json.toString();
	}
	
	/**
	 * 新建角色信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(String jsonStr) {
		Role role = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), Role.class);
		int roleId = roleService.insert(role);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("userId", roleId);
		json.put("code", 0);
		return json.toString();
	}
	
	/**
	 * 更新角色信息
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(String jsonStr) {
		Role role = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), Role.class);
		roleService.update(role);
		JSONObject json = new JSONObject();
		json.put("msg", "修改成功");
		json.put("code", 0);
		return json.toString();
	}
	
	/**
	 * 根据角色ID删除角色信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int roleId) {
		roleService.delete(roleId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toString();
	}
	
	/**
	 * 根据角色ID删除用户与角色关联信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/deleteRelUserByRoleId")
	@ResponseBody
	public String deleteRelUserByRoleId(int roleId) {
		roleService.deleteRelUserByRoleId(roleId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toString();
	}
	
	/**
	 * 根据角色ID删除用户分组与角色关联信息
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/deleteRelGroupByRoleId")
	@ResponseBody
	public String deleteRelGroupByRoleId(int roleId) {
		roleService.deleteRelGroupByRoleId(roleId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toString();
	}
}
