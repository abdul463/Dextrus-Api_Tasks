package com.dextrus.controller;

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

	@PostMapping("/dbConnection")
	ResponseEntity<String> create(@RequestBody DextrusDto dextrusDto) {
		int check = dextrusService.saveConnectionProperties(dextrusDto);
		if (check == 1)
			return ResponseEntity.ok("{\"status\":\"success\"}");
		else
			return ResponseEntity.ok("{\"status\":\" unable to connect\"}");
	}

	@PostMapping("/getCatalogs")
	ResponseEntity<ArrayList<DextrusDto>> get(@RequestBody DextrusDto dextrusDto) {
		ArrayList<DextrusDto> list;
		list = dextrusService.getCatalogs(dextrusDto);
		// return ResponseEntity.ok("{\"status\":\"success\"}");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/schemaList")
	ResponseEntity<ArrayList<DextrusDto>> getSchema(@RequestBody DextrusDto dextrusDto) {
		ArrayList<DextrusDto> list;
		list = dextrusService.getSchemaList(dextrusDto);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@PostMapping("/TablesAndViews")
	ResponseEntity<ArrayList<DextrusDto>>  getTables(@RequestBody DextrusDto dextrusDto){
		ArrayList<DextrusDto> list;
		list=dextrusService.getTablesAndViews(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/TableProperties")
	ResponseEntity<ArrayList<String>>  getTableProperties(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list;
		list=dextrusService.getTablesProperties(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/getTabledata")
	ResponseEntity<ArrayList<DextrusDto>> getTableDataByQuery(@RequestBody DextrusDto dextrusDto){
		ArrayList<DextrusDto> list;
		list=dextrusService.getTableDataByQuery(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	@PostMapping("/searchOperation")
	ResponseEntity<ArrayList<String>> getTablesByPattern(@RequestBody DextrusDto dextrusDto){
		ArrayList<String> list=dextrusService.getTablesByPattern(dextrusDto);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
}
