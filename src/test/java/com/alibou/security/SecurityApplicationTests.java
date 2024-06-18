package com.alibou.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SecurityApplicationTests {


	@Autowired
	private DataSource dataSource; // Autowire DataSource for Spring-managed connection pool

	@Test
	public void testDatabaseConnection() {
		try (Connection connection = dataSource.getConnection()) {
			// If connection is successful, it means database connectivity is working
			System.out.println("Connected to database: " + connection.getMetaData().getDatabaseProductName());
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle exception if connection fails
		}
	}


	@Test
	void contextLoads() {
	}



}
