package com.dextrus.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dextrus.dao.DextrusDao;
import com.dextrus.dto.DextrusDto;

@Service
public class DextrusService {
	@Autowired
	private DextrusDao dextrusDao;

	public int saveConnectionProperties(DextrusDto dextrusDto) {
		int response=dextrusDao.dbConnection(dextrusDto);
		return response;
	}

	public ArrayList getCatalogs(DextrusDto dextrusDto) {
		ArrayList list;
		list=dextrusDao.retrieveCatalogs(dextrusDto);
		return list;
	}

	public ArrayList getSchemaList(DextrusDto dextrusDto) {
		ArrayList list;
		list=dextrusDao.retrieveSchemaList(dextrusDto);
		return list;
	}

	public ArrayList getTablesAndViews(DextrusDto dextrusDto) {
		ArrayList list;
		list=dextrusDao.retrieveTableAndViews(dextrusDto);
		return list;
	}

	public ArrayList<String> getTablesProperties(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getTableProperties(dextrusDto);
		return list;
	}

	public ArrayList getTableDataByQuery(DextrusDto dextrusDto) {
		ArrayList<DextrusDto> list=dextrusDao.getTableDataByQuery(dextrusDto);
		return list;
	}

	public ArrayList<String> getTablesByPattern(DextrusDto dextrusDto) {
		ArrayList<String> list=dextrusDao.getTablesByPattern(dextrusDto);
		return list;
	}
}
