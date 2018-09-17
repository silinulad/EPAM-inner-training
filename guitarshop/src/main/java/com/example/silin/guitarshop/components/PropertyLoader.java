package com.example.silin.guitarshop.components;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class PropertyLoader {
	public Properties loadProperty(String filename) {

		Properties properties = new Properties();

		try {
			Resource resource = new ClassPathResource(filename);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}
}
