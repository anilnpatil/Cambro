package com.demo.mappingController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mappingService.DatabaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/changedb")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/changeto")
    public ResponseEntity<String> changeDatabase(@RequestParam String dbName) {
        // Call service method with dbName
        databaseService.performDatabaseOperation(dbName);
        // Perform operation.s
        return ResponseEntity.ok("Switched to database: " + dbName);
    }
}