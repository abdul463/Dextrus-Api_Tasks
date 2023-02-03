package com.dextrus.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dextrus.dao.DextrusDao;
import com.dextrus.dto.DextrusDto;

@Service
public class DextrusService {
	@Autowired
	private DextrusDao dextrusDao;

	public Connection getDbConnection(DextrusDto dextrusDto) {
		Connection response=dextrusDao.getDbConnection(dextrusDto);
		return response;
	}

	public ArrayList<String> getCatalogs(DextrusDto dextrusDto) {
		ArrayList<String> list;
		list=dextrusDao.getCatalogs(dextrusDto);
		return list;
	}

	public ArrayList<String> getSchemaList(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getSchemaList(dextrusDto);
		return list;
	}

	public ArrayList<String> getTablesAndViews(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.retrieveTableAndViews(dextrusDto);
		return list;
	}

	public ArrayList<String> getTablesProperties(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getTableProperties(dextrusDto);
		return list;
	}

	public ArrayList<String> getTableDataByQuery(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getTableDataByQuery(dextrusDto);
		return list;
	}

	public ArrayList<String> getTablesByPattern(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getTablesByPattern(dextrusDto);
		return list;
	}
}
