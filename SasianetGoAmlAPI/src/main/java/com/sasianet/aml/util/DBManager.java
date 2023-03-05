package com.sasianet.aml.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sasianet.aml.pojo.Database;




public class DBManager {
	
	private static DBManager dbManager = null;
	
	

	public static DBManager getInstance() {
		if (dbManager == null) {
			dbManager = new DBManager();
		}
		return dbManager;
	}

	public Connection getConnection() throws Exception {
		Connection conn = null;
		try {

			String connString = "jdbc:oracle:thin:@" + Database.getInstance().getHost()+ ":" + Database.getInstance().getPort() + ":" + Database.getInstance().getName();
			System.out.println(connString);
			Class.forName(Database.getInstance().getJdbcDriver());
			conn = DriverManager.getConnection(connString, Database.getInstance().getUsername(), Database.getInstance().getPassword());
			conn.setAutoCommit(false);

		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			throw ex;
		}

		return conn;
	}

	public void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception ex) {
			// ex.printStackTrace();
		}
	}
	
	

}
