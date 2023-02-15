package com.dextrus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

import com.dextrus.dto.ConnectionDto;
import com.dextrus.dto.MetaDataDto;
import com.dextrus.dto.TablePropertiesDto;

@Repository
public class DextrusDao {
	Connection connection;
	public Connection getDbConnection(ConnectionDto connectionDto) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
		} catch (Exception e) {
			connection=null;
			e.printStackTrace();
		}
		return connection;
	}

	public ArrayList<String> getCatalogs(ConnectionDto connectionDto) {
		ArrayList<String> list = new ArrayList<>();
		try {
			String query = "SELECT name FROM sys.databases";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				connectionDto.setDbName(resultSet.getString(1));
				list.add(connectionDto.getDbName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getSchemaList(ConnectionDto connectionDto,String catalog) {
		ArrayList<String> list = new ArrayList<>();
		try {
			String query = "SELECT name FROM " + catalog + ".sys.schemas";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				connectionDto.setDbName(rs.getString(1));
				list.add(connectionDto.getDbName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TablePropertiesDto> retrieveTableAndViews(ConnectionDto connectionDto,String catalog,String schema) {
		ArrayList<TablePropertiesDto> list = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			PreparedStatement preparedStatement = connection.prepareStatement("use " + catalog + "; "
					+ "select * from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA=?;");
			preparedStatement.setString(1,schema);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TablePropertiesDto tablePropertiesDto=new TablePropertiesDto();
				tablePropertiesDto.setTableName(resultSet.getString(3));
				tablePropertiesDto.setTableType(resultSet.getString(4));
				list.add(tablePropertiesDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<MetaDataDto> getTableProperties(ConnectionDto connectionDto,String catalog,String schema,String tableName) {
		ArrayList<MetaDataDto> list = new ArrayList<>();
		String tableDescriptionQuery = "SELECT c.name AS 'COLUMN_NAME', t.name AS 'DATA_TYPE', c.is_nullable AS 'IS_NULLABLE', ISNULL(i.is_primary_key, 0) AS 'PRIMARY_KEY' FROM sys.columns c INNER JOIN sys.types t ON c.user_type_id = t.user_type_id LEFT JOIN sys.index_columns ic ON ic.object_id = c.object_id AND ic.column_id = c.column_id LEFT JOIN sys.indexes i ON ic.object_id = i.object_id AND ic.index_id = i.index_id WHERE c.object_id = OBJECT_ID(?);";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("use " + catalog + "; " + tableDescriptionQuery);
			tableName=schema+"."+tableName;
			preparedStatement.setString(1,tableName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MetaDataDto metaDataDto=new MetaDataDto();
				metaDataDto.setColumnName(resultSet.getString("COLUMN_NAME"));
				metaDataDto.setDataType(resultSet.getString("DATA_TYPE"));
				metaDataDto.setIsNullable(resultSet.getString("IS_NULLABLE"));
				metaDataDto.setPrimaryKey(resultSet.getString("PRIMARY_KEY"));
				list.add(metaDataDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getTableDataByQuery(ConnectionDto connectionDto,String catalog,String inputQuery) {
		ArrayList<String> list = new ArrayList<>();
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("use " + catalog + "; " + inputQuery+";");
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					list.add(resultSetMetaData.getColumnName(i) + ":" + resultSet.getString(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TablePropertiesDto> getTablesByPattern(ConnectionDto connectionDto,String catalog) {
		ArrayList<TablePropertiesDto> list = new ArrayList<>();
		String getTablesByPatternQuery = "SELECT TABLE_NAME,TABLE_TYPE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME LIKE ?";
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(connectionDto.getUrl(), connectionDto.getUserName(),
					connectionDto.getPassword());
			PreparedStatement preparedStatement = connection
					.prepareStatement("use " + catalog + "; " + getTablesByPatternQuery);
			preparedStatement.setString(1,connectionDto.getPattern());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				TablePropertiesDto tablepropertiesDto=new TablePropertiesDto();
				tablepropertiesDto.setTableName(resultSet.getString("TABLE_NAME"));
				tablepropertiesDto.setTableType(resultSet.getString("TABLE_TYPE"));
				list.add(tablepropertiesDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
