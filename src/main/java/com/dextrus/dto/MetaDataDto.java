package com.dextrus.dto;

public class MetaDataDto {
	String columnName;
	String dataType;
	String IsNullable;
	String primaryKey;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getIsNullable() {
		return IsNullable;
	}
	public void setIsNullable(String isNullable) {
		IsNullable = isNullable;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
}
