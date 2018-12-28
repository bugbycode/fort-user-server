package com.test;

import org.apache.commons.validator.routines.RegexValidator;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.bugbycode.module.user.User;
import com.util.reg.RegexUtil;

public class TestJson {

	@Test
	public void testJsonToBean() {
		JSONObject json = new JSONObject();
		json.put("name", "zhangsan");
		User user = JSONObject.toJavaObject(json, User.class);
		System.out.println(user.getName());
	}
	
	@Test
	public void testValidate() {
		boolean check = RegexUtil.check(RegexUtil.EMAIL_REGEX, null);
		System.out.println(check);
	}
}
