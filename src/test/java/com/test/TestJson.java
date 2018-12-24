package com.test;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.bugbycode.module.user.User;

public class TestJson {

	@Test
	public void testJsonToBean() {
		JSONObject json = new JSONObject();
		json.put("name", "zhangsan");
		User user = JSONObject.toJavaObject(json, User.class);
		System.out.println(user.getName());
	}
}
