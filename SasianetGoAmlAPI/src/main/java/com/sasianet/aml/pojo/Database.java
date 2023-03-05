package com.sasianet.aml.pojo;

public final class Database {
	private static Database instance = null;
	
	public static Database getInstance() {
		if(instance==null) {
			instance = new Database();
		}		
		return instance;
	}

	//private String host = "snpdsrv";
	private String host = "147.120.20.1";//"10.10.4.1";
	private String port = "1521";
	private String name = "SNPDDB";//"MRFDB";//SNPDDB
	private String username = "MRFL";
	private String password = "eightitengpw82";
	private String jdbcDriver = "oracle.jdbc.driver.OracleDriver";	
	
	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getJdbcDriver() {
		return jdbcDriver;
	}



	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}


	public Database() {
		super();
	}
	
	
}
