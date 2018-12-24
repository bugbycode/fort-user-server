package com.bugbycode.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bugbycode.module.user.User;
import com.bugbycode.service.user.UserService;
import com.util.StringUtil;
import com.util.page.SearchResult;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
		List<User> list = null;
		if(startIndex > -1) {
			SearchResult<User> sr = userService.query(param, startIndex, pageSize);
			list = sr.getList();
			if(CollectionUtils.isEmpty(list)) {
				list = new ArrayList<User>();
			}
			json.put("data", list);
			json.put("page", sr.getPage());
		}else {
			list = userService.query(param);
			json.put("data", list);
		}
		return json.toString();
	}
	
	@RequestMapping("/queryByUserId")
	@ResponseBody
	public String queryByUserId(int userId) {
		JSONObject json = new JSONObject();
		User user = userService.queryByUserId(userId);
		json.put("data", user);
		return json.toString();
	}
	
	@RequestMapping("/queryByUserName")
	@ResponseBody
	public String queryByUserName(String userName) {
		JSONObject json = new JSONObject();
		User user = userService.queryByUserName(userName);
		json.put("data", user);
		return json.toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(String jsonStr) {
		User user = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), User.class);
		userService.update(user);
		JSONObject json = new JSONObject();
		json.put("msg", "修改成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(String jsonStr) {
		User user = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), User.class);
		userService.insert(user);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("userId", user.getId());
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(int userId) {
		userService.delete(userId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/insertRelRole")
	@ResponseBody
	public String insertRelRole(int userId,int roleId) {
		userService.insertRelRole(userId, roleId);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/deleteRelRoleByUserId")
	@ResponseBody
	public String deleteRelRoleByUserId(int userId) {
		userService.deleteRelRoleByUserId(userId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/insertRelGroup")
	@ResponseBody
	public String insertRelGroup(int userId,int groupId) {
		userService.insertRelGroup(userId, groupId);
		JSONObject json = new JSONObject();
		json.put("msg", "新建成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/deleteRelGroupByUserId")
	@ResponseBody
	public String deleteRelGroupByUserId(int userId) {
		userService.deleteRelGroupByUserId(userId);
		JSONObject json = new JSONObject();
		json.put("msg", "删除成功");
		json.put("code", 0);
		return json.toJSONString();
	}
	
	@RequestMapping("/export")
	@ResponseBody
	public String exportUser() {
		return "/user/export";
	}
	
	@RequestMapping("/import")
	@ResponseBody
	public String importUser() {
		return "/user/import";
	}
}
