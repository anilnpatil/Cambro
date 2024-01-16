package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.dto.DynamicTableDto;
import com.demo.service.DynamicTableService;

@RestController
@RequestMapping("/api")
public class DynamicTableController {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTableController.class);

    @Autowired
    private DynamicTableService dynamicTableService;

    @PostMapping("/createTable")
    public ResponseEntity<String> createTable(@RequestBody DynamicTableDto tableRequest) {
        logger.info("Request received to create table: {}", tableRequest.getTableName());
        try {
            String result = dynamicTableService.createTable(tableRequest);
            logger.info("Table created successfully: {}", tableRequest.getTableName());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error creating table: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating table: " + e.getMessage());
        }
    }

    // Delete table feature
    @DeleteMapping("/dropDatabase/{dbName}")
    public ResponseEntity<String> dropDatabase(@PathVariable String dbName) {
        try {
            String result = dynamicTableService.dropDatabase(dbName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error dropping database: " + e.getMessage());
        }
    }

    //delete table feature
    @DeleteMapping("/dropTable/dbName={dbName}/tableName={tableName}")
    public ResponseEntity<String> dropTable(@PathVariable String dbName, @PathVariable String tableName) {
        try {
            String result = dynamicTableService.dropTable(dbName, tableName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error dropping table: " + e.getMessage());
        }
    }
    //Read the Database table columnnames../
    
    @GetMapping("/fetchColumnNames/dbName/{dbName}/tableName/{tableName}")
    public ResponseEntity<?> getTableColumns(@PathVariable String dbName, @PathVariable String tableName) {
        try {
            List<Map<String, String>> columns = dynamicTableService.getTableColumns(dbName, tableName);
            return ResponseEntity.ok(columns);
        } catch (Exception e) {
            logger.error("Error fetching columns from table {}.{}: {}", dbName, tableName, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching columns: " + e.getMessage());
        }
    }
}


