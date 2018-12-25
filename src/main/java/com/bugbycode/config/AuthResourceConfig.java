package com.bugbycode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class AuthResourceConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${spring.user.oauth.clientId}")
	private String clientId;
	
	@Value("${spring.user.oauth.clientSecret}")
	private String clientSecret;
	
	@Value("${spring.user.oauth.checkTokenUrl}")
	private String checkTokenUrl;
	
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setClientId(clientId);
        tokenService.setClientSecret(clientSecret);
        tokenService.setCheckTokenEndpointUrl(checkTokenUrl);
        resources.tokenServices(tokenService);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        //用户管理
        .antMatchers("/user/update").hasRole("USER_UPDATE") //修改用户权限
        .antMatchers("/user/insert").hasRole("USER_INSERT") //添加用户权限
        .antMatchers("/user/delete").hasRole("USER_DELTE")  //删除用户权限
        .antMatchers("/user/insertRelRole","/user/deleteRelRoleByUserId","/user/insertRelGroup",
        		"/user/deleteRelGroupByUserId")
        .hasAnyRole("USER_UPDATE","USER_DELTE","USER_INSERT")
        .antMatchers("/user/queryByUserId","/user/query","/user/queryByUserName") //具备操作用户的任意权限均可查看用户列表以及用户编辑页面
        .hasAnyRole("USER_QUERY","USER_UPDATE","USER_DELTE","USER_INSERT");
    }
}
