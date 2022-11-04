package com.ispan.util1;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	public static Connection createMSSQLConnection() {
		Properties props = new Properties();
		FileInputStream fis = null;
		Connection conn = null;
		try {
			fis = new FileInputStream("src/db.properties");
			props.load(fis);
			conn = DriverManager.getConnection(props.getProperty("MSSQL_DB_URL"), 
					props.getProperty("MSSQL_DB_UserName"), props.getProperty("MSSQL_DB_Password"));
			
		}catch(SQLException | IOException e) {
			System.out.println("連線出問題了");
			e.printStackTrace();
		}
		return conn;
	}
}
