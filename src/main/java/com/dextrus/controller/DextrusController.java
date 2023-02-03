package com.dextrus.controller;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dextrus.dto.DextrusDto;
import com.dextrus.service.DextrusService;

@RestController
@RequestMapping("/dextrus")
public class DextrusController {
	@Autowired
	private DextrusService dextrusService;

	private Connection connection=null;
	@PostMapping("/dbConnection")
	ResponseEntity<String> getDbConnection(@RequestBody DextrusDto dextrusDto) {
		connection = dextrusService.getDbConnection(dextrusDto);
		if (connection!=null)
			return ResponseEntity.ok("{\"status\":\"success\"}");
		else
			return ResponseEntity.ok("{\"status\":\" unable to connect\"}");
	}

	@PostMapping("/getCatalogs")
	ResponseEntity<ArrayList<String>> getCatalogs(@RequestBody DextrusDto dextrusDto) {
		ArrayList<String> list;
		list = dextrusService.getCatalogs(dextrusDto);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/schemaList")
	ResponseEntity<ArrayList<String>> getSchema(@RequestBody DextrusDto dextrusDto) {
		ArrayList<String> list;
		list = dextrusService.getSchemaList(dextrusDto);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@PostMapping("/TablesAndViews")
	ResponseEntity<ArrayList<String>>  getTablesAndViews(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list=dextrusService.getTablesAndViews(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/TableProperties")
	ResponseEntity<ArrayList<String>>  getTableProperties(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list=dextrusService.getTablesProperties(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/getTabledata")
	ResponseEntity<ArrayList<String>> getTableDataByQuery(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list=dextrusService.getTableDataByQuery(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/searchOperation")
	ResponseEntity<ArrayList<String>> getTablesByPattern(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list=dextrusService.getTablesByPattern(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
}
