package com.example.silin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.example.silin.guitarshop.model.Customer;

public class DataAccessIntegrationTestTemplate {
	private EmbeddedDatabase db;
	private DriverManagerDataSource dataSource;
	
//	@Before
//	public void setUp() {
//		 dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:~/test");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		
//	}
	
	@Before
	public void setUp() {
		db = new EmbeddedDatabaseBuilder().setName("guitar-shop").setScriptEncoding("UTF-8")
				.setType(EmbeddedDatabaseType.H2).addDefaultScripts().build();
	}

	@Test
	public void testDataAccess() {
		JdbcTemplate template = new JdbcTemplate(db);
		String sql = "SELECT * FROM customers WHERE firstname='Jhon'";
		template.query(sql, new CustomerMapper());
	}

	@After
	public void tearDown() {
		db.shutdown();
	}

	public static class CustomerMapper implements RowMapper<Customer> {

		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Customer user = new Customer();
			user.setId(rs.getLong("customer_id"));
			user.setEmail(rs.getString("email"));
			user.setHash(rs.getString("hash"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setRegDate(rs.getTimestamp("regDate"));
			return user;
		}
	}
}
