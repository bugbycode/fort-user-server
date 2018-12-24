package com.bugbycode.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DataSourceConfig {
	
	@Bean("dataSource")
	@ConfigurationProperties(prefix="spring.user.server.datasource")
	public DataSource getDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	@Bean("sqlSessionFactory")
	@Resource(name="dataSource")
	public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(dataSource);
		sf.setConfigLocation(resolver.getResource("classpath:mybatis/config/mybatis-config.xml"));
		sf.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*/*.xml"));
		return sf.getObject();
	}
}
