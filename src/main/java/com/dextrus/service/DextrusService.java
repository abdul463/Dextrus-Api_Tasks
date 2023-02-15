package com.dextrus.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dextrus.dao.DextrusDao;
import com.dextrus.dto.ConnectionDto;
import com.dextrus.dto.MetaDataDto;
import com.dextrus.dto.TablePropertiesDto;

@Service
public class DextrusService {
	@Autowired
	private DextrusDao dextrusDao;

	public Connection getDbConnection(ConnectionDto dextrusDto) {
		Connection connection=dextrusDao.getDbConnection(dextrusDto);
		return connection;
	}

	public ArrayList<String> getCatalogs(ConnectionDto dextrusDto) {
		ArrayList<String> list;
		list=dextrusDao.getCatalogs(dextrusDto);
		return list;
	}

	public ArrayList<String> getSchemaList(ConnectionDto dextrusDto,String catalog) {
		ArrayList<String> list=dextrusDao.getSchemaList(dextrusDto,catalog);
		return list;
	}

	public ArrayList<TablePropertiesDto> getTablesAndViews(ConnectionDto connectionDto,String catalog,String schema) {
		ArrayList<TablePropertiesDto> list=dextrusDao.retrieveTableAndViews(connectionDto,catalog,schema);
		return list;
	}

	public ArrayList<MetaDataDto> getTableProperties(ConnectionDto connectionDto,String catalog,String schema,String tableName){
		ArrayList<MetaDataDto> list=dextrusDao.getTableProperties(connectionDto,catalog,schema,tableName);
		return list;
	}

	public ArrayList<String> getTableDataByQuery(ConnectionDto connectionDto,String catalog,String inputQuery) {
		ArrayList<String> list=dextrusDao.getTableDataByQuery(connectionDto,catalog,inputQuery);
		return list;
	}

	public ArrayList<TablePropertiesDto> getTablesByPattern(ConnectionDto connectionDto,String catalog) {
		ArrayList<TablePropertiesDto> list=dextrusDao.getTablesByPattern(connectionDto,catalog);
		return list;
	}
}
