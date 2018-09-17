package com.example.silin.guitarshop.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.silin.guitarshop.components.DBParam;

@Configuration
@ComponentScan(basePackages = { "com.example.silin" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@PropertySource(value = "classpath:db.properties", ignoreResourceNotFound = true)
public class RootConfig {

	@Autowired
	private DBParam dbParam;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JdbcTemplate getJdbcTamplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dbParam.getDriver());
		dataSource.setUrl(dbParam.getUrl());
		dataSource.setUsername(dbParam.getUser());
		dataSource.setPassword(dbParam.getPassword());
		return dataSource;
	}
}