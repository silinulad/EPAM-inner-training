package com.example.silin.guitarshop.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBParam {

	@Value("${jdbc.driver}")
	private String driver;

	@Value("${db.url}")
	private String url;

	@Value("${user}")
	private String user;

	@Value("${password}")
	private String password;

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

}
