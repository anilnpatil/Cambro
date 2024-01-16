package com.demo.controller;

import com.demo.dto.CreateTableDto;
import com.demo.service.CreateTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/tables")
public class CreateTableController {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTableController.class);

    @Autowired
    private CreateTableService createTableService;

    @PostMapping("/createTable")
    public ResponseEntity<String> createTable(@RequestBody CreateTableDto tableRequest) {
        logger.info("Received request to create table: {}.{}", tableRequest.getSchemaName(), tableRequest.getTableName());
        try {
            String result = createTableService.createTable(tableRequest);
            logger.info("Response for creating table {}.{}: {}", tableRequest.getSchemaName(), tableRequest.getTableName(), result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error creating table {}.{}: {}", tableRequest.getSchemaName(), tableRequest
            .getTableName(), e.getMessage(), e);
            return ResponseEntity.status(500).body("Error creating table: " + e.getMessage());
        }
    }
} 