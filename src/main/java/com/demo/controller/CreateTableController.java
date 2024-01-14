package com.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.CreateTableDto;
import com.demo.service.CreateTableService;

@RestController
@RequestMapping("/jpi/table")
public class CreateTableController {

    @Autowired
    private CreateTableService tableService;

    @PostMapping("/createTable")
    public ResponseEntity<String> createTable(@RequestBody CreateTableDto createTableDto) {
        System.out.println("Tablename: "+(createTableDto).getTableName());
        System.out.println("columns:  "+(createTableDto).getColumns());
        System.out.println("key :  "+(createTableDto).getPrimaryKey());
        return ResponseEntity.ok(tableService.createTable(createTableDto));
    }
}
