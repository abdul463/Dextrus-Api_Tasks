package com.dextrus.dto;

public class ConnectionDto {
	String url;
	String userName;
	String password;
	String pattern;
	String dbName;
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName=dbName;
	}
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
