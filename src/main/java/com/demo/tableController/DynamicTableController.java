package com.demo.tableController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.config.DatabaseConfig;
import com.demo.tableDto.DynamicTableDto;
import com.demo.tableService.DynamicTableService;

@RestController
@RequestMapping("/api")
public class DynamicTableController {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTableController.class);

    @Autowired
    private DynamicTableService dynamicTableService;
//Create table feature    
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

    // Delete Database feature
    @DeleteMapping("/dropDatabase/{dbName}")
    public ResponseEntity<ApiResponse<String>> dropDatabase(@PathVariable String dbName) {
        DatabaseConfig database = new DatabaseConfig();
        database.setDatabaseName(dbName);
        try {
            String result = dynamicTableService.dropDatabase(dbName);
            ApiResponse<String> response = new ApiResponse<>(true, result, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, null, "Error dropping database: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    //delete table feature
    @DeleteMapping("/dropTable/dbName={dbName}/tableName={tableName}")
    public ResponseEntity<ApiResponse<String>> dropTable(@PathVariable String dbName, @PathVariable String tableName) {
        DatabaseConfig database = new DatabaseConfig();
        database.setDatabaseName(dbName);
        try {
            String result = dynamicTableService.dropTable(dbName, tableName);
            ApiResponse<String> response = new ApiResponse<>(true, result, null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(false, null, "Error dropping table: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error dropping table: " + e.getMessage());
        }
    }  
}


