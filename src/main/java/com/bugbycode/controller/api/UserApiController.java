package com.bugbycode.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bugbycode.module.user.User;
import com.bugbycode.service.user.UserService;
import com.util.AESUtil;
import com.util.StringUtil;

@RestController
@RequestMapping("/api")
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserByUserName")
	public String getUserByUserName(String userName) {
		JSONObject json = new JSONObject();
		User user = userService.queryByUserName(userName);
		if(!(user == null || StringUtil.isBlank(user.getPassword()))) {
			user.setPassword(AESUtil.decrypt(user.getPassword()));
		}
		json.put("data", user);
		return json.toString();
	}
}
