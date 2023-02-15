package com.dextrus.controller;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dextrus.dto.ConnectionDto;
import com.dextrus.dto.MetaDataDto;
import com.dextrus.dto.TablePropertiesDto;
import com.dextrus.service.DextrusService;
@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/dextrus")
public class DextrusController {
	@Autowired
	private DextrusService dextrusService;

	@PostMapping("/dbConnection")
	ResponseEntity<String> getDbConnection(@RequestBody ConnectionDto connectionDto) {
		Connection connection = dextrusService.getDbConnection(connectionDto);
		if (connection!=null)
			return ResponseEntity.ok("{\"status\":\"success\"}");
		else
			return new ResponseEntity<String>("not connected",HttpStatus.SERVICE_UNAVAILABLE);
	}

	@PostMapping("/getCatalog")
	ResponseEntity<ArrayList<String>> getCatalogs(@RequestBody ConnectionDto connectionDto) {
		ArrayList<String> list;
		list = dextrusService.getCatalogs(connectionDto);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/{catalog}")
	ResponseEntity<ArrayList<String>> getSchema(@RequestBody ConnectionDto connectionDto,@PathVariable String catalog) {
		ArrayList<String> list;
		list = dextrusService.getSchemaList(connectionDto,catalog);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@PostMapping("/getTablesAndViews/{catalog}/{schema}")
	ResponseEntity<ArrayList<TablePropertiesDto>>  getTablesAndViews(@RequestBody ConnectionDto connectionDto,@PathVariable String catalog,@PathVariable String schema){
		ArrayList<TablePropertiesDto> list=dextrusService.getTablesAndViews(connectionDto,catalog,schema);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/getMetaData/{catalog}/{schema}/{tableName}")
	ResponseEntity<ArrayList<MetaDataDto>>  getTableProperties(@RequestBody ConnectionDto connectionDto,@PathVariable String catalog,@PathVariable String schema,@PathVariable String tableName){
		ArrayList<MetaDataDto> list=dextrusService.getTableProperties(connectionDto,catalog,schema,tableName);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/getTabledata/{catalog}/{inputQuery}")
	ResponseEntity<ArrayList<String>> getTableDataByQuery(@RequestBody ConnectionDto connectionDto,@PathVariable String catalog,@PathVariable String inputQuery){
		ArrayList<String> list=dextrusService.getTableDataByQuery(connectionDto,catalog,inputQuery);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/searchOperation/{catalog}")
	ResponseEntity<ArrayList<TablePropertiesDto>> getTablesByPattern(@RequestBody ConnectionDto connectionDto,@PathVariable String catalog){
		ArrayList<TablePropertiesDto> list=dextrusService.getTablesByPattern(connectionDto,catalog);		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
}
