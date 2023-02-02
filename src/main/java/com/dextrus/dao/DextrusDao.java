package com.dextrus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import com.dextrus.dto.DextrusDto;

@Repository
public class DextrusDao {

	public int dbConnection(DextrusDto dextrusDto) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList retrieveCatalogs(DextrusDto dextrusDto) {
		ArrayList list = new ArrayList();
		try {
			String query = "SELECT name FROM sys.databases";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				dextrusDto.setDbName(resultSet.getString(1));
				list.add(dextrusDto.getDbName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList retrieveSchemaList(DextrusDto dextrusDto) {
		ArrayList list = new ArrayList();
		try {
			String query = "SELECT name FROM " + dextrusDto.getCatalog() + ".sys.schemas";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				dextrusDto.setDbName(rs.getString(1));
				list.add(dextrusDto.getDbName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> retrieveTableAndViews(DextrusDto dextrusDto) {
		ArrayList<String> list = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			PreparedStatement preparedStatement = connection.prepareStatement("use " + dextrusDto.getCatalog() + "; "
					+ "select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA=?;");
			preparedStatement.setString(1, dextrusDto.getSchema());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				dextrusDto.setCatalog(resultSet.getString(1));
				dextrusDto.setSchema(resultSet.getString(2));
				dextrusDto.setTableName(resultSet.getString(3));
				dextrusDto.setTableType(resultSet.getString(4));
				list.add(dextrusDto.getCatalog());
				list.add(dextrusDto.getSchema());
				list.add(dextrusDto.getTableName());
				list.add(dextrusDto.getTableType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getTableProperties(DextrusDto dextrusDto) {
		ArrayList<String> list = new ArrayList<>();
		String tableDescriptionQuery = "SELECT c.name AS 'COLUMN_NAME', t.name AS 'DATA_TYPE', c.is_nullable AS 'IS_NULLABLE', ISNULL(i.is_primary_key, 0) AS 'PRIMARY_KEY' FROM sys.columns c INNER JOIN sys.types t ON c.user_type_id = t.user_type_id LEFT JOIN sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id LEFT JOIN sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id WHERE c.object_id = OBJECT_ID(?);";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("use " + dextrusDto.getCatalog() + "; " + tableDescriptionQuery);
			preparedStatement.setString(1, dextrusDto.getTableName());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				dextrusDto.setColumnName(resultSet.getString("COLUMN_NAME"));
				dextrusDto.setDataType(resultSet.getString("DATA_TYPE"));
				dextrusDto.setIsNullable(resultSet.getString("IS_NULLABLE"));
				dextrusDto.setPrimaryKey(resultSet.getString("PRIMARY_KEY"));
				list.add("Column_Name:" + dextrusDto.getColumnName());
				list.add("Data_Type:" + dextrusDto.getDataType());
				list.add("Is_Nullable" + dextrusDto.getIsNullable());
				list.add("Primary_Key:" + dextrusDto.getPrimaryKey());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList getTableDataByQuery(DextrusDto dextrusDto) {
		ArrayList<String> list=new ArrayList<>();
		
		return list;
	}

	public ArrayList<String> getTablesByPattern(DextrusDto dextrusDto) {
		ArrayList<String> list = new ArrayList<>();
		String getTablesByPatternQuery = "SELECT TABLE_NAME,TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE ?";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(dextrusDto.getUrl(), dextrusDto.getUserName(),
					dextrusDto.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("use " + dextrusDto.getCatalog() + "; " + getTablesByPatternQuery);
			preparedStatement.setString(1, dextrusDto.getPattern());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				list.add("Table_Name:" + resultSet.getString("TABLE_NAME"));
				list.add("Table_Type:" + resultSet.getString("TABLE_TYPE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
