package com.demo.tableController;

import com.demo.tableDto.CreateTableDto;
import com.demo.tableService.CreateTableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{dbName}/table")
public class CreateTableController {

    @Autowired
    private CreateTableService createTableService;

   @PostMapping("/create")
public ResponseEntity<String> createTable(@PathVariable("dbName") String dbName,
                                          @RequestBody CreateTableDto tableRequest) {
    try {
        String result = createTableService.createTable(dbName, tableRequest);
        return ResponseEntity.ok(result);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("Error creating table: " + e.getMessage());
    }
} 
}